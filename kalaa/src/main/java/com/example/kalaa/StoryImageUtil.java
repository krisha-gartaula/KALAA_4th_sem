package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class StoryImageUtil {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private StoryImageUtil() {}

    public static String saveStoryImage(HttpServletRequest request, String partName, String servletContextPath)
            throws IOException, ServletException {
        Part part = request.getPart(partName);
        if (part == null || part.getSize() == 0) {
            return null;
        }

        if (part.getSize() > MAX_FILE_SIZE) {
            throw new ServletException("Image file is too large. Maximum size is 5 MB.");
        }

        String submittedName = part.getSubmittedFileName();
        String extension = getExtension(submittedName);
        if (extension == null) {
            throw new ServletException("Invalid image type. Use JPG, PNG, GIF, or WEBP.");
        }

        String uploadsDir = servletContextPath + File.separator + "uploads" + File.separator + "stories";
        File directory = new File(uploadsDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Could not create uploads directory.");
        }

        String filename = "story_" + UUID.randomUUID().toString().replace("-", "") + extension;
        File destination = new File(directory, filename);
        part.write(destination.getAbsolutePath());

        return "uploads/stories/" + filename;
    }

    public static void deleteStoryImage(String imagePath, String servletContextPath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return;
        }

        String normalized = imagePath.replace("/", File.separator).replace("\\", File.separator);
        File file = new File(servletContextPath, normalized);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    private static String getExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return null;
        }

        String lower = filename.toLowerCase();
        for (String ext : ALLOWED_EXTENSIONS) {
            if (lower.endsWith(ext)) {
                return ext;
            }
        }
        return null;
    }
}
