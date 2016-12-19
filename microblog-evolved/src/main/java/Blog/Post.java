package Blog;

import java.util.Date;
import java.util.List;

/**
 * Created by sparatan117 on 12/14/16.
 */
public class Post {
    private int id;
    private String message;
    private Date date;


    public Post(int id, String message) {
        this.message = message;
        this.id = id;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static int addId(List<Post> messages){
        int newID = 1;
        for(Post message: messages){
            int ID = message.getId();
            if (ID > newID){
                newID = ID;
            }
            if (ID == newID){
                newID++;
            }
        }
        return newID;
    }
    @Override
    public String toString(){
        return this.message;
    }
}

