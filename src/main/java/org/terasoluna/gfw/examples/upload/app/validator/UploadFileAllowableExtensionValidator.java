package org.terasoluna.gfw.examples.upload.app.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileAllowableExtensionValidator implements
        ConstraintValidator<UploadFileAllowableExtension, MultipartFile> {

    private UploadFileAllowableExtension constraint;

    @Override
    public void initialize(UploadFileAllowableExtension constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null || !StringUtils.hasLength(multipartFile.getOriginalFilename())) {
            return true;
        }
        String fileName = multipartFile.getOriginalFilename();
        String extension;
        if (fileName.indexOf(".") == -1) {
            extension = "";
        } else {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return 0 <= Arrays.binarySearch(constraint.value(), extension);
    }

}
