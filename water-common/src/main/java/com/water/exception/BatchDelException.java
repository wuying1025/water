package com.water.exception;

/**
 * 批量删除异常
 * @author zhangkaiqiang
 *
 */
public class BatchDelException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BatchDelException(String msg) {
		super(msg);
	}
}