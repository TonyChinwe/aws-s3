package com.phisoft.awss3.profile;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>This class encapsulates the pet-profile</p>
 */
public class PetProfile {
private UUID profileId;
private String username;
private String profileImageLink;

    public PetProfile(UUID profileId, String username, String profileImageLink) {
        this.profileId = profileId;
        this.username = username;
        this.profileImageLink = profileImageLink;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public String getUsername() {
        return username;
    }

    public Optional<String> getProfileImageLink() {
        return Optional.ofNullable(profileImageLink);
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetProfile that = (PetProfile) o;
        return Objects.equals(profileId, that.profileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(profileImageLink, that.profileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, username, profileImageLink);
    }
}
