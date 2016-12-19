import microblog.*;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by melmo on 12/16/16.
 */
public class TestUser {

    User user;

    @Test
    public void testLoadUser() throws SQLException {
        user = User.loadUser("username");

        assertEquals("The password is not correct", "password", user.getPassword());
        assertEquals("The user id is not correct", 1, user.getId());
    }
}
