package org.artJava.upload.pojo;

import java.io.Serializable;

public class FileRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fileName;
	private byte[] fileContent;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
