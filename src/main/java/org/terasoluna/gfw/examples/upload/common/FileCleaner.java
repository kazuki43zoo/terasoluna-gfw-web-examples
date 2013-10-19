package org.terasoluna.gfw.examples.upload.common;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.terasoluna.gfw.common.date.DateFactory;

public class FileCleaner implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCleaner.class);

    @Inject
    private DateFactory dateFactory;

    private long savedPeriodMinutes = TimeUnit.DAYS.toMinutes(1);

    private File targetDirectory;

    public void setSavedPeriodMinutes(long savedPeriodMinutes) {
        this.savedPeriodMinutes = savedPeriodMinutes;
    }

    public void setTargetDirectory(File targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void cleanup() {

        if (savedPeriodMinutes < 0) {
            return;
        }

        // calculate cutoff date.
        Date cutoffDate = new Date(dateFactory.newDate().getTime() - (TimeUnit.MINUTES.toMillis(savedPeriodMinutes)));

        // logging begin.
        LOGGER.info("Begin cleanup. Target directory is '{}'. cutoffDate is '{}'.", targetDirectory.getAbsolutePath(),
                cutoffDate);

        // collect target files.
        IOFileFilter fileFilter = FileFilterUtils.ageFileFilter(cutoffDate);
        Collection<File> targetFiles = FileUtils.listFiles(targetDirectory, fileFilter, null);

        if (targetFiles.isEmpty()) {
            // logging target not found.
            LOGGER.info("Delete target file is not found. Target directory is '{}'.", targetDirectory.getAbsoluteFile());
            return;
        }

        // delete files.
        int failedCount = 0;
        for (File targetFile : targetFiles) {
            Date lastModifiedDate = new Date(targetFile.lastModified());
            if (FileUtils.deleteQuietly(targetFile)) {
                LOGGER.debug("Succeed delete file. Deleted file is '{}'. LastModifiedDate is '{}'.",
                        targetFile.getAbsolutePath(), lastModifiedDate);
            } else {
                failedCount++;
                LOGGER.warn("Failed delete file. Taget file is '{}'.", targetFile.getAbsolutePath());
            }
        }

        // logging finished.
        if (0 < failedCount) {
            LOGGER.error("Finished cleanup. Succeed/Failed is {}/{}. Target directory is '{}'.", new Object[] {
                    targetFiles.size() - failedCount, failedCount, targetDirectory.getAbsolutePath() });
        } else {
            LOGGER.info("Finished cleanup. Succeed/Failed is {}/0. Target directory is '{}'.", targetFiles.size(),
                    targetDirectory.getAbsolutePath());
        }

    }

    @Override
    public void afterPropertiesSet() {
        if (targetDirectory == null) {
            throw new IllegalArgumentException("targetDirectory is not setting.");
        }
        if (!targetDirectory.exists()) {
            throw new IllegalArgumentException("targetDirectory is not exists. targetDirectory is '"
                    + targetDirectory.getAbsolutePath() + "'.");
        }
        if (!targetDirectory.isDirectory()) {
            throw new IllegalArgumentException(
                    "targetDirectory is wrong(not direcotry). Please specify the existing directory path. targetDirectory is '"
                            + targetDirectory.getAbsolutePath() + ".");
        }

    }

}
