package org.cloudy.ddl.error;

public class CCDLException extends RuntimeException {

	private static final long serialVersionUID = 3193397632487188632L;

	public CCDLException() {
		super();
	}
	
	public CCDLException(Throwable thr) {
		super(thr);
	}	
	
	public CCDLException(String msg) {
		super(msg);
	}	
	
	public CCDLException(String msg1, String msg2) {
		super(new StringBuilder().append(msg1).append(msg2).toString());
	}
}
