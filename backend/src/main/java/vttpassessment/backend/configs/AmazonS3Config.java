package vttpassessment.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

    @Value("${BUCKETSECRET}")
	private String secretKey;

	@Value("${BUCKETACCESS}")
	private String accessKey;

    @Bean
    public AmazonS3 createS3Client() {
        
        EndpointConfiguration epConfig = new EndpointConfiguration("sgp1.digitaloceanspaces.com", "sgp1");
		BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(epConfig)
			.withCredentials(new AWSStaticCredentialsProvider(cred))
			.build();
	}
    
}
