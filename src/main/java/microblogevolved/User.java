package microblogevolved;

import java.util.HashMap;

/**
 * Created by darionmoore on 12/17/16.
 */
public class User {
    private String user;
    private String password;



    public User(String user){
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String userName) {
        this.user = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
