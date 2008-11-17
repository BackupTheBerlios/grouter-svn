package org.grouter.domain.service.spring;

import org.grouter.domain.entities.UserState;
import org.grouter.domain.service.UserService;


/**
 * Concordion fixture for User service.
 *
 * @author Georges Polyzois
 */
public class UserServiceConcordionTest extends AbstractConcordionTestCase {
    private Long USER_ID = -1L;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;


    public String blockUser(Long userId, Long fromState, Long toState) {

        try {
            userService.changeState(userId, UserState.values.get(fromState));
            flushSession();
            userService.changeState(userId, UserState.values.get(toState));
            flushSession();
        } catch (Exception e) {

            return "Change state failed:" + e.getMessage();
        }


        int result = jdbcTemplate.queryForInt("SELECT user_state_fk FROM user WHERE id = ?", new Object[]{userId});
        // assertEquals( 4, result);

        return UserState.values.get(new Long(result)).getName();

    }


}
