package org.terasoluna.gfw.examples.upload.domain.service;

import java.util.List;

public interface UploadService {

    void saveFile(UploadFile uploadTmpFile);

    void saveFile(String tmpFileId, String fileName, String description);

    void saveFiles(List<UploadFile> uploadTmpFiles);

}
