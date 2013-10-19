package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.SystemException;

@Transactional
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${app.upload.tmpDir}")
    private File uploadFileTmpDir;

    @Value("${app.upload.saveDir}")
    private File uploadFileSaveDir;

    @Override
    public UploadFileInfo saveFile(String uploadTmpFileId, String fileName, String description) {
        String fileId = UUID.randomUUID().toString();
        File uploadFile = new File(uploadFileSaveDir, fileId);
        File uploadTmpFile = new File(uploadFileTmpDir, uploadTmpFileId);
        try {
            FileUtils.moveFile(uploadTmpFile, uploadFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9001", e);
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

}
