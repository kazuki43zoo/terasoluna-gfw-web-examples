package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
public class DirectUploadServiceImpl implements DirectUploadService {

    @Value("${app.upload.saveDir}")
    private File uploadFileSaveDir;

    @Override
    public UploadFileInfo saveFile(InputStream uploadFileInputStream, String fileName, String description) {
        String fileId = UUID.randomUUID().toString();
        File uploadFile = new File(uploadFileSaveDir, fileId);
        try {
            FileUtils.copyInputStreamToFile(uploadFileInputStream, uploadFile);
        } catch (IOException e) {
            throw new SystemException("e.xx.fw.9001", e);
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
