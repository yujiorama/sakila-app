package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String label;

    Rating(final String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return this.label;
    }

    @JsonCreator
    public static Rating of(final String label) {

        return Rating.valueOf(label.replace("-", "_"));
    }
}
