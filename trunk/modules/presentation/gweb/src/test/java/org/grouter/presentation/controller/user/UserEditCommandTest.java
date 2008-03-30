package org.grouter.presentation.controller.user;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.Address;
import junit.framework.TestCase;

/**
 * Unit test the UserEditCommand
 *
 * @author Georges Polyzois
 * @see UserEditCommand
 */
public class UserEditCommandTest extends TestCase
{
    private User user;
    private Address address;
    private ServletRequestDataBinder binder;
    private MockHttpServletRequest request;

    public void setUp() throws Exception
    {
        user = new User();
        address = new Address();
        binder = new ServletRequestDataBinder(user, "nameBean");
        request = new MockHttpServletRequest();
    }

    public void testSimpleBind()
    {
        // just like /servlet?firstName=Anya&lastName=Lala
        request.addParameter("firstName", "Georges");
        request.addParameter("lastName", "Polyzois");
        request.addParameter("address.email", "gepo01@gmail.com");
        //request.addParameter("roles", "Poly?zois");
        binder.bind(request);    // performed by BaseCommandController

        // on submit so you don’t have to
        assertEquals("Georges", user.getFirstName());  // true!
        assertEquals("Polyzois", user.getLastName());   // true!
        assertEquals("gepo01@gmail.com", user.getAddress().getEmail());  // true!
    }
}

