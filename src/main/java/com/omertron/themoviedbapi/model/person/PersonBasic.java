package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

/**
 * Basic information about a person
 *
 * @author stuart.boston
 */
public class PersonBasic extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonBasic other = (PersonBasic) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!StringUtils.equals(this.name, other.name)) {
            return false;
        }
        return !StringUtils.equals(this.profilePath, other.profilePath);
    }

}
