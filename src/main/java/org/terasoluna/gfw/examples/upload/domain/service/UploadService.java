package org.terasoluna.gfw.examples.upload.domain.service;

import java.util.List;

public interface UploadService {

    void deleteTmpFile(String uploadTmpFileId);

    UploadFileInfo saveFile(String uploadTmpFileId, String fileName, String description);

    UploadFileInfo saveFile(UploadFileInfo uploadTmpFileInfo);

    List<UploadFileInfo> saveFiles(List<UploadFileInfo> uploadTmpFileInfos);

}
