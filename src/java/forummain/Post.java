package forummain;

import forummain.Topic;
import java.util.Date;

public class Post {
    
    private String author;
    private String message;
    private long time;
    private int topicId;
    
    public Post(String author, String message, Topic topic) {
        this.author = author;
        this.message = message;
        this.time = new Date().getTime();
        topic.setLastPost(this.time);
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}
