package com.br.coffeeandit.repository;

import com.br.coffeeandit.config.S3ClientResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Path;

@ApplicationScoped
public class S3ImageClientRepository extends S3ClientResource {

    public static final String IMAGE_PNG = "image/png";

    @Inject
    S3Client s3Client;

    public PutObjectResponse putObject(Path file, String uuid) {
        var putObjectResponse = s3Client.putObject(buildPutRequest(uuid, IMAGE_PNG), RequestBody.fromFile(file.toFile()));
        assert null != putObjectResponse;
        return putObjectResponse;
    }

    public ResponseBytes<GetObjectResponse> getObjects(final String uuid) {
        try {
            return s3Client.getObjectAsBytes(buildGetRequest(uuid));
        } catch (NoSuchKeyException noSuchKeyException) {
            return null;
        }
    }
}
