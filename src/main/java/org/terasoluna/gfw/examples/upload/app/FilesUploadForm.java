package org.terasoluna.gfw.examples.upload.app;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class FilesUploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull
    @NotEmpty
    @UploadFilesRequired
    private List<FileUploadForm> uploadUploadForms;

    public List<FileUploadForm> getUploadUploadForms() {
        return uploadUploadForms;
    }

    public void setUploadUploadForms(List<FileUploadForm> uploadUploadForms) {
        this.uploadUploadForms = uploadUploadForms;
    }

}
