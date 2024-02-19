package com.contentstack.cms.stack;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileUploader {


    public MultipartBody.Part createMultipartBody(String filePath, String parentUid, String title, String description, String[] tags) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        // Check if filePath is not empty and file exists
        if (!filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                String contentType = getContentType(file);
                RequestBody fileBody = RequestBody.create(Objects.requireNonNull(MediaType.parse(contentType)), file);
                builder.addFormDataPart("asset[upload]", file.getName(), fileBody);
            }
        }

        // Add other parts if not null
        addFormDataPartIfNotNull(builder, "asset[parent_uid]", parentUid);
        addFormDataPartIfNotNull(builder, "asset[title]", title);
        addFormDataPartIfNotNull(builder, "asset[description]", description);

        // Handle tags array null case
        if (tags != null) {
            addFormDataPartIfNotNull(builder, "asset[tags]", tagConvertor(tags));
        }

        return builder.build().part(0);
    }

    // Helper method to add form data part if value is not null
    private void addFormDataPartIfNotNull(MultipartBody.Builder builder, String name, String value) {
        if (value != null) {
            builder.addFormDataPart(name, value);
        }
    }

    // Helper method to get content type of file
    private String getContentType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine content type of file", e);
        }
    }

    // Dummy implementation of tagConvertor method
    private String tagConvertor(String[] tags) {
        // Implement your tag conversion logic here
        return String.join(",", tags);
    }
}
