/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.grouter.common.mail;

import java.util.List;

/**
 * @author Georges Polyzois
 */


public class MailDto
{
    public final static String CONTENT_TYPE_TEXT_PLAIN = "text/plain; charset=iso-8859-1";
    public final static String CONTENT_TYPE_TEXT_HTML = "text/html; charset=iso-8859-1";
    public final static String CONTENT_TYPE_MULTIPART_ALTERNATIVE = "multipart/alterntive; charset=iso-8859-1";
    public final static String CONTENT_TYPE_MULTIPART_MIXED = "multipart/mixed; charset=iso-8859-1";

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String from;
    private String plainTextBody;
    private String htmlTextBody;
    private String subject;
    private String extId;
    private String contentType;
    private String mailTemplateName;
    private String senderNote;

    public MailDto()
    {
    }


    public String getMailTemplateName()
    {
        return mailTemplateName;
    }

    public void setMailTemplateName(final String mailTemplateName)
    {
        this.mailTemplateName = mailTemplateName;
    }

    public String getPlainTextBody()
    {
        return plainTextBody;
    }

    public void setPlainTextBody(final String plainTextBody)
    {
        this.plainTextBody = plainTextBody;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(final String subject)
    {
        this.subject = subject;
    }

    public String getExtId()
    {
        return extId;
    }

    public void setExtId(final String extId)
    {
        this.extId = extId;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(final String contentType)
    {
        this.contentType = contentType;
    }


    public List<String> getTo()
    {
        return to;
    }

    public void setTo(final List<String> to)
    {
        this.to = to;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(final String from)
    {
        this.from = from;
    }


    public String getSenderNote()
    {
        return senderNote;
    }

    public void setSenderNote(final String senderNote)
    {
        this.senderNote = senderNote;
    }


    public String getHtmlTextBody()
    {
        return htmlTextBody;
    }

    public void setHtmlTextBody(final String htmlTextBody)
    {
        this.htmlTextBody = htmlTextBody;
    }


    public List<String> getCc()
    {
        return cc;
    }

    public void setCc(final List<String> cc)
    {
        this.cc = cc;
    }

    public List<String> getBcc()
    {
        return bcc;
    }

    public void setBcc(final List<String> bcc)
    {
        this.bcc = bcc;
    }
}
