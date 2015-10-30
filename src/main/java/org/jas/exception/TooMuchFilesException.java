package org.jas.exception;

public class TooMuchFilesException extends Exception{

	private Integer maxFiles;

	private static final long serialVersionUID = -8819322710932071107L;
	
	public TooMuchFilesException(Integer maxFiles) {
		this.maxFiles = maxFiles;
	}
	
	@Override
	public String getMessage() {
		return "TOO MUCH FILES LOADED: " + maxFiles;
	}

}
