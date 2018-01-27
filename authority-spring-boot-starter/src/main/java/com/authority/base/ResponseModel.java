package com.authority.base;
import java.io.Serializable;

/**
 * 统一返回处理
 */
public class ResponseModel<T> implements Serializable {

    private static final long serialVersionUID = 3771700626958736013L;
    private int code;
    private String message = "";
    private T data;

    private ResponseModel() {
        code = 200;
    }

    private ResponseModel(String message) {
        this();
        this.message = message;
    }
    
    private ResponseModel(int code) {
        this();
        this.code = code;
    }

    private ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;

    }

    private ResponseModel(T t) {
        this();
        data = t;
    }
    
    public boolean isSuccess() {
        return this.code == 200;
    }
    
    public static <T> ResponseModel<T> build() {
    	return new ResponseModel<>();
    }
    
    public static <T> ResponseModel<T> build(int code) {
    	return new ResponseModel<>(code);
    }
    
    public static <T> ResponseModel<T> build(int code, String message) {
    	return new ResponseModel<>(code, message);
    }
    
    public static <T> ResponseModel<T> build(T data) {
    	return new ResponseModel<>(data);
    }
    
    public static <T> ResponseModel<T> build(String message) {
    	return new ResponseModel<>(message);
    }

	public int getCode() {
		return code;
	}

	public ResponseModel<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResponseModel<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public ResponseModel<T> setData(T data) {
		this.data = data;
		return this;
	}

}