package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.InputStream;
import java.io.Serializable;

public class DirectUploadFileInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputStream inputStream;
    private String fileName;
    private String description;

    public DirectUploadFileInfo() {

    }

    public DirectUploadFileInfo(InputStream inputStream, String fileName, String description) {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.description = description;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
