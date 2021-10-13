package com.phisoft.awss3.datastore;

import com.phisoft.awss3.profile.PetProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * <p>This is fake data-store for testing</p>
 */
@Repository
public class FakePetProfileDataStore {

    private static final List<PetProfile> PET_PROFILES = new ArrayList<>();

    static {
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "sparrow", null));
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "gold", null));
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "crown", null));
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "ijele", null));
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "lucky", null));
        PET_PROFILES.add(new PetProfile(UUID.randomUUID(), "aki", null));
    }

    public List<PetProfile> getPetProfiles() {
        return PET_PROFILES;
    }
}
