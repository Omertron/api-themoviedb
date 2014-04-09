/*
 *      Copyright (c) 2004-2014 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.tools;

import com.omertron.themoviedbapi.MovieDbException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web browser with simple cookies support
 */
public final class WebBrowser {

    private static final Logger LOG = LoggerFactory.getLogger(WebBrowser.class);
    private static final Map<String, String> BROWSER_PROPERTIES = new HashMap<String, String>();
    private static final Map<String, Map<String, String>> COOKIES = new HashMap<String, Map<String, String>>();
    private static String proxyHost = null;
    private static int proxyPort = 0;
    private static String proxyUsername = null;
    private static String proxyPassword = null;
    private static String proxyEncodedPassword = null;
    // 25 second timeout
    private static int webTimeoutConnect = 25000;
    // 90 second timeout
    private static int webTimeoutRead = 90000;

    // Hide the constructor
    protected WebBrowser() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Populate the browser properties
     */
    private static void populateBrowserProperties() {
        if (BROWSER_PROPERTIES.isEmpty()) {
            BROWSER_PROPERTIES.put("User-Agent", "Mozilla/5.25 Netscape/5.0 (Windows; I; Win95)");
            BROWSER_PROPERTIES.put("Accept", "application/json");
            BROWSER_PROPERTIES.put("Content-type", "application/json");
        }
    }

