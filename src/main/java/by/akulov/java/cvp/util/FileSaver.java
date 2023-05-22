package by.akulov.java.cvp.util;

import by.akulov.java.cvp.exception.ServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public final class FileSaver {

    public static String saveFileTo(MultipartFile multipartFile, String path) throws IOException {
        if (multipartFile != null) {
            File file = new File("");
            byte[] buf = new byte[51200];
            String fileName = UUID.randomUUID().toString();
            String fullPath = file.getCanonicalPath() + path + fileName;
            InputStream inputStream = multipartFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
            int numRead;
            while ((numRead = inputStream.read(buf)) >= 0) {
                fileOutputStream.write(buf, 0, numRead);
            }
            inputStream.close();
            fileOutputStream.close();
            return fileName;
        } else {
            throw new ServerErrorException();
        }
    }


}
