package com.ho.edcustom.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FireBaseService {

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;
    public String uploadItem(MultipartFile file)
            throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        Storage storage = bucket.getStorage();

        String fileName = "keyboard/" + UUID.randomUUID();

        String downloadToken = UUID.randomUUID().toString();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("firebaseStorageDownloadTokens", downloadToken);

        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), fileName)
                .setContentType(file.getContentType())
                .setMetadata(metadata)
                .build();

        Blob blob = storage.create(blobInfo, file.getBytes());

        String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucket.getName() +
                "/o/" + fileName.replace("/", "%2F") + "?alt=media&token=" + downloadToken;

        return downloadUrl;
    }
    public String uploadProfile(String file){
        if(file==null||file.trim().isEmpty())
        {
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/cuskey-44d99.firebasestorage.app/o/profile%2Fdefault?alt=media&token=default";
            return downloadUrl;
        }

        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        Storage storage = bucket.getStorage();

        String fileName = "profile/" + UUID.randomUUID();

        String downloadToken = UUID.randomUUID().toString();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("firebaseStorageDownloadTokens", downloadToken);


        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), fileName)
                .setContentType(file)
                .setMetadata(metadata)
                .build();


        Blob blob = storage.create(blobInfo, file.getBytes());

        String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucket.getName() +
                "/o/" + fileName.replace("/", "%2F") + "?alt=media&token=" + downloadToken;

        return  downloadUrl;
    }
}
