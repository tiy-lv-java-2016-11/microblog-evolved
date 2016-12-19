package Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sparatan117 on 12/14/16.
 */
public class User {
    private int id;
    private String loginName;
    private String password;
    private List<Post> messages = new ArrayList<>();


    public User(String loginName, String password) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<Post> getMessages() {
        return messages;
    }

    public void setMessages(List<Post> messages) {
        this.messages = messages;
    }

    public void addMessage(Post message){
        this.messages.add(message);
    }

    public Post getMessage(int id){
        for (Post message: this.messages){
            if(message.getId()==id){
                return message;
            }
        }
        return null;
    }

    public void deleteMessage(int ID){
        Post message = this.getMessage(id);
        this.messages.remove(message);
    }
    public void updateMessage(int ID, String message){
        Post m = this.getMessage(id);
        messages.remove(m);
        messages.add(new Post(id, message));
    }


}
