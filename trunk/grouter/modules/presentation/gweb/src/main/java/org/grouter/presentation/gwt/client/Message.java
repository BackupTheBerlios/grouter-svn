package org.grouter.presentation.gwt.client;

import java.util.Date;

/**
 *
 * @author Georges Polyzois
 */
public class Message
{
    String id;
    Date creationTimestamp;
    String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
