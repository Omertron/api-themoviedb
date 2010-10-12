/*
 *      Copyright (c) 2004-2010 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list 
 *  
 *      Web: http://code.google.com/p/moviejukebox/
 *  
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *  
 *      For any reuse or distribution, you must make clear to others the 
 *      license terms of this work.  
 */
package com.moviejukebox.themoviedb.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.moviejukebox.themoviedb.TheMovieDb;

/**
 * Generic set of routines to process the DOM model data
 * @author Stuart.Boston
 *
 */
public class DOMHelper {
    static Logger logger = TheMovieDb.getLogger();

    /**
     * Gets the string value of the tag element name passed
     * @param element
     * @param tagName
     * @return
     */
    public static String getValueFromElement(Element element, String tagName) {
        String returnValue = "";

        try {
            NodeList elementNodeList = element.getElementsByTagName(tagName);
            Element tagElement = (Element) elementNodeList.item(0);
            NodeList tagNodeList = tagElement.getChildNodes();
            returnValue = ((Node) tagNodeList.item(0)).getNodeValue();
        } catch (Exception ignore) {
            return returnValue;
        }

        return returnValue;
    }

    /**
     * Get a DOM document from the supplied URL
     * @param url
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Document getEventDocFromUrl(String url)
            throws IOException, ParserConfigurationException, SAXException {
        Document doc = null;
        InputStream in = null;
        String webPage = null;
        
        try {
            boolean validWebPage = false;
            webPage = WebBrowser.request(url);
           
            // There seems to be an error with some of the web pages that returns garbage
            if (webPage.startsWith("<?xml version")) {
                // This looks like a valid web page
                validWebPage = true;
            } else {
                logger.fine("Error with API Call for: " + url);
                return null;
            }
            
            if (validWebPage) {
                in = new ByteArrayInputStream(webPage.getBytes("UTF-8"));
    
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(in);
                doc.getDocumentElement().normalize();
            }
        } catch (Exception error) {
            logger.fine("Error parsing: " + url);
            // Some sort of error occurred getting the data, so clear the document
            doc = null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return doc;
    }
}
