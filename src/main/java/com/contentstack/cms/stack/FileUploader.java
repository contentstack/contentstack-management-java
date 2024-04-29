package com.contentstack.cms.stack;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import javax.activation.MimetypesFileTypeMap;

public class FileUploader {


    public MultipartBody createMultipartBody(String filePath, String parentUid, String title, String description, String[] tags) {
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

        //Adding additional parameters
          if (parentUid != null) {
            builder.addFormDataPart("asset[parent_uid]", parentUid);
        }
        if (title != null ) {
            builder.addFormDataPart("asset[title]", title);
        }
        if (description != null) {
            builder.addFormDataPart("asset[description]", description);
        }
        if (tags != null && tags.length > 0) {
            builder.addFormDataPart("asset[tags]", tagConvertor(tags));
        }

        return builder.build();
    }

    // Helper method to get content type of file
    private String getContentType(File file) {
        try {
            java.nio.file.Path source = Paths.get(file.toString());
            MimetypesFileTypeMap m = new MimetypesFileTypeMap(source.toString());
            return m.getContentType(file);
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
