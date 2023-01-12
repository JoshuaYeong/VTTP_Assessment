package vttpassessment.backend.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttpassessment.backend.models.Post;
import vttpassessment.backend.services.PostingRedis;
import vttpassessment.backend.services.PostingService;

@RestController
@RequestMapping
public class PostingController {

    @Autowired
    private PostingService postingSvc;

    @Autowired
    private PostingRedis postRedis;

    @Autowired
    private AmazonS3 s3;

    @PostMapping(path = "/api/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postSpacesUpoad(@RequestPart MultipartFile myfile, @RequestPart String name, @RequestPart String email, @RequestPart String phone, @RequestPart String title, @RequestPart String description) {

        String hash = UUID.randomUUID().toString().replace("-","").substring(1,8);
        Date currentDate = new Date();        

        // My private metadata
        Map<String, String> myData = new HashMap<>();
        myData.put("postingId", hash);
        myData.put("postingDate", (currentDate.toString()));

        // Metadata for the object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(myfile.getContentType());
        metadata.setContentLength(myfile.getSize());
        metadata.setUserMetadata(myData);

        // Save to Spaces/S3 bucket
        try {
            // bucketName, followed by folderName
            PutObjectRequest putReq = new PutObjectRequest("dos3bucket", "images/%s".formatted(hash),
                    myfile.getInputStream(), metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3.putObject(putReq);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Response Object
        JsonObject imageData = Json.createObjectBuilder()
                .add("content-type", myfile.getContentType())
                .add("size", myfile.getSize())
                .add("name", hash)
                .add("original_name", myfile.getOriginalFilename())
                .build();

        System.out.println(">>>>>>>imageData: " + imageData);

        String imageUrl = String.format("https://dos3bucket.sgp1.digitaloceanspaces.com/images/%s", hash);
        System.out.println(">>>>>>>imageUrl: " + imageUrl);

        Post newPost = new Post();
        newPost.setPostingId(hash);
        newPost.setPostingDate(currentDate);
        newPost.setName(name);
        newPost.setEmail(email);
        newPost.setPhone(phone);
        newPost.setTitle(title);
        newPost.setDescription(description);
        newPost.setImage(imageUrl);

        postRedis.save(newPost);

        System.out.println(newPost.toJson().toString());
                
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost.toJson().toString());
    }

    @PutMapping(path="/api/posting/{postingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePosting(@PathVariable String postingId) {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Post post = postRedis.findByPostingId(postingId);

        if(post == null) {
            objectBuilder.add("message", "Posting ID " + postingId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectBuilder.build().toString());
        }

        postingSvc.insertPost(post);
        objectBuilder.add("message", "Accepted " + postingId + "\"");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(objectBuilder.build().toString());
    }
    
}