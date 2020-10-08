package org.bitbucket.yujiorama.sakilaapp.impl;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class GenericArrayUserType<T extends Serializable> implements UserType {

    private static final int[] SQL_TYPES = {Types.ARRAY};

    private Class<T> returnedClass;

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<T> returnedClass() {
        return this.returnedClass;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session, final Object owner) throws HibernateException, SQLException {

        if (rs.wasNull()) {
            return null;
        }

        if (rs.getArray(names[0]) == null) {
            return new Integer[0];
        }

        final var array = rs.getArray(names[0]);

        //noinspection unchecked
        return array.getArray();
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SharedSessionContractImplementor session) throws HibernateException, SQLException {

        final var conn = st.getConnection();

        if (value == null) {
            st.setNull(index, SQL_TYPES[0]);
        } else {
            //noinspection unchecked
            final var array = conn.createArrayOf("integer", (Object[]) value);
            st.setArray(index, array);
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        //noinspection unchecked
        return (T) this.deepCopy(value);
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {

        if (x == null) {
            return y == null;
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {

        return x.hashCode();
    }
}
