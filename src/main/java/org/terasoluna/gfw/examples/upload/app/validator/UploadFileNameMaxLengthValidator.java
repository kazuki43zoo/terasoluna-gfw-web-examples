package org.terasoluna.gfw.examples.upload.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileNameMaxLengthValidator implements ConstraintValidator<UploadFileNameMaxLength, MultipartFile> {
    private UploadFileNameMaxLength constraint;

    @Override
    public void initialize(UploadFileNameMaxLength constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null) {
            return true;
        }
        return multipartFile.getOriginalFilename().length() <= constraint.value();
    }

}
