package org.terasoluna.gfw.examples.upload.app;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.examples.upload.common.UploadConfig;
import org.terasoluna.gfw.examples.upload.common.UploadFileIdGenerator;

@Component
public class UploadHelper {

    @Inject
    private UploadConfig uploadConfig;

    @Inject
    private UploadFileIdGenerator uploadFileIdGenerator;

    public String saveTmpFile(MultipartFile multipartFile) {
        String uploadTmpFileId = uploadFileIdGenerator.generate();
        File uploadTmpFile = new File(uploadConfig.getUploadTmpDir(), uploadTmpFileId);
        try {
            multipartFile.transferTo(uploadTmpFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9003", e);
        }
        return uploadTmpFileId;
    }

}
