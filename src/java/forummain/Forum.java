package forummain;

import forummain.Topic;
import java.util.ArrayList;
import java.util.List;

public class Forum {
    
    private List<Topic> topics = new ArrayList<>();
    private String name;
    private int postNum = 0;
    private int topicNum = 0;
    private long lastPost;
    
    private static Forum forum = new Forum();
    
    private Forum() {}
    
    public static Forum getInstance() {
        return forum;
    }
    
    public Topic addNewTopic(String subject, String author) {
	Topic newTopic = new Topic(topicNum + 1, subject, author, this);
        topics.add(newTopic);
        topicNum++;
	return newTopic;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public void setLastPost(long lastPost) {
        this.lastPost = lastPost;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public long getLastPost() {
        return lastPost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
