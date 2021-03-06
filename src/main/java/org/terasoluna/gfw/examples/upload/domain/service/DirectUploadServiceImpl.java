package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
public class DirectUploadServiceImpl implements DirectUploadService {

    @Inject
    private UploadConfig uploadConfig;

    @Inject
    private UploadFileIdGenerator uploadFileIdGenerator;

    @Override
    public UploadFileInfo saveFile(InputStream inputStream, String fileName, String description) {
        String fileId = uploadFileIdGenerator.generate();
        File uploadFile = new File(uploadConfig.getUploadSaveDir(), fileId);
        try {
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9003", e);
        }
        return new UploadFileInfo(fileId, fileName, description);
    }

    public UploadFileInfo saveFile(DirectUploadFileInfo directUploadFileInfo) {
        return saveFile(directUploadFileInfo.getInputStream(), directUploadFileInfo.getFileName(),
                directUploadFileInfo.getDescription());
    }

    @Override
    public List<UploadFileInfo> saveFiles(List<DirectUploadFileInfo> directUploadFileInfos) {
        List<UploadFileInfo> savedUploadFiles = new ArrayList<UploadFileInfo>();
        for (DirectUploadFileInfo directUploadFileInfo : directUploadFileInfos) {
            savedUploadFiles.add(saveFile(directUploadFileInfo));
        }
        return savedUploadFiles;
    }

}
