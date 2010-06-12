/*
 *      Copyright (c) 2004-2009 YAMJ Members
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author altman.matthew (Original)
 * @author stuart.boston
 */
public class XMLHelper {

    public static XMLEventReader getEventReader(String url) throws IOException, XMLStreamException {
        InputStream in = (new URL(url)).openStream();
        return XMLInputFactory.newInstance().createXMLEventReader(in);
    }
    
    public static void closeEventReader(XMLEventReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException ex) {
                System.err.println("ERROR: TheMovieDb API -> " + ex.getMessage());
            }
        }
    }

    public static String getCData(XMLEventReader r) throws XMLStreamException {
        StringBuffer sb = new StringBuffer();
        while (r.peek().isCharacters()) {
            sb.append(r.nextEvent().asCharacters().getData());
        }
        return sb.toString().trim();
    }
    
    public static int parseInt(XMLEventReader r) throws XMLStreamException {
        int i = 0;
        String val = getCData(r);
        if (val != null && !val.isEmpty()) {
            i = Integer.parseInt(val);
        }
        return i;
    }
}
