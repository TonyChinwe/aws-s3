package com.phisoft.awss3.service;

import com.phisoft.awss3.bucket.BucketName;
import com.phisoft.awss3.imagestore.ImageStoreService;
import com.phisoft.awss3.profile.PetProfile;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * <p>The service class where the main business logic happens</p>
 */
@Service
public class PetProfileService {

    private final PetProfileDataAcessService petProfileDataAcessService;
    private final ImageStoreService imageStoreService;
     @Autowired
    public PetProfileService(PetProfileDataAcessService petProfileDataAcessService, ImageStoreService imageStoreService) {
        this.petProfileDataAcessService = petProfileDataAcessService;
         this.imageStoreService = imageStoreService;
     }

    /**
     * <p>Get all the profiles in our data-store</p>
     * @return list of petprofiles
     */
    public List<PetProfile> getPetProfiles() {
        return petProfileDataAcessService.getPetProfiles();
    }

    /**
     * <p>Uploads image to s3</p>
     * @param petProfileId is the of the petprofile
     *  @param file is the file that is to be uploaded to s3 bucket
     */
    public void uploadImage(UUID petProfileId, MultipartFile file) {
        isFileEmpty(file);
        isImageFile(file);
        PetProfile petProfile = getPetProfileOrThrow(petProfileId);
        Map<String, String> metadata = extractMetadata(file);
        String path=String.format("%s/%s", BucketName.PET_IMAGE.getName(),petProfile.getProfileId());
        String fileName=String.format("%s-%s",file.getOriginalFilename(),UUID.randomUUID());
      try {
          imageStoreService.save(path, fileName, Optional.of(metadata), file.getInputStream());
          petProfile.setProfileImageLink(fileName);
      }catch (IOException e){
          throw new IllegalStateException(e);
      }
    }

    /**
     * <p>Checks for existing pet-profile using profileId</p>
     * @param petProfileId is the petProfileId
     * @return PetProfile
     */
    private PetProfile getPetProfileOrThrow(UUID petProfileId) {
        return petProfileDataAcessService.getPetProfiles().stream().filter(pprofile -> pprofile.getProfileId().equals(petProfileId)).findFirst().orElseThrow(()->new IllegalStateException("pet not found"));
    }

    /**
     * <p>Add all the metadata associated with an image file</p>
     * @param file the file to be uploaded
     * @return map of string containing the metadata
     */
    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String,String> metadata=new HashMap<>();
        metadata.put("Content-Type",file.getContentType());
        metadata.put("Content-Length",String.valueOf(file.getSize()));
        return metadata;
    }

    /**
     * <p>Checks if the file is an image data</p>
     * @param file image file to be uploaded
     */
    private void isImageFile(MultipartFile file) {
        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),ContentType.IMAGE_GIF.getMimeType(),ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())){
        throw new IllegalStateException("File must be image "+file.getContentType());
    }
    }

    /**
     * <p>Checks if the file is empty</p>
     * @param file image file to be uploaded
     */
    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
    }

    /**
     * <p>Downloads petProfile image based on id </p>
     * @param petProfileId id of the image to be downloaded
     */
    public byte[] downloadImage(UUID petProfileId) {

        PetProfile petProfile = getPetProfileOrThrow(petProfileId);
        String fullPath=String.format("%s/%s",BucketName.PET_IMAGE.getName(),petProfile.getProfileId());
        return petProfile.getProfileImageLink()
                .map(key->imageStoreService.download(fullPath,key))
                .orElse(new byte[0]);

    }
}
