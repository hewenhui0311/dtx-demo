package com.dtx.demo.common.contant;

/**
 * 
 * @author hewenhui
 * @date 2019年12月10日
 * @description
 */
public enum ResponseCode {

    SUCCESS(200, "OK"),
    
    IllEGAL_PARAM(400, "Illegal Parameter"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    LOCKED(423, "Locked"),
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    VERSION_NOT_SUPPORTED(505, "Version not supported"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    AUTHENTICATION_REQUIRED(511, "Authentication Required");
	

    private int value;

    private String text;

    ResponseCode(int value, String text) {
        this.value = value;
        this.text = text;
    }

	public int value() {
		return this.value;
	}

	public String text() {
		return text;
	}
	
	public static ResponseCode valueOf(int responseCode) {
		ResponseCode code = resolve(responseCode);
		if (code == null) {
			throw new IllegalArgumentException("No matching constant for [" + responseCode + "]");
		}
		return code;
	}

	public static ResponseCode resolve(int responseCode) {
		for (ResponseCode code : values()) {
			if (code.value == responseCode) {
				return code;
			}
		}
		return null;
	}
	
    @Override
    public String toString() {
    	return "{\"value\":\""+value+"\",\"text\":\""+text+"\"}";
    }
}
