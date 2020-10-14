package com.dtx.servicecomb.tcc.service;

import com.dtx.servicecomb.tcc.entity.Transfer;

public interface TestService {

	public Boolean reserve1(Transfer transfer);
	
	public Boolean reserve2(Transfer transfer);
}
