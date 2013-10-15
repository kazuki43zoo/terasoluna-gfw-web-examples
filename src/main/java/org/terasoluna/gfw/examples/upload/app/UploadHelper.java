package org.terasoluna.gfw.examples.upload.app;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadHelper {

    @Value("${upload.tmpDir}")
    private File uploadFileTmpDir;

    public String saveTmpFile(MultipartFile uploadedFile) throws IOException {
        String tmpFileId = UUID.randomUUID().toString();
        File tmpFile = new File(uploadFileTmpDir, tmpFileId);
        uploadedFile.transferTo(tmpFile);
        return tmpFileId;
    }

}
