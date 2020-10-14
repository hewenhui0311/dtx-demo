package com.dtx.demo.common.contant;

public enum TransferStatus {

	TRANSFER_FROM_SUCCESS(0), 
	TRANSFER_FROM_FAILURE(1), 
	TRANSFER_TO_SUCCESS(2),
	TRANSFER_TO_FAILURE(3);

	private int value;

	TransferStatus(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
	
	public static TransferStatus valueOf(int status) {
		TransferStatus code = resolve(status);
		if (code == null) {
			throw new IllegalArgumentException("No matching constant for [" + status + "]");
		}
		return code;
	}

	public static TransferStatus resolve(int status) {
		for (TransferStatus code : values()) {
			if (code.value == status) {
				return code;
			}
		}
		return null;
	}
}
