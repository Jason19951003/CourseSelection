package course.selection.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static final String UPLOAD_DIR = "src/main/resources/static/img";

    public static void uploadFile(MultipartFile sticker, String userId) throws IOException {
        String fileName = getStikcerOriginalName(sticker, userId);
        File dest = new File(UPLOAD_DIR, fileName);
        sticker.transferTo(dest);
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(fileName)+1);
        }
    }

    public static String getFileExtension(MultipartFile sticker) {
        return getFileExtension(sticker.getOriginalFilename());
    }

    public static String getStikcerOriginalName(MultipartFile sticker, String userId) {
        return userId + "." + getFileExtension(sticker);
    }

    public static String getStickerName(MultipartFile sticker, String userId) {
        return UPLOAD_DIR + "/" + getStikcerOriginalName(sticker, userId);
    }
}