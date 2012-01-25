/*
 *      Copyright (c) 2004-2012 YAMJ Members
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
package com.moviejukebox.themoviedb.wrapper;

import com.moviejukebox.themoviedb.model.Translation;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;

/**
 *
 * @author Stuart
 */
public class WrapperTranslations {
    /*
     * Logger
     */

    private static final Logger logger = Logger.getLogger(WrapperTranslations.class);
    /*
     * Properties
     */
    private int id;
    private List<Translation> translations;

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setId(int id) {
        this.id = id;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public int getId() {
        return id;
    }

    public List<Translation> getTranslations() {
        return translations;
    }
    //</editor-fold>

    /**
     * Handle unknown properties and print a message
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        logger.warn(sb.toString());
    }
}
