package microblog;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by melmo on 12/14/16.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private List<Message> messages = new ArrayList<>();

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /* *
     * Adds 'message' to 'messages' array
     * */
    public void addMessage(Message message){
        this.messages.add(message);
    }

    /* *
     * Deletes the message with 'id' in the 'messages' array
     * */
    public void removeMessage(int id){
        Message m = this.getMessage(id);
        this.messages.remove(m);
    }
    /* *
     * Deletes the message with 'id' in the 'messages' array
     * Creates new message with 'id' and 'content' and adds to 'messages' array
     * */
    public void updateMessage(int id, String content) {
        Message m = this.getMessage(id);
        messages.remove(m);
        messages.add(new Message(id, content));
    }

    /* *
     * Returns Message object with 'id'
     * */
    private Message getMessage(int id){
        for (Message m : this.messages){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }

    public static User loadUser(String username) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql:messages");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM users");
        while (result.next()){
            String rName = result.getString("username");
            if (username.equals(rName)){
                String rPass = result.getString("password");
                int rId = result.getInt("id");
                return new User(rId, rName, rPass);
            }
        }
        return null;
    }
}
