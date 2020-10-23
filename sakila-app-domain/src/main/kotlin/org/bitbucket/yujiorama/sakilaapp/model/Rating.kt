package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

enum class Rating(
    val label: String,
) {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    companion object {
        fun of(label: String): Rating =
            valueOf(label.replace("-", "_"))
    }
}

@WritingConverter
class RatingToStringConverter : Converter<Rating, String> {

    override fun convert(source: Rating): String? = source.label
}

@ReadingConverter
class StringToRatingConverter : Converter<String, Rating> {

    override fun convert(source: String): Rating? = Rating.of(source)
}
