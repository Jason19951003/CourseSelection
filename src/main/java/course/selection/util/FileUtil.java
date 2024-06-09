package course.selection.util;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static final String UPLOAD_DIR = "src/main/resources/static/img";

    public static void uploadFile(MultipartFile avatar, String userId) {
        String fileName = getStikcerOriginalName(avatar, userId);
        File dest = new File(UPLOAD_DIR, fileName);
        try (FileOutputStream os = new FileOutputStream(dest)) {
            os.write(avatar.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
    }

    public static String getFileExtension(MultipartFile avatar) {
        return getFileExtension(avatar.getOriginalFilename());
    }

    public static String getStikcerOriginalName(MultipartFile avatar, String userId) {
        return userId + "." + getFileExtension(avatar);
    }

    public static String getAvatarName(MultipartFile avatar, String userId) {
        return UPLOAD_DIR + "/" + getStikcerOriginalName(avatar, userId);
    }
}
