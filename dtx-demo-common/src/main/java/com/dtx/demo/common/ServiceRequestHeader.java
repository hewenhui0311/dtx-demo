package com.dtx.demo.common;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.dtx.demo.common.utils.RandomTextGenerator;

/**
 * @auth hewenhui
 * @data 2020年3月5日
 * @since
 * @description
 */
@Validated
public class ServiceRequestHeader extends ServiceHeader {
	private static final long serialVersionUID = -211846798258557652L;
	// 请求序列号
	private final String reqSeqNo;
	// 业务跟踪序列号
	@NotEmpty(message = "bizTrackNo is empty")
	private String bizTrackNo;
	// 渠道
	private String channel;
	//应用名
	@NotEmpty(message = "appName is empty")
	private String appName;
	// 请求路径
	@NotEmpty(message = "methodName is empty")
	private String methodName;

	public ServiceRequestHeader () {
		reqSeqNo = RandomTextGenerator.getRandomText32();
	}
	
	public String getReqSeqNo() {
		return reqSeqNo;
	}

	public String getBizTrackNo() {
		return bizTrackNo;
	}

	public void setBizTrackNo(String bizTrackNo) {
		this.bizTrackNo = bizTrackNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
