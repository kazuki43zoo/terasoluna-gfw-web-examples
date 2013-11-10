package org.terasoluna.gfw.examples.upload.common;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadConfig {

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

    @PostConstruct
    public void afterPropertiesSet() {
        validateDirectory(uploadTmpDir, "temporary dorectory", "app.upload.tmpDir");
        validateDirectory(uploadSaveDir, "save dorectory", "app.upload.saveDir");
    }

    private void validateDirectory(File direcotry, String logicalNameOfDirecotry, String propertyKey) {
        if (!direcotry.exists()) {
            throw new IllegalArgumentException("Upload " + logicalNameOfDirecotry + " is not exists. "
                    + logicalNameOfDirecotry + " is '" + direcotry.getAbsolutePath() + "'.");
        }
        if (!direcotry.isDirectory()) {
            throw new IllegalArgumentException("Property value('" + propertyKey
                    + "') is wrong(not direcotry). Please specify the existing directory path. Property value is '"
                    + direcotry.getAbsolutePath() + "'.");
        }
    }

}
