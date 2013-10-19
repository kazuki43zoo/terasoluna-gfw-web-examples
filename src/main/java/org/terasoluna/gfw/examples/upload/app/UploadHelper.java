package org.terasoluna.gfw.examples.upload.app;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadHelper {

    @Value("${app.upload.tmpDir}")
    private File uploadFileTmpDir;

    public String saveTmpFile(MultipartFile multipartFile) throws IOException {
        String uploadTempFileId = UUID.randomUUID().toString();
        File uploadTmpFile = new File(uploadFileTmpDir, uploadTempFileId);
        multipartFile.transferTo(uploadTmpFile);
        return uploadTempFileId;
    }

}
