package org.terasoluna.gfw.examples.upload.app;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @UploadFileRequired(groups = SingleFileUpload.class)
    @UploadFileNotEmpty
    @UploadFileMaxSize(20)
    @UploadFileAllowableExtension("txt")
    private MultipartFile file;

    @NotNull
    @Length(max = 100)
    private String description;

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

    public static interface SingleFileUpload {
    }

}
