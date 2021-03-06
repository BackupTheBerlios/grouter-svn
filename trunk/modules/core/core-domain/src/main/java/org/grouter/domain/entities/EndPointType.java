package org.grouter.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.*;

/**
 * @author Georges Polyzois
 */

@Entity
@Table(name = "endpoint_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EndPointType extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    Long id;

    @Column(name = "name")
    String name;

    public static EndPointType FILE_WRITER = new EndPointType(1L, "fileWriter");
    public static EndPointType FILE_READER = new EndPointType(2L, "fileReader");
    public static EndPointType FTP_READER = new EndPointType(3L, "ftpReader");
    public static EndPointType FTP_WRITER = new EndPointType(4L, "ftpWriter");
    public static EndPointType JMS_READER = new EndPointType(5L, "jmsReader");
    public static EndPointType JMS_WRITER = new EndPointType(6L, "jmsWriter");
    public static EndPointType HTTP_READER = new EndPointType(7L, "httpReader");

    public static Map<Long, EndPointType> values = new HashMap<Long, EndPointType>();

    static
    {
        values.put(FILE_WRITER.getId(), FILE_WRITER);
        values.put(FILE_READER.getId(), FILE_READER);
        values.put(FTP_READER.getId(), FTP_READER);
        values.put(FTP_WRITER.getId(), FTP_WRITER);
        values.put(JMS_READER.getId(), JMS_READER);
        values.put(JMS_WRITER.getId(), JMS_WRITER);
        values.put(HTTP_READER.getId(), HTTP_READER);
    }

    public static EndPointType valueOf(Long id)
    {
        return values.get(id);
    }

    /**
     * Util method.
     *
     * @return lsit of NodeStatus
     */
    public static List<EndPointType> values()
    {
        return Collections.unmodifiableList(new ArrayList<EndPointType>(values.values()));
    }

    public EndPointType(Long id, String name)
    {
        this.name = name;
        this.id = id;
    }


    public EndPointType()
    {
    }

    public String getName()
    {
        return name;
    }


    public void setName(final String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }


}
