package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Rating(private val label: String) {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    @JsonValue
    fun serialize(): String {
        return this.label
    }

    companion object {

        @JsonCreator
        fun deserialize(label: String): Rating {

            return enumValueOf(label.replace("-", "_"))
        }
    }
}
