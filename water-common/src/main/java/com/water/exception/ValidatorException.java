package com.water.exception;
/***
 * 
 * @ClassName:  ValidatorException   
 * @Description:表单校验异常
 * @author: 张凯强
 * @date:   Mar 3, 2020 10:14:08 AM
 */
public class ValidatorException extends RuntimeException {

	/**   
	 * @Fields serialVersionUID :    
	 */   
	private static final long serialVersionUID = 1L;
	
	public ValidatorException(String msg) {
		super(msg);
	}

	
}
