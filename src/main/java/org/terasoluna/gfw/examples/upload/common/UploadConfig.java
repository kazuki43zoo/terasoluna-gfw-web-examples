package org.terasoluna.gfw.examples.upload.common;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.SystemException;

@Component
public class UploadConfig implements InitializingBean {

    @Value("${app.upload.tmpDir}")
    private File uploadTmpDir;

    @Value("${app.upload.saveDir}")
    private File uploadSaveDir;

    public File getUploadTmpDir() {
        return uploadTmpDir;
    }

    public void setUploadTmpDir(File uploadTmpDir) {
        this.uploadTmpDir = uploadTmpDir;
    }

    public File getUploadSaveDir() {
        return uploadSaveDir;
    }

    public void setUploadSaveDir(File uploadSaveDir) {
        this.uploadSaveDir = uploadSaveDir;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!uploadTmpDir.exists()) {
            throw new SystemException("e.xx.fw.9004",
                    "Upload temporary dorectory is not exists. Temporary dorectory is '"
                            + uploadTmpDir.getAbsolutePath() + "'.");
        }
        if (!uploadTmpDir.isDirectory()) {
            throw new SystemException("e.xx.fw.9004",
                    "Property value('app.upload.tmpDir') is wrong. Please specify the existing directory path.");
        }
        if (!uploadSaveDir.exists()) {
            throw new SystemException("e.xx.fw.9004", "Upload save dorectory is not exists. Save dorectory is '"
                    + uploadSaveDir.getAbsolutePath() + "'.");
        }
        if (!uploadSaveDir.isDirectory()) {
            throw new SystemException("e.xx.fw.9004",
                    "Property value('app.upload.saveDir') is wrong. Please specify the existing directory path.");
        }
    }
}
