package com.dtx.demo.common;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

/**
 * 服务请求对象
 * 
 * @author hewenhui
 * @date 2018年3月8日
 *
 */
@Validated
public class ServiceRequest<T> implements Serializable {

	private static final long serialVersionUID = 306860174925786970L;

	@Valid
	private ServiceRequestHeader header;
	// 请求参数
	@Valid
	private T body;
	
	public ServiceRequest() {
		header = new ServiceRequestHeader();
	}

	public ServiceRequestHeader getHeader() {
		return header;
	}
	
	public void setBody(T body) {
		this.body = body;
	}
	
	public T getBody() {
		return body;
	}

	public void setHeader(ServiceRequestHeader header) {
		this.header = header;
	}

	

	/*
	 * public String toString() { StringBuilder sb = new StringBuilder();
	 * sb.append("{").append("reqSeqNo:").append(reqSeqNo).append(",")
	 * .append("bizTrackNo:").append(bizTrackNo).append(",")
	 * .append("hostIp:").append(hostIp).append(",")
	 * .append("userId:").append(userId).append(",")
	 * .append("appId:").append(appId).append(",")
	 * .append("channelId:").append(channelId).append(",") .append("params:{");
	 * for(String key : params.keySet()) {
	 * sb.append(key).append(":").append(params.get(key)).append(","); }
	 * sb.append("}}"); return sb.toString(); }
	 */
}
