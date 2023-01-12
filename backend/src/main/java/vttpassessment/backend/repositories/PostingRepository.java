package vttpassessment.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttpassessment.backend.models.Post;

@Repository
public class PostingRepository {

    private static final String SQL_INSERT_POST = "insert into postings (posting_id, posting_date, name, email, phone, title, description, image) values (?,?,?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate template;

    public boolean insertPost(Post post) {

        int added;

        added = template.update(SQL_INSERT_POST, post.getPostingId(), post.getPostingDate(), post.getName(), post.getEmail(), post.getPhone(), post.getTitle(), post.getDescription(), post.getImage());

        return added > 0;
    }
    
}
