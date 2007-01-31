package org.grouter.common.security;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Controller used for redirecting an authenticated user or nu authenticated user to a resource.
 * Normally if a user tries to access a certain url he/she will be presetnted with a logn form
 * as specified by the login attribute of this class. Upon a succesfull authentication the user
 * will be forwarded to the resource url.
 * If unsuccesfull attempt is made then the user is redirected to a login denied form as specified
 * by the loginDenied attribute.
 * When user logs out the session data are removed and they are redirected to the logedout url. 
 *
 * @author Georges Polyzois
 */
public class SecurityController extends MultiActionController
{
    private String login;
    private String logedout;
    private String loginDenied;

    public void setLogin(String login)
    {
        this.login = login;
    }

    public void setLogedout(String logedout)
    {
        this.logedout = logedout;
    }

    public void setLoginDenied(String loginDenied)
    {
        this.loginDenied = loginDenied;
    }

    public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        return new ModelAndView( this.login );
    }


    public ModelAndView loggedOut(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        return new ModelAndView( this.logedout );
    }


    public ModelAndView loginDenied(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        return new ModelAndView( this.loginDenied );
    }
}

