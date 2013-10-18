package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.Serializable;

public class UploadFileInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileId;
    private String fileName;
    private String description;

    public UploadFileInfo(String fileId, String fileName, String description) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
