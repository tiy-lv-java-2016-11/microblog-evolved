package microblog;

import java.util.Date;
import java.util.List;

/**
 * Created by melmo on 12/14/16.
 */
public class Message {
    private int id = 0;
    private String content;
    private Date createdAt;

    public static int getNextId(List<Message> messages){
        int currentId=1;
        for (Message m : messages){
            int id = m.getId();
            if (id > currentId){
                currentId = id;
            }
            if (id == currentId){
                currentId++;
            }
        }
        return currentId;
    }

    public Message(String content){
        this.content = content;
    }

    public Message(int id, Date createdAt, String content) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
