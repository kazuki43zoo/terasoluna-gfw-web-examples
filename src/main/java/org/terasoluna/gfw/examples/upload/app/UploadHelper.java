package org.terasoluna.gfw.examples.upload.app;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.examples.upload.common.UploadConfig;

@Component
public class UploadHelper {

    @Inject
    private UploadConfig uploadConfig;

    public String saveTmpFile(MultipartFile multipartFile) {
        String uploadTempFileId = UUID.randomUUID().toString();
        File uploadTmpFile = new File(uploadConfig.getUploadTmpDir(), uploadTempFileId);
        try {
            multipartFile.transferTo(uploadTmpFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9003", e);
        }
        return uploadTempFileId;
    }

}
