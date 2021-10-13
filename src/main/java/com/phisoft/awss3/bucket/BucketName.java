package com.phisoft.awss3.bucket;

/**
 * <p>Enum that encapsulates s3 buckets</p>
 */
public enum BucketName {
    PET_IMAGE("phi-soft-b2");
    private final String name;
    BucketName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


}
