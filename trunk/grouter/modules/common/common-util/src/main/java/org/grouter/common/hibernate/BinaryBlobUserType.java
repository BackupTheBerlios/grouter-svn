/**
 * BinaryBlobUserType.java
 */

package org.grouter.common.hibernate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

// Need to handle oracle jars....
// import oracle.sql.BLOB;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Try using oracle jdbc driver for Oraccle10 (works with Oracle9 also)
 * - this way you do not need this BinaryBlobUserType.</br>
 * Look up Hibernate UserType documentation to read more about UserTypes
 * and Hibernate. Basically we want to override the set method Hibernate
 * performs and do some extra queries to get a blob back from Oracle.
 * The reason for this implementation is that Oracle jdbc driver does not
 * work out of the box with Blobs - as does all other databases!!!!
 *<pre>
 * -- create table
 *
 * CREATE GLOBAL TEMPORARY TABLE TEMP_BLOB_TABLE (
   ID         NUMBER,
   TEMP_BLOB  BLOB)
   ON COMMIT DELETE ROWS;

 -- create sequence
 CREATE SEQUENCE SEQ_TEMP_BLOB_ID INCREMENT BY 1 START WITH 0 MINVALUE 0 MAXVALUE 99999999 CYCLE NOCACHE NOORDER;
 *</pre>
 *
 *
 *
 *
 * @author Georges Polyzois
 */
public class BinaryBlobUserType  implements UserType
{
    /**
     * Tupes.
     *
     * @return java.sql.Types.BLOB
     */
    public int[] sqlTypes()
    {
        return new int[]{Types.BLOB};
    }

    /**
     * Class.
     *
     * @return byte[]
     */
    public Class returnedClass()
    {
        return byte[].class;
    }

    /**
     * Responsible for dirty checking property values. Compares current with
     * previous snapshot.
     *
     *
     * @param x Object
     * @param y Object
     * @return boolean
     * @throws HibernateException
     */
    public boolean equals(Object x, Object y) throws HibernateException
    {
        return (x == y) || (x != null && y != null && (x.equals(y)));
    }

    /**
     * Get blob from resultset.
     *
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     * @param rs ResultSet
     * @param names String[]
     * @param owner Object
     * @return Object
     * @throws HibernateException
     * @throws SQLException
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws
        HibernateException, SQLException
        {
        Blob blob = rs.getBlob(names[0]);
        return blob.getBytes(1, (int) blob.length());
    }

    /**
     * Overrides set and adds functionality for Oracle.
     *
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     * @param preparedStatment PreparedStatement
     * @param value Object
     * @param index int
     * @throws HibernateException
     * @throws SQLException
     */
    public void nullSafeSet(PreparedStatement preparedStatment, Object value, int index) throws
    HibernateException, SQLException
    {
        int tempBlobId = -1;
        Connection con = preparedStatment.getConnection();
        Statement sta = null;
        ResultSet rs = null;
        String sql = null;
        try
        {
            sql = "select seq_temp_blob_id.nextval from dual";
            sta = con.createStatement();
            rs = sta.executeQuery(sql);
            rs.next();
            tempBlobId = rs.getInt(1);
        } catch (SQLException ex)
        {
            rs.close();
            sta.close();
        }
        finally
        {
            try
            {
                rs.close();
                sta.close();
            } catch (SQLException ex1)
            {
                //ignore
            }
        }

        try
        {
            sta = con.createStatement();
            sql = "insert into temp_blob_table (id, temp_blob) values("
                  + tempBlobId + ", empty_blob())";
            sta.executeUpdate(sql);
        } catch (SQLException ex)
        {
            rs.close();
            sta.close();
        }
        finally
        {
            try
            {
                rs.close();
                sta.close();
            } catch (SQLException ex1)
            {
                //ignore
            }
        }

   // ORACLE     BLOB tempBlob = null;
        OutputStream tempBlobWriter = null;
        try
        {
            sta = con.createStatement();
            sql = "select temp_blob from temp_blob_table where id=" +
                  tempBlobId
                  + " for update";
            rs = sta.executeQuery(sql);
            rs.next();
   // ORACLE         tempBlob = (BLOB) rs.getBlob(1); //Oracle class!!
   // ORACLE         tempBlobWriter = tempBlob.getBinaryOutputStream();
            try
            {
                tempBlobWriter.write((byte[]) value);
            } catch (IOException ioe)
            {
                throw new HibernateException(ioe);
            }
            finally
            {
                try
                {
                    tempBlobWriter.flush();
                    tempBlobWriter.close();
   // ORACLE                 tempBlob.close();
                } catch (IOException ex2)
                {
                    //ignore
                }
            }
        } catch (SQLException ex)
        {
            rs.close();
            sta.close();
        }
        finally
        {
            try
            {
                rs.close();
                sta.close();
            } catch (SQLException ex1)
            {
                //ignore
            }
        }

        try
        {
   // ORACLE         preparedStatment.setBlob(index, tempBlob);
            preparedStatment.setBlob(index, null);  // Just so that it comiles...
        } catch (SQLException ex)
        {
            preparedStatment.close();        }
        finally
        {
            try
            {
                preparedStatment.close();
            } catch (SQLException ex1)
            {
                //ignore
            }
        }
    }

    /**
     * Performs a deep copy.
     *
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     * @param value Object
     * @return Object
     * @throws HibernateException
     */
    public Object deepCopy(Object value) throws HibernateException
    {
        if (value == null)
        {
            return null;
        }
        byte[] bytes = (byte[]) value;
        byte[] result = new byte[bytes.length];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        return result;
    }

    /**
     * Mutable or not.
     *
     * @return false
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable()
    {
        return false;
    }

    /**
     * Hash.
     *
     * @param object Object
     * @return int
     * @throws HibernateException
     */
    public int hashCode(Object object) throws HibernateException
    {
        return 0;
    }

    /**
     * Overrides.
     *
     * @param object Object
     * @return Serializable
     * @throws HibernateException
     */
    public Serializable disassemble(Object object) throws HibernateException
    {
        return null;
    }

    /**
     * Overrides.
     *
     * @param serializable Serializable
     * @param object Object
     * @return Object
     * @throws HibernateException
     */
    public Object assemble(Serializable serializable, Object object) throws HibernateException
    {
        return null;
    }

    /**
     * Overrides.
     *
     * @param object Object
     * @param object1 Object
     * @param object2 Object
     * @return Object
     * @throws HibernateException
     */
    public Object replace(Object object, Object object1, Object object2) throws HibernateException
    {
        return null;
    }

}
