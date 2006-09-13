/**
 * AcknowledgeMode.java
 */
package org.grouter.common.jms;

/**
 * Maps to {@link javax.jms.Session} acknowledge modes, but enforces user to provide only
 * one of these and not just any number. NONE is used when we don not
 * expllicitly want any acknowledge mode - e.g. when we are using a transactional
 * approach to ack messages through commit/rollback.
 */
public enum AcknowledgeMode
{
    AUTO_ACKNOWLEDGE, DUPS_OK_ACKNOWLEDGE, CLIENT_ACKNOWLEDGE, NONE
};
