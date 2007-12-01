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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailParseException;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * A plain text email could look like:
 * <p/>
 * To: recipient@domain.com
 * From: sender@domain.com
 * Subject: Plain Text email
 * Content-Type: text/plain; charset="us-ascii"
 * <p/>
 * And a html based email would look like:
 * <p/>
 * To: recipient@domain.com
 * From: sender@domain.com
 * Subject: HTML email.
 * Content-Type: text/html; charset="us-ascii"
 * <p/>
 * If we want to handle html emails and fallback to using plain text emails it would look like:
 * Date: Tue, 01 Feb 1971 09:48:30 -0700
 * MIME-Version: 1.0
 * Message-ID: <2.4.10534340032@localhost>
 * Content-Transfer-Encoding: quoted-printable
 * Content-Type: multipart/mixed; charset=us-ascii;
 * boundary=___BoUnDaRy_1057078110032.797.291273273055
 * From: sender@domain.com
 * To: recipient@domain.com
 * Subject: multipart mixed email.
 * <p/>
 * --___Boundary_1057078110032.797.29127327305
 * Content-Type: multipart/alternative;
 * boundary=___Boundary_1057078110032.355.65337159541633
 * <p/>
 * --___Boundary_1057078110032.355.65337159541633
 * Content-Type: text/plain
 * <p/>
 * This is plain text
 * <p/>
 * --___Boundary_1057078110032.355.65337159541633
 * Content-Type: text/html
 * <b>HTML text</b>
 * --___Boundary_1057078110032.355.65337159541633--
 * <p/>
 * --___Boundary_1057078110032.797.291273273055--
 *
 * @author Georges Polyzois
 */
public class MailHandler
{
    private String templateRoot;
    private static final String ENCODING = "iso-8859-1";
    private static Logger logger = Logger.getLogger(MailHandler.class);
    private static final String PRE_HTML_HEADER = "<HTML><HEAD><META HTTP-EQUIV='Content-Type' " + "CONTENT='text/html; " +
            "charset=iso-8859-1'><TITLE>\n";
    private static final String POST_HTML_HEADER = "</TITLE></HEAD>\n";
    private static final String END_HTML = "</HTML>";





    public void sendEmail(MailDto mailDto)
    {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessageContent = new MimeMessage(session);


        try
        {
            createHeader(mimeMessageContent, mailDto.getTo(), mailDto.getSubject(),
                    mailDto.getFrom(), mailDto.getSenderNote());

            createHtmlAndPlainTextBody(mimeMessageContent, mailDto);

        }
        catch (Exception e)
        {
            logger.error(e, e);
            throw new MailParseException("Failed to create message", e);
        }

        // send



    }


    /**
     * Creates a multipart email with html and plain text email body to fallback to if
     * email client do not handle html base emails.
     *
     * @param mimeMessage
     * @param dto
     * @throws MessagingException
     */
    private void createHtmlAndPlainTextBody(MimeMessage mimeMessage, MailDto dto)
            throws MessagingException
    {
        if (StringUtils.isNotEmpty(dto.getHtmlTextBody()))
        {
            mimeMessage.setContentID(MailDto.CONTENT_TYPE_MULTIPART_ALTERNATIVE);

            // Create your text message part
            BodyPart plainBodyPart = new MimeBodyPart();
            plainBodyPart.setContent(dto.getPlainTextBody(), MailDto.CONTENT_TYPE_TEXT_PLAIN);

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");
            multipart.addBodyPart(plainBodyPart);

            StringBuilder sb = new StringBuilder();
            sb.append(PRE_HTML_HEADER);
            sb.append(dto.getSubject()).append("\n");
            sb.append(POST_HTML_HEADER);
            sb.append(dto.getHtmlTextBody());
            sb.append(END_HTML);

            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(sb.toString(), MailDto.CONTENT_TYPE_TEXT_HTML);

            // Add html part to multi part
            multipart.addBodyPart(htmlBodyPart);
            mimeMessage.setContent(multipart);
        }
        else
        {
            mimeMessage.setSubject( dto.getSubject() );
            mimeMessage.setText( dto.getPlainTextBody() );
            mimeMessage.setContent( dto.getPlainTextBody(), MailDto.CONTENT_TYPE_TEXT_PLAIN );
        }

    }


    /**
     * @param mimeMessage
     * @param to
     * @param subject
     * @param from
     * @param senderNote
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private void createHeader(MimeMessage mimeMessage, MailDto mailDto
    )
            throws UnsupportedEncodingException, MessagingException
    {

        //from
        InternetAddress fromAddress = new InternetAddress(mailDto.getFrom());
        fromAddress.setPersonal(mailDto.getSenderNote() , ENCODING);
        mimeMessage.setSender(fromAddress);

        //to

        List<String> senders = mailDto.getTo();
        InternetAddress[] toAddress = getRecipients(senders);

        List<String> sendersCc = mailDto.getCc();
        InternetAddress[] toAddressCc = getRecipients(sendersCc);

        List<String> sendersBcc = mailDto.getBcc();
        InternetAddress[] toAddressBcc = getRecipients(sendersBcc);


        mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);
        mimeMessage.setRecipients(Message.RecipientType.CC, toAddressCc);
        mimeMessage.setRecipients(Message.RecipientType.BCC, toAddressBcc);

        //subject
        mimeMessage.setSubject(mailDto.getSubject(), ENCODING);

        //sent date
        mimeMessage.setSentDate(new Date());
    }

    /**
     * Helper method to create array of recipients from list.
     *
     * @param senders
     * @return array of recipients
     * @throws AddressException
     */
    private InternetAddress[] getRecipients(final List<String> senders)
            throws AddressException
    {
        InternetAddress[] toAddress = new InternetAddress[senders.size()];
        int i = 0;
        for (String sender : senders)
        {
            toAddress[i] =  new InternetAddress(sender);
        }
        return toAddress;
    }

    private void initVelocity()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        props.setProperty("file.resource.loader.path", "/temp/velocity");

        try
        {
            velocityEngine.init(props);
        } catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Map context = new HashMap();
        context.put("id", "Hello");

        VelocityContext velocityContext = new VelocityContext();


    }
}
