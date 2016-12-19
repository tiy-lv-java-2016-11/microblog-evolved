package microblog;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by melmo on 12/14/16.
 */
public class User {
    private int id = 0;
    private String username;
    private String password;
    private List<Message> messages = new ArrayList<>();

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

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
    public void addMessage(int id, Date created, String content){
        this.messages.add(new Message(id, created, content));
    }

    /* *
     * Deletes the message with 'id' in the 'messages' array
     * */
    public void removeMessage(int id){
        Message m = this.getMessage(id);
        this.messages.remove(m);
    }
    /* *
     * Gets message with 'id' and overwrites the message 'content'
     * */
    public void updateMessage(int id, String content) {
        Message m = this.getMessage(id);
        m.setContent(content);
    }

    /* *
     * Returns Message object with 'id'
     * */
    public Message getMessage(int id){
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
        ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = " + username);
        if (result.next()){
            String pass = result.getString("password");
            int id = result.getInt("id");
            User user = new User(id, username, pass);
            user.loadMessages(connection);
            return user;
        }
        return null;
    }

    public static void loadUsers(Map<String, User> users) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql:messages");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM users");
        while (rs.next()){
            int id = rs.getInt("user_id");
            String name = rs.getString("username");
            String pass = rs.getString("password");
            User user = new User(id, name, pass);
            user.loadMessages(connection);
            users.put(name, user);
        }
    }

    public void loadMessages(Connection conn) throws SQLException {
        int userId = this.getId();
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM messages WHERE user_id = " + userId);
        while (result.next()){
            int mId = result.getInt("message_id");
            Date createdAt = result.getDate("created_at");
            String content = result.getString("content");
            this.addMessage(mId, createdAt, content);
        }
    }

    public void saveUser() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql:messages");
        if (this.id<1){ // Insert
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)");
            insert.setString(1, this.username);
            insert.setString(2, this.password);
        }
    }

    public void saveMessages() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql:messages");
        for (Message m : messages){
            if (m.getId()<1){ // Insert
                PreparedStatement insert = connection.prepareStatement(
                        "INSERT INTO messages (user_id, content) VALUES (?, ?)");
                insert.setInt(1, this.id);
                insert.setString(2, m.getContent());
            }
            else { // Update
                PreparedStatement insert = connection.prepareStatement(
                        "UPDATE messages (content) VALUES (?) WHERE message_id = " + m.getId());
                insert.setString(1, m.getContent());
            }
        }
    }
}
