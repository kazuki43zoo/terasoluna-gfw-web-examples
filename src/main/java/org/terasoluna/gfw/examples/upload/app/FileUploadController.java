package org.terasoluna.gfw.examples.upload.app;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.common.Messages;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.FileUpload;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.SingleFileUpload;
import org.terasoluna.gfw.examples.upload.domain.service.DirectUploadService;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenContext;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("upload/single")
@RequestMapping("upload/single")
@Controller
public class FileUploadController {

    @Inject
    private DirectUploadService directUploadService;

    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.GET)
    public String uploadFileForm(FileUploadForm form) {
        return "upload/uploadFile";
    }

    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String uploadFile(
            @Validated({ SingleFileUpload.class, FileUpload.class, Default.class }) FileUploadForm form,
            BindingResult result, RedirectAttributes redirectAttributes, TransactionTokenContext txTokenContext)
            throws IOException {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadFile";
        }

        // save file.
        MultipartFile multipartFile = form.getFile();
        directUploadService.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
                form.getDescription());

        // set result message.
        redirectAttributes
                .addFlashAttribute(ResultMessages.success().add(Messages.UP_FILE_UPLOADED.getResultMessage()));

        // remove transaction token.
        txTokenContext.removeToken();

        return "redirect:/upload/single";
    }

}
