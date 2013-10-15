package org.terasoluna.gfw.examples.upload.app;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class UploadFilesRequiredValidator implements
        ConstraintValidator<UploadFilesRequired, Collection<FileUploadForm>> {

    private UploadFilesRequired constraint;

    @Override
    public void initialize(UploadFilesRequired constraint) {
        if (constraint.value() < 1) {
            throw new IllegalArgumentException("value of @UploadFileRequiredForCollection must be over 1.");
        }
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(Collection<FileUploadForm> fileUploadForms, ConstraintValidatorContext context) {
        if (fileUploadForms == null || fileUploadForms.size() < constraint.value()) {
            return false;
        }
        int fileCount = 0;
        for (FileUploadForm fileUploadForm : fileUploadForms) {
            if (StringUtils.hasLength(fileUploadForm.getFile().getOriginalFilename())) {
                fileCount++;
            }
        }
        return constraint.value() <= fileCount;
    }
}