    public static String request(String url) throws MovieDbException {
        try {
            return request(new URL(url));
        } catch (MalformedURLException ex) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.INVALID_URL, null, ex);
        }
    }

    public static URLConnection openProxiedConnection(URL url) throws MovieDbException {
        try {
            if (proxyHost != null) {
                System.getProperties().put("proxySet", "true");
                System.getProperties().put("proxyHost", proxyHost);
                System.getProperties().put("proxyPort", proxyPort);
            }

            URLConnection cnx = url.openConnection();

            if (proxyUsername != null) {
                cnx.setRequestProperty("Proxy-Authorization", proxyEncodedPassword);
            }

            return cnx;
        } catch (IOException ex) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.INVALID_URL, null, ex);
        }
    }

    public static String request(URL url) throws MovieDbException {
        return request(url, null, Boolean.FALSE);
    }

    public static String request(URL url, String jsonBody) throws MovieDbException {
        return request(url, jsonBody, Boolean.FALSE);
    }

    public static String request(URL url, String jsonBody, boolean isDeleteRequest) throws MovieDbException {

        StringWriter content = null;

        try {
            content = new StringWriter();

            BufferedReader in = null;
            HttpURLConnection cnx = null;
            OutputStreamWriter wr = null;
            try {
                cnx = (HttpURLConnection) openProxiedConnection(url);

                // If we get a null connection, then throw an exception
                if (cnx == null) {
                    throw new MovieDbException(MovieDbException.MovieDbExceptionType.CONNECTION_ERROR, "No HTTP connection could be made.");
                }

                if (isDeleteRequest) {
                    cnx.setDoOutput(true);
                    cnx.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    cnx.setRequestMethod("DELETE");
                }

                sendHeader(cnx);

                if (StringUtils.isNotBlank(jsonBody)) {
                    cnx.setDoOutput(true);
                    wr = new OutputStreamWriter(cnx.getOutputStream());
                    wr.write(jsonBody);
                }

                readHeader(cnx);

                // http://stackoverflow.com/questions/4633048/httpurlconnection-reading-response-content-on-403-error
                if (cnx.getResponseCode() >= 400) {
                    in = new BufferedReader(new InputStreamReader(cnx.getErrorStream(), getCharset(cnx)));
                } else {
                    in = new BufferedReader(new InputStreamReader(cnx.getInputStream(), getCharset(cnx)));
                }

                String line;
                while ((line = in.readLine()) != null) {
                    content.write(line);
                }
            } finally {
                if (wr != null) {
                    wr.flush();
                    wr.close();
                }

                if (in != null) {
                    in.close();
                }

                if (cnx instanceof HttpURLConnection) {
                    ((HttpURLConnection) cnx).disconnect();
                }
            }
            return content.toString();
        } catch (IOException ex) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.CONNECTION_ERROR, null, ex);
        } finally {
            if (content != null) {
                try {
                    content.close();
                } catch (IOException ex) {
                    LOG.debug("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    private static void sendHeader(URLConnection cnx) {
        populateBrowserProperties();

        // send browser properties
        for (Map.Entry<String, String> browserProperty : BROWSER_PROPERTIES.entrySet()) {
            cnx.setRequestProperty(browserProperty.getKey(), browserProperty.getValue());
        }
        // send cookies
        String cookieHeader = createCookieHeader(cnx);
        if (!cookieHeader.isEmpty()) {
            cnx.setRequestProperty("Cookie", cookieHeader);
        }
    }

    private static String createCookieHeader(URLConnection cnx) {
        String host = cnx.getURL().getHost();
        StringBuilder cookiesHeader = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> domainCookies : COOKIES.entrySet()) {
            if (host.endsWith(domainCookies.getKey())) {
                for (Map.Entry<String, String> cookie : domainCookies.getValue().entrySet()) {
                    cookiesHeader.append(cookie.getKey());
                    cookiesHeader.append("=");
                    cookiesHeader.append(cookie.getValue());
                    cookiesHeader.append(";");
                }
            }
        }
        if (cookiesHeader.length() > 0) {
            // remove last ; char
            cookiesHeader.deleteCharAt(cookiesHeader.length() - 1);
        }
        return cookiesHeader.toString();
    }

    private static void readHeader(URLConnection cnx) {
        // read new cookies and update our cookies
        for (Map.Entry<String, List<String>> header : cnx.getHeaderFields().entrySet()) {
            if ("Set-Cookie".equals(header.getKey())) {
                for (String cookieHeader : header.getValue()) {
                    String[] cookieElements = cookieHeader.split(" *; *");
                    if (cookieElements.length >= 1) {
                        String[] firstElem = cookieElements[0].split(" *= *");
                        String cookieName = firstElem[0];
                        String cookieValue = firstElem.length > 1 ? firstElem[1] : null;
                        String cookieDomain = null;
                        // find cookie domain
                        for (int i = 1; i < cookieElements.length; i++) {
                            String[] cookieElement = cookieElements[i].split(" *= *");
                            if ("domain".equals(cookieElement[0])) {
                                cookieDomain = cookieElement.length > 1 ? cookieElement[1] : null;
                                break;
                            }
                        }
                        if (cookieDomain == null) {
                            // if domain isn't set take current host
                            cookieDomain = cnx.getURL().getHost();
                        }
                        Map<String, String> domainCookies = COOKIES.get(cookieDomain);
                        if (domainCookies == null) {
                            domainCookies = new HashMap<String, String>();
                            COOKIES.put(cookieDomain, domainCookies);
                        }
                        // add or replace cookie
                        domainCookies.put(cookieName, cookieValue);
                    }
                }
            }
        }
    }

    private static Charset getCharset(URLConnection cnx) {
        Charset charset = null;
        // content type will be string like "text/html; charset=UTF-8" or "text/html"
        String contentType = cnx.getContentType();
        if (contentType != null) {
            // changed 'charset' to 'harset' in regexp because some sites send 'Charset'
            Matcher m = Pattern.compile("harset *=[ '\"]*([^ ;'\"]+)[ ;'\"]*").matcher(contentType);
            if (m.find()) {
                String encoding = m.group(1);
                try {
                    charset = Charset.forName(encoding);
                } catch (UnsupportedCharsetException e) {
                    // there will be used default charset
                }
            }
        }
        if (charset == null) {
            charset = Charset.defaultCharset();
        }

        return charset;
    }

    public static String getProxyHost() {
        return proxyHost;
    }

    public static void setProxyHost(String myProxyHost) {
        WebBrowser.proxyHost = myProxyHost;
    }

    public static int getProxyPort() {
        return proxyPort;
    }

    public static void setProxyPort(int myProxyPort) {
        WebBrowser.proxyPort = myProxyPort;
    }

    public static String getProxyUsername() {
        return proxyUsername;
    }

    public static void setProxyUsername(String myProxyUsername) {
        WebBrowser.proxyUsername = myProxyUsername;
    }

    public static String getProxyPassword() {
        return proxyPassword;
    }

    public static void setProxyPassword(String myProxyPassword) {
        WebBrowser.proxyPassword = myProxyPassword;

        if (proxyUsername != null) {
            proxyEncodedPassword = proxyUsername + ":" + proxyPassword;
            proxyEncodedPassword = "Basic " + new String(Base64.encodeBase64((proxyUsername + ":" + proxyPassword).getBytes()));
        }
    }

    public static int getWebTimeoutConnect() {
        return webTimeoutConnect;
    }

    public static int getWebTimeoutRead() {
        return webTimeoutRead;
    }

    public static void setWebTimeoutConnect(int webTimeoutConnect) {
        WebBrowser.webTimeoutConnect = webTimeoutConnect;
    }

    public static void setWebTimeoutRead(int webTimeoutRead) {
        WebBrowser.webTimeoutRead = webTimeoutRead;
    }
}
