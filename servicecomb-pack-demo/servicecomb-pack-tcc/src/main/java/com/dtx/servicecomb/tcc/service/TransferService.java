package com.dtx.servicecomb.tcc.service;

import com.dtx.servicecomb.tcc.entity.Transfer;

public interface TransferService {

	boolean reserveFrom(Transfer transfer);
	
	boolean reserveTo(Transfer transfer);
	
	boolean confirmFrom(Transfer transfer);
	
	boolean confirmTo(Transfer transfer);
	
	boolean cancelFrom(Transfer transfer);
	
	boolean cancelTo(Transfer transfer);
}
