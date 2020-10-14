package com.dtx.demo.common;

import java.io.Serializable;

import com.dtx.demo.common.contant.ResponseCode;


/**
 * 服务响应
 * @author hewenhui
 * @date 2018年3月8日
 */
public class ServiceResponse<T> implements Serializable {

	private static final long serialVersionUID = -885151682463012042L;
	private ServiceResponseHeader header;
	//响应码
	private int code;
	//响应消息
	private String message;
	//响应结果
	private T body;
		
	private ServiceResponse() {
		this.code = ResponseCode.SUCCESS.value();
		this.message = ResponseCode.SUCCESS.text();
		header = new ServiceResponseHeader();
	}
	
	public static <E> ServiceResponse<E> newInstance() {
		return new ServiceResponse<E>();
	}
	
	public int getCode() {
		return code;
	}

	public ServiceResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ServiceResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public T getBody() {
		return body;
	}

	public ServiceResponse<T> setBody(T body) {
		this.body = body;
		return this;
	}

	public ServiceResponse<T> setResponseCode(ResponseCode respCode) {
		setCode(respCode.value());
		setMessage(respCode.text());
		return this;
	}

	public ServiceResponseHeader getHeader() {
		return header;
	}

	public void setHeader(ServiceResponseHeader header) {
		this.header = header;
	}

}
