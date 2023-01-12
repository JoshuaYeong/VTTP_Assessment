package vttpassessment.backend.models;

import java.io.StringReader;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Post {

    private String postingId;
    private Date postingDate;
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String image;

    public String getPostingId() {
        return postingId;
    }
    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }
    public Date getPostingDate() {
        return postingDate;
    }
    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
    public static Post create(SqlRowSet rs) {
        Post p = new Post();
        p.setPostingId(rs.getString("posting_id"));
        p.setPostingDate(rs.getDate("posting_date"));
        p.setName(rs.getString("name"));
        p.setEmail(rs.getString("email"));
        p.setPhone(rs.getString("phone"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setImage(rs.getString("image"));
        return p;
    }

    public static Post createPost(String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject obj = reader.readObject();

        Post p = new Post();
        p.setName(obj.getString("name"));
        p.setEmail(obj.getString("email"));
        p.setPhone(obj.getString("phone"));
        p.setTitle(obj.getString("title"));
        p.setDescription(obj.getString("description"));
        return p;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("postingId", postingId)
            .add("postingDate", postingDate.toString())
            .add("name", name)
            .add("email", email)
            .add("phone", phone)
            .add("title", title)
            .add("description", description)
            .add("image", image)
            .build();
    }
    
}
