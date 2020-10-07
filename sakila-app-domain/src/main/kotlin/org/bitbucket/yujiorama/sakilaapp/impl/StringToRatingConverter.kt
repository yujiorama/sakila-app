package org.bitbucket.yujiorama.sakilaapp.impl

import org.bitbucket.yujiorama.sakilaapp.model.Rating
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StringToRatingConverter : Converter<String, Rating> {

    override fun convert(source: String): Rating? = Rating.deserialize(source)
}
