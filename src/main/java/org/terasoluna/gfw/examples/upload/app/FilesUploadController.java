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
import org.terasoluna.gfw.examples.upload.domain.service.DirectUploadFileInfo;
import org.terasoluna.gfw.examples.upload.domain.service.DirectUploadService;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenContext;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("upload")
@RequestMapping("upload")
@Controller
public class FilesUploadController {

    @Inject
    private DirectUploadService directUploadService;

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
            RedirectAttributes redirectAttributes, TransactionTokenContext txTokenContext) throws IOException {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadFiles";
        }

        // save files.
        List<DirectUploadFileInfo> uploadTmpFiles = new ArrayList<DirectUploadFileInfo>();
        for (FileUploadForm fileUploadForm : form.getUploadUploadForms()) {
            MultipartFile multipartFile = fileUploadForm.getFile();
            if (!StringUtils.hasLength(multipartFile.getOriginalFilename())) {
                continue;
            }
            uploadTmpFiles.add(new DirectUploadFileInfo(multipartFile.getInputStream(), multipartFile
                    .getOriginalFilename(), fileUploadForm.getDescription()));
        }
        directUploadService.saveFiles(uploadTmpFiles);

        // set result message.
        redirectAttributes.addFlashAttribute(ResultMessages.success().add("i.xx.fw.0001"));

        // remove transaction token.
        txTokenContext.removeToken();

        return "redirect:/upload/files";
    }
}
