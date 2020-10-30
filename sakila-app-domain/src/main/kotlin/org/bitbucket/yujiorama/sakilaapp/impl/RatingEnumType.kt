package org.bitbucket.yujiorama.sakilaapp.impl

import org.bitbucket.yujiorama.sakilaapp.model.Rating
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.type.EnumType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

class RatingEnumType : EnumType<Rating?>() {
    @Throws(SQLException::class)
    override fun nullSafeGet(
        rs: ResultSet,
        names: Array<String>,
        session: SharedSessionContractImplementor,
        owner: Any
    ): Any {
        val v = rs.getObject(names[0])
        return Rating.of(v.toString())
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(
        st: PreparedStatement,
        value: Any,
        index: Int,
        session: SharedSessionContractImplementor
    ) {
        st.setObject(
            index,
            (value as Rating).label,
            Types.OTHER
        )
    }
}
