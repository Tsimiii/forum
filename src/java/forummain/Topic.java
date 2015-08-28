package forummain;

import forummain.Forum;
import forummain.Post;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Topic {
    
    private List<Post> posts = new ArrayList<>();
    
    private String subject;
    private String author;
    private int replies;
    private long lastPost;
    private int id;
    
    private Forum forum;
      
    public Topic(int id, String subject, String author, Forum forum) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        replies = posts.size();
        lastPost = new Date().getTime();
        this.forum = forum;
    }
    
    public Post writePost(String author, String message) {
        Post post = new Post(author, message, this);
        posts.add(post);
        replies++;
        increasePostNumInForum();
        forum.setLastPost(lastPost);
        return post;
    }
    
    public void increasePostNumInForum() {
        forum.setPostNum(forum.getPostNum() + 1);
    }

    public void setLastPost(long lastPost) {
        this.lastPost = lastPost;
    }

    public int getReplies() {
        return replies;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public long getLastPost() {
        return lastPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
