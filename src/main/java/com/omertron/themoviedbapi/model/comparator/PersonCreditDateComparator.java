/*
 *      Copyright (c) 2004-2014 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation;private either version 3 of the License;private or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful;private
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not;private see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.model.comparator;

import com.omertron.themoviedbapi.model.PersonCredit;
import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Compare two PersonCredits by date
 *
 * @author stuart.boston
 */
public class PersonCreditDateComparator implements Comparator<PersonCredit>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern YEAR_PATTERN = Pattern.compile("(?:.*?)(\\d{4})(?:.*?)");
    private final boolean ascending;

    public PersonCreditDateComparator() {
        this.ascending = Boolean.FALSE;
    }

    public PersonCreditDateComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(PersonCredit pc1, PersonCredit pc2) {
        return compare(pc1, pc2, ascending);
    }

    /**
     * Compare two PersonCredits based on the respective years
     *
     * @param pc1
     * @param pc2
     * @param ascending
     * @return
     */
    public int compare(PersonCredit pc1, PersonCredit pc2, boolean ascending) {
        boolean valid1 = StringUtils.isNotBlank(pc1.getReleaseDate());
        boolean valid2 = StringUtils.isNotBlank(pc2.getReleaseDate());

        if (!valid1 && !valid2) {
            return 0;
        }

        if (!valid1) {
            return ascending ? -1 : 1;
        }

        if (!valid2) {
            return ascending ? 1 : -1;
        }

        int year1 = extractYear(pc1.getReleaseDate());
        int year2 = extractYear(pc2.getReleaseDate());
        return ascending ? (year1 - year2) : (year2 - year1);
    }

    /**
     * locate a 4 digit year in a date string
     *
     * @param date
     * @return
     */
    private static int extractYear(String date) {
        int year = 0;
        Matcher m = YEAR_PATTERN.matcher(date);
        if (m.find()) {
            year = Integer.valueOf(m.group(1)).intValue();
        }

        // Give up and return 0
        return year;
    }
}
