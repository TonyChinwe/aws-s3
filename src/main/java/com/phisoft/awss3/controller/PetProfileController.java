package com.phisoft.awss3.controller;

import com.phisoft.awss3.profile.PetProfile;
import com.phisoft.awss3.service.PetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * <p>The Api layer( REST controller) that interfaces with the clients</p>
 */
@RestController
@RequestMapping(path="api/pet-profile")
public class PetProfileController {

    private final PetProfileService petProfileService;

    @Autowired
    public PetProfileController(PetProfileService petProfileService) {
        this.petProfileService = petProfileService;
    }

    /**
     * <p>Return all the pet profile in our store or database</p>
     */
    @GetMapping(path="/profile")
    public List<PetProfile> getPetProfiles(){
      return petProfileService.getPetProfiles();
    }
    /**
     * <p>The REST endpoint for uploading pet image to s3</p>
     * @param petProfileId is the id of the pet profile
     * @param file is the actual file or image being uploaded
     */
    @PostMapping(path = "{profileId}/image/upload",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadPetProfileImage(@PathVariable("profileId")UUID petProfileId, @RequestParam("image")MultipartFile file){
        petProfileService.uploadImage(petProfileId,file);
    }

    /**
     * <p>The REST endpoint for downloading pet image from s3</p>
      * @param petProfileId is the id of the pet profile
     * @return the image byte.
     */
    @GetMapping("{profileId}/image/download")
    public byte[] downloadProfileImage(@PathVariable("profileId") UUID petProfileId){

        return petProfileService.downloadImage(petProfileId);


    }
}
