package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.examples.upload.common.UploadConfig;
import org.terasoluna.gfw.examples.upload.common.UploadFileIdGenerator;

@Transactional
@Service
public class UploadServiceImpl implements UploadService {

    @Inject
    private UploadConfig uploadConfig;

    @Inject
    private UploadFileIdGenerator uploadFileIdGenerator;

    @Override
    public UploadFileInfo saveFile(String uploadTmpFileId, String fileName, String description) {
        String fileId = uploadFileIdGenerator.generate();
        File uploadFile = new File(uploadConfig.getUploadSaveDir(), fileId);
        File uploadTmpFile = new File(uploadConfig.getUploadTmpDir(), uploadTmpFileId);
        try {
            FileUtils.moveFile(uploadTmpFile, uploadFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9003", e);
        }
        return new UploadFileInfo(fileId, fileName, description);
    }

    @Override
    public UploadFileInfo saveFile(UploadFileInfo uploadTmpFileInfo) {
        return saveFile(uploadTmpFileInfo.getFileId(), uploadTmpFileInfo.getFileName(),
                uploadTmpFileInfo.getDescription());
    }

    @Override
    public List<UploadFileInfo> saveFiles(List<UploadFileInfo> uploadTmpFileInfos) {
        List<UploadFileInfo> savedUploadFiles = new ArrayList<UploadFileInfo>();
        for (UploadFileInfo uploadTmpFileInfo : uploadTmpFileInfos) {
            savedUploadFiles.add(saveFile(uploadTmpFileInfo));
        }
        return savedUploadFiles;
    }

    public void deleteTmpFile(String uploadTmpFileId) {
        File uploadTmpFile = new File(uploadConfig.getUploadTmpDir(), uploadTmpFileId);
        FileUtils.deleteQuietly(uploadTmpFile);
    }

}
