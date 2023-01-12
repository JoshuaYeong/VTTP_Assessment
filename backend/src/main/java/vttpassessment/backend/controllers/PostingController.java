package vttpassessment.backend.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
import vttpassessment.backend.models.Post;

@RestController
@RequestMapping
public class PostingController {

    @Autowired
    private AmazonS3 s3;

    @PostMapping(path = "/api/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postSpacesUpoad(@RequestPart MultipartFile myfile, @RequestPart String name, @RequestPart String email, @RequestPart String phone, @RequestPart String title, @RequestPart String description) {

        System.out.println(">>>>>>>>>>>>HERE 1");

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
                
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost.toJson().toString());
    }
    
}