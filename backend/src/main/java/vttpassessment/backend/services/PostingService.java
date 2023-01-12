package vttpassessment.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttpassessment.backend.models.Post;
import vttpassessment.backend.repositories.PostingRepository;

@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepo;

    public boolean insertPost(Post post) {
        return postingRepo.insertPost(post);
    }
    
}
