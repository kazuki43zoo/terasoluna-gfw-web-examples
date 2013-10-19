package org.terasoluna.gfw.examples.upload.app;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.examples.upload.app.validator.UploadFileMaxSize;
import org.terasoluna.gfw.examples.upload.app.validator.UploadFileNameMaxLength;
import org.terasoluna.gfw.examples.upload.app.validator.UploadFileNotEmpty;
import org.terasoluna.gfw.examples.upload.app.validator.UploadFileRequired;

public class FileUploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @UploadFileRequired(groups = { SingleFileUpload.class })
    @UploadFileNotEmpty
    @UploadFileMaxSize
    @UploadFileNameMaxLength
    private MultipartFile file;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotNull(groups = Upload.class)
    @Size(max = 256, groups = Upload.class)
    private String fileName;

    @NotNull(groups = { Upload.class, DeleteUploadFile.class })
    private String fileId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public static interface SingleFileUpload {
    }

    public static interface Upload {
    }

    public static interface DeleteUploadFile {
    }

}
