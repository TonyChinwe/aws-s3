package com.phisoft.awss3.imagestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * <p>The service class that uploads images to s3 and downloads images from s3</p>
 */
@Service
public class ImageStoreService {

    private final AmazonS3 s3;

    @Autowired
    public ImageStoreService(AmazonS3 s3){
        this.s3=s3;
    }

    /**
     * <p>Uploads image to s3</p>
     * @param path is the image path
     *  @param opMetadata is the additional information accompanying the image
     *  @param inputStream is the actual image date in the form of stream                   
     */
   public void save(String path, String name, Optional<Map<String,String>> opMetadata, InputStream inputStream){
       ObjectMetadata objectMetadata=new ObjectMetadata();
       opMetadata.ifPresent(map->{
           if(!map.isEmpty()){
               map.forEach(objectMetadata::addUserMetadata);
           }
       });
       try{
           s3.putObject(path,name,inputStream,objectMetadata);
       }catch (AmazonServiceException e){
       }
   }

    /**
     * <p>Downloads images from s3</p>
     * @param path is the image path
     *  @param key is the unique key that identifies an image                  
     */
    public byte[] download(String path,String key) {
      try{
     S3Object object= s3.getObject(path,key);
     S3ObjectInputStream inputStream=object.getObjectContent();
     return IOUtils.toByteArray(inputStream);
  }catch(AmazonServiceException |IOException e){
      throw new IllegalStateException("Failed to download file from s3 "+e);

  }


    }
}
