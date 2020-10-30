package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Rating(
    @get:JsonValue val label: String
) {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    companion object {
        @JsonCreator
        fun of(label: String): Rating {
            return valueOf(label.replace("-", "_"))
        }
    }
}
