package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.File;
import java.util.ArrayList;
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
    public UploadFileInfo saveFile(String tmpFileId, String fileName, String description) {
        File tmpFile = new File(uploadFileTmpDir, tmpFileId);
        String fileId = UUID.randomUUID().toString();

        tmpFile.renameTo(new File(uploadFileSaveDir, fileId));
        tmpFile.delete();
        return new UploadFileInfo(fileId, fileName, description);
    }

    @Override
    public UploadFileInfo saveFile(UploadFileInfo uploadTmpFile) {
        return saveFile(uploadTmpFile.getFileId(), uploadTmpFile.getFileName(), uploadTmpFile.getDescription());
    }

    @Override
    public List<UploadFileInfo> saveFiles(List<UploadFileInfo> uploadTmpFiles) {
        List<UploadFileInfo> savedUploadFiles = new ArrayList<UploadFileInfo>();
        for (UploadFileInfo uploadTmpFile : uploadTmpFiles) {
            savedUploadFiles.add(saveFile(uploadTmpFile));
        }
        return savedUploadFiles;
    }

}
