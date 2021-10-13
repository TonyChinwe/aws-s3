package com.phisoft.awss3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>The Configuration of aws credentials that allows the application have access to s3 storage features </p>
 */
@Configuration
public class ProjectConfig {
    
    /**
     * {@inheritDoc}
     */
    @Bean
    public AmazonS3 s3Credential(){
        AWSCredentials awsCredentials=new BasicAWSCredentials(
                "AKIAWRB4PFVGJ3DHRE7R",
    "9HCfQj0eK1sb6KyVHagIcqKuPpOZRBEUDq1S5sQZ"
        );
        return AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_2).
                withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }


}
