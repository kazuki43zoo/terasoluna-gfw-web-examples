package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${upload.tmpDir}")
    private File uploadFileTmpDir;

    @Value("${upload.saveDir}")
    private File uploadFileSaveDir;

    @Override
    public void saveFile(String tmpFileId, String fileName, String description) {
        File tmpFile = new File(uploadFileTmpDir, tmpFileId);
        String fileId = UUID.randomUUID().toString();

        tmpFile.renameTo(new File(uploadFileSaveDir, fileId));
        tmpFile.delete();

    }

    @Override
    public void saveFile(UploadFile uploadTmpFile) {
        saveFile(uploadTmpFile.getFileId(), uploadTmpFile.getFileName(), uploadTmpFile.getDescription());

    }

    @Override
    public void saveFiles(List<UploadFile> uploadTmpFiles) {
        for (UploadFile uploadTmpFile : uploadTmpFiles) {
            saveFile(uploadTmpFile);
        }
    }

}
