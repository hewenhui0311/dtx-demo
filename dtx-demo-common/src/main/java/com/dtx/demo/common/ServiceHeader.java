package com.dtx.demo.common;

import java.io.Serializable;

import com.dtx.demo.common.utils.HostUtil;

/**
 * @auth hewenhui
 * @data 2020年3月5日
 * @since
 * @description
 */

public class ServiceHeader implements Serializable {

	private static final long serialVersionUID = 5278185052742113716L;
	
	// 服务器IP
	private final String hostIp;

	// 用户ID
	private Long userId;
	
	public ServiceHeader() {
		hostIp = HostUtil.getHostIp();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getHostIp() {
		return hostIp;
	}
}
