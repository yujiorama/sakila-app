package org.bitbucket.yujiorama.sakilaapp.impl;

import org.bitbucket.yujiorama.sakilaapp.model.Rating;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class RatingEnumType extends EnumType<Rating> {

    @Override
    public Object nullSafeGet(
        final ResultSet rs,
        final String[] names,
        final SharedSessionContractImplementor session,
        final Object owner
    ) throws SQLException {

        final var v = rs.getObject(names[0]);
        return Rating.of(v.toString());
    }

    @Override
    public void nullSafeSet(
        final PreparedStatement st,
        final Object value,
        final int index,
        final SharedSessionContractImplementor session
    ) throws HibernateException, SQLException {

        st.setObject(
            index,
            value != null ?
                ((Rating) value).getLabel() :
                null,
            Types.OTHER
        );
    }
}
