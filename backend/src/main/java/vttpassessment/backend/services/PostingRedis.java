package vttpassessment.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttpassessment.backend.models.Post;

@Service
public class PostingRedis {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public void save(final Post posting) {
        redisTemplate.opsForValue().set(posting.getPostingId(), posting);
    }

    public Post findByPostingId(final String postingId) {
        Post post = (Post) redisTemplate.opsForValue().get(postingId);
        return post;
    }
}
