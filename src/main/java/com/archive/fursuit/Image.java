package com.archive.fursuit;

public class Image {

    private String label;
    private String fileType;
    private String downloadURL;
    private long fileSize;

    public Image(){};

    public Image(String label, String fileType, String downloadURL, long fileSize) {
        this.label = label;
        this.fileType = fileType;
        this.downloadURL = downloadURL;
        this.fileSize = fileSize;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
