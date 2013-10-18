package org.terasoluna.gfw.examples.upload.domain.service;

import java.util.List;

public interface UploadService {

    UploadFileInfo saveFile(UploadFileInfo uploadTmpFile);

    UploadFileInfo saveFile(String tmpFileId, String fileName, String description);

    List<UploadFileInfo> saveFiles(List<UploadFileInfo> uploadTmpFiles);

}
