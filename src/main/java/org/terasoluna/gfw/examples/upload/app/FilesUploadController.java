package org.terasoluna.gfw.examples.upload.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.upload.domain.service.UploadService;
import org.terasoluna.gfw.examples.upload.domain.service.UploadFile;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("upload")
@RequestMapping("upload")
@Controller
public class FilesUploadController {

    @Inject
    private UploadHelper uploadHelper;;

    @Inject
    private UploadService uploadService;

    @ModelAttribute
    public FilesUploadForm setupFilesUploadForm() {
        return new FilesUploadForm();
    }

    @TransactionTokenCheck(value = "files", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "files", method = RequestMethod.GET)
    public String uploadFilesForm() {
        return "upload/uploadFiles";
    }

    @TransactionTokenCheck(value = "files")
    @RequestMapping(value = "files", method = RequestMethod.POST)
    public String uploadFiles(@Validated FilesUploadForm form, BindingResult result,
            RedirectAttributes redirectAttributes) throws IOException {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadFiles";
        }

        // save tmp files.
        List<UploadFile> uploadTmpFiles = new ArrayList<UploadFile>();
        for (FileUploadForm fileUploadForm : form.getUploadUploadForms()) {
            MultipartFile uploadedFile = fileUploadForm.getFile();
            if (!StringUtils.hasLength(uploadedFile.getOriginalFilename())) {
                continue;
            }
            String tmpFileId = uploadHelper.saveTmpFile(uploadedFile);
            uploadTmpFiles.add(new UploadFile(tmpFileId, uploadedFile.getOriginalFilename(), fileUploadForm
                    .getDescription()));
        }

        // save files.
        uploadService.saveFiles(uploadTmpFiles);

        // set result message.
        redirectAttributes.addFlashAttribute(ResultMessages.success().add("i.xx.fw.0001"));

        return "redirect:/upload/files";
    }
}
