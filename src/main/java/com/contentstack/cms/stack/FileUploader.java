package com.contentstack.cms.stack;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.activation.MimetypesFileTypeMap;

public class FileUploader {

    // Static map for common file extensions to MIME types
    private static final Map<String, String> EXTENSION_TO_MIME;
    static {
        EXTENSION_TO_MIME = new HashMap<>();
        EXTENSION_TO_MIME.put(".svg", "image/svg+xml");
        EXTENSION_TO_MIME.put(".webp", "image/webp");
        EXTENSION_TO_MIME.put(".json", "application/json");
        EXTENSION_TO_MIME.put(".woff", "font/woff");
        EXTENSION_TO_MIME.put(".woff2", "font/woff2");
        EXTENSION_TO_MIME.put(".ttf", "font/ttf");
        EXTENSION_TO_MIME.put(".otf", "font/otf");
        EXTENSION_TO_MIME.put(".eot", "application/vnd.ms-fontobject");
        EXTENSION_TO_MIME.put(".mp4", "video/mp4");
        EXTENSION_TO_MIME.put(".m4a", "audio/mp4");
        EXTENSION_TO_MIME.put(".mkv", "video/x-matroska");
        EXTENSION_TO_MIME.put(".webm", "video/webm");
        EXTENSION_TO_MIME.put(".ico", "image/x-icon");
        EXTENSION_TO_MIME.put(".csv", "text/csv");
        EXTENSION_TO_MIME.put(".md", "text/markdown");
    }

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
        String name = file.getName().toLowerCase();
        int dot = name.lastIndexOf('.');
        if (dot != -1) {
            String ext = name.substring(dot);
            String mime = EXTENSION_TO_MIME.get(ext);
            if (mime != null) return mime;
        }
        try {
            java.nio.file.Path source = java.nio.file.Paths.get(file.toString());
            javax.activation.MimetypesFileTypeMap m = new javax.activation.MimetypesFileTypeMap(source.toString());
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
