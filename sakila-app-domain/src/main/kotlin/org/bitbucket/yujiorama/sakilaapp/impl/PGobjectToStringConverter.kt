package org.bitbucket.yujiorama.sakilaapp.impl

import org.postgresql.util.PGobject
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class PGobjectToStringConverter : Converter<PGobject, String> {

    override fun convert(source: PGobject): String? = source.value
}
