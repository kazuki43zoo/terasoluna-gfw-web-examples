package org.terasoluna.gfw.examples.upload.app;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
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
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadTmpFile);
        } catch (IOException e) {
            throw new SystemException("e.ex.fw.9003", e);
        }
        return uploadTmpFileId;
    }

    public void deleteTmpFile(String uploadTmpFileId) {
        File uploadTmpFile = new File(uploadConfig.getUploadTmpDir(), uploadTmpFileId);
        FileUtils.deleteQuietly(uploadTmpFile);
    }

}
