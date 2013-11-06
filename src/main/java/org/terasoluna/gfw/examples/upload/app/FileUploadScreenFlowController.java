package org.terasoluna.gfw.examples.upload.app;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.common.Messages;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.DeleteUploadFile;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.FileUpload;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.SingleFileUpload;
import org.terasoluna.gfw.examples.upload.app.FileUploadForm.Upload;
import org.terasoluna.gfw.examples.upload.domain.service.UploadFileInfo;
import org.terasoluna.gfw.examples.upload.domain.service.UploadService;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

/**
 * Controller for upload with in screen flow.
 */
@TransactionTokenCheck("upload/flow")
@RequestMapping("upload/flow")
@Controller
public class FileUploadScreenFlowController {

    /**
     * Helper for upload in web layer.
     */
    @Inject
    private UploadHelper uploadHelper;

    /**
     * Service for upload.
     */
    @Inject
    private UploadService uploadService;

    /**
     * Component for Bean mapping.
     */
    @Inject
    private Mapper beanMapper;

    /**
     * View the upload form screen.
     * 
     * @return View name of upload form screen
     */
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.GET)
    public String uploadFileForm(FileUploadForm form) {
        return "upload/uploadForm";
    }

    /**
     * Upload file to the temporary directory.
     * 
     * @param form
     *            Instance of upload form.
     * @param result
     *            Binding result(Validation result) of form.
     * @return View name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST, params = "upload")
    public String uploadFile(@Validated({ SingleFileUpload.class, FileUpload.class }) FileUploadForm form,
            BindingResult result) {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadForm";
        }

        // save temporary file.
        MultipartFile multipartFile = form.getFile();
        String tmpFileId = uploadHelper.saveTmpFile(multipartFile);

        // retain temporary file information in form.
        form.setFileId(tmpFileId);
        form.setFileName(multipartFile.getOriginalFilename());

        return "upload/uploadForm";
    }

    /**
     * Confirm & Upload file to the temporary directory.
     * 
     * @param form
     *            Instance of upload form.
     * @param result
     *            Binding result(Validation result) of form.
     * @return View name of upload confirm screen. If occurred a validation
     *         error, return view name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST, params = "confirmAndUpload")
    public String confirmAndUpload(
            @Validated({ SingleFileUpload.class, FileUpload.class, Default.class }) FileUploadForm form,
            BindingResult result) {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadForm";
        }

        // save temporary file.
        MultipartFile multipartFile = form.getFile();
        String tmpFileId = uploadHelper.saveTmpFile(multipartFile);

        // retain temporary file information in form.
        form.setFileId(tmpFileId);
        form.setFileName(multipartFile.getOriginalFilename());

        return "upload/uploadConfirm";
    }

    /**
     * Confirm form values.
     * 
     * @param form
     *            Instance of upload form.
     * @param result
     *            Binding result(Validation result) of form.
     * @return View name of upload confirm screen. If occurred a validation
     *         error, return view name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String uploadConfirm(@Validated FileUploadForm form, BindingResult result) {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadForm";
        }

        return "upload/uploadConfirm";
    }

    /**
     * Delete upload file from the temporary directory.
     * 
     * @param form
     *            Instance of upload form.
     * @param result
     *            Binding result(Validation result) of form.
     * @param model
     *            Instance of Model container.
     * @return View name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST, params = "delete")
    public String deleteFile(@Validated(DeleteUploadFile.class) FileUploadForm form, BindingResult result, Model model) {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadForm";
        }

        // delete tmp file.
        uploadHelper.deleteTmpFile(form.getFileId());

        // cleanup tmp file information in form.
        form.setFileId(null);
        form.setFileName(null);

        return "upload/uploadForm";
    }

    /**
     * Back to the upload form screen.
     * 
     * @param form
     *            Instance of upload form.
     * @return View name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST, params = "redo")
    public String uploadRedo(FileUploadForm form) {
        return "upload/uploadForm";
    }

    /**
     * Upload file.
     * 
     * @param form
     *            Instance of upload form.
     * @param result
     *            Binding result(Validation result) of form.
     * @param redirectAttributes
     *            Instance of Model container for redirect
     * @return View name of redirect upload complete screen. If occurred a
     *         validation error, return view name of upload form screen.
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String upload(@Validated({ Upload.class, Default.class }) FileUploadForm form, BindingResult result,
            RedirectAttributes redirectAttributes) {

        // handle validation result.
        if (result.hasErrors()) {
            return "upload/uploadForm";
        }

        // convert to domain layer object.
        UploadFileInfo tmpUploadFileInfo = beanMapper.map(form, UploadFileInfo.class);

        // upload.
        UploadFileInfo uploadedFileInfo = uploadService.saveFile(tmpUploadFileInfo);

        // retain uploaded file information in flush scope.
        redirectAttributes.addFlashAttribute(uploadedFileInfo);

        // set result message.
        redirectAttributes
                .addFlashAttribute(ResultMessages.success().add(Messages.UP_FILE_UPLOADED.getResultMessage()));

        return "redirect:/upload/flow?complete";
    }

    /**
     * View the upload complete screen.
     * 
     * @return View name of upload complete screen.
     */
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public String uploadComplete() {
        return "upload/uploadComplete";
    }

}
