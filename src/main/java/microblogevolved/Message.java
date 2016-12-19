package microblogevolved;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by darionmoore on 12/17/16.
 */
public class Message {
    private String message;
    private static Integer ID;
    private int createdAt;
    private static ArrayList<Message> messages = new ArrayList();

    public Message(String message, Integer ID, int createdAt) {
        this.message = message;
        this.ID = ID;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    static void messageId() {
        HashMap<Integer, ArrayList<Message>> messageList = new HashMap<>();
        for (Message message : messages) {
            Integer currentID = ID++;
            if (currentID == null) {
                currentID++;
            } else {
                message.getID();
                messageList.put(currentID, messages);
            }

        }
    }





}
