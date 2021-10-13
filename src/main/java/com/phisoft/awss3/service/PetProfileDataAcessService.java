package com.phisoft.awss3.service;

import com.phisoft.awss3.datastore.FakePetProfileDataStore;
import com.phisoft.awss3.profile.PetProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Repository that encapsulates the data-store</p>
 */
@Repository
public class PetProfileDataAcessService {
    private final FakePetProfileDataStore fakePetProfileDataStore;

    @Autowired
    public PetProfileDataAcessService(FakePetProfileDataStore fakePetProfileDataStore) {
        this.fakePetProfileDataStore = fakePetProfileDataStore;
    }

    /**
     * <p>Get all the profiles in our data-store</p>
     * @return list of petprofiles
     */ 
    public List<PetProfile> getPetProfiles() {
        return fakePetProfileDataStore.getPetProfiles();
    }
}
