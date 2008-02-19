package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.daolayer.SystemDAO;
import org.grouter.domain.servicelayer.SystemService;


/**
 * The implementation of the interface {@link SystemService} uses underlying
 * generic DAOs providing transaction demarcation for the service layer.
 * <p/>
 * Client such as - gswing and gweb - uses this service layer.
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 * <p/>
 * DAOs are injected using Spring.
 *
 * @author Georges Polyzois
 */
public class SystemServiceImpl implements SystemService
{
    SystemDAO systemDAO;

    public void initIndex()
    {
        systemDAO.initSearchIndex();
    }

    public void setSystemDAO(SystemDAO systemDAO)
    {
        this.systemDAO = systemDAO;
    }
}
