package org.terasoluna.gfw.examples.upload.app;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.SingleFileUpload;
import org.terasoluna.gfw.examples.upload.domain.service.UploadService;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("upload")
@RequestMapping("upload")
@Controller
public class FileUploadController {

    @Inject
    private UploadHelper uploadHelper;

    @Inject
    private UploadService uploadService;

    @ModelAttribute
    public FileUploadForm setupFileUploadForm() {
        return new FileUploadForm();
    }

    @TransactionTokenCheck(value = "file", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "file", method = RequestMethod.GET)
    public String uploadFileForm() {
        return "upload/uploadFile";
    }

    @TransactionTokenCheck(value = "file")
    @RequestMapping(value = "file", method = RequestMethod.POST)
    public String uploadFile(@Validated({ SingleFileUpload.class, Default.class }) FileUploadForm form,
            BindingResult result, RedirectAttributes redirectAttributes) throws IOException {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadFile";
        }

        // save tmp file.
        MultipartFile uploadedFile = form.getFile();
        String tmpFileId = uploadHelper.saveTmpFile(uploadedFile);

        // save file.
        uploadService.saveFile(tmpFileId, uploadedFile.getOriginalFilename(), form.getDescription());

        // set result message.
        redirectAttributes.addFlashAttribute(ResultMessages.success().add("i.xx.fw.0001"));

        return "redirect:/upload/file";
    }

}
