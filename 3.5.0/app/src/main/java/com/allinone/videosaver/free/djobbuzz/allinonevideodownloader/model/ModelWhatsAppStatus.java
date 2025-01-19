package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model;

import java.io.Serializable;

public class ModelWhatsAppStatus implements Serializable {
    public String fileName;
    public String filePath;
    public int fileType;
    public boolean isDownloaded;
    public int itemPos;
    public int itemType;

    public ModelWhatsAppStatus(String str, String str2) {
        this.filePath = str;
        this.fileName = str2;
    }

    public ModelWhatsAppStatus(int i) {
        this.itemType = i;
    }

    public int getFileType() {
        return this.fileType;
    }

    public void setFileType(int i) {
        this.fileType = i;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public boolean isDownloaded() {
        return this.isDownloaded;
    }

    public void setDownloaded(boolean z) {
        this.isDownloaded = z;
    }

    public int getItemPos() {
        return this.itemPos;
    }

    public void setItemPos(int i) {
        this.itemPos = i;
    }
}
