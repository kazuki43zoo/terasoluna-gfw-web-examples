package org.terasoluna.gfw.examples.upload.domain.service;

import java.util.List;

public interface UploadService {

    UploadFileInfo saveFile(String uploadTmpFileId, String fileName, String description);

    UploadFileInfo saveFile(UploadFileInfo uploadTmpFile);

    List<UploadFileInfo> saveFiles(List<UploadFileInfo> uploadTmpFiles);

}
