package org.terasoluna.gfw.examples.upload.domain.service;

import java.io.InputStream;
import java.util.List;

public interface DirectUploadService {

    UploadFileInfo saveFile(InputStream uploadFileInputStream, String fileName, String description);

    UploadFileInfo saveFile(DirectUploadFileInfo directUploadFileInfo);

    List<UploadFileInfo> saveFiles(List<DirectUploadFileInfo> directUploadFileInfos);

}
