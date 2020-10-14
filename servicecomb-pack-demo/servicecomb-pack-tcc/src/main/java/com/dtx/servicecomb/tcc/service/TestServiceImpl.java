package com.dtx.servicecomb.tcc.service;

import org.apache.servicecomb.pack.omega.transaction.annotations.Participate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.servicecomb.tcc.entity.Transfer;


@Service
public class TestServiceImpl implements TestService {
	
	@Participate(confirmMethod = "confirm1", cancelMethod="cancel1")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean reserve1(Transfer transfer) {
		System.out.println("this is reserve1");
		return true;
	}

	public void confirm1(Transfer transfer) {
		System.out.println("this is confirm1");
	}
	
	@Transactional
	public void cancel1(Transfer transfer) {
		System.out.println("this is cancle1");
	}

	@Participate(confirmMethod = "confirm2", cancelMethod="cancel2")
	@Transactional(timeout = 2,rollbackFor = Exception.class)
	@Override
	public Boolean reserve2(Transfer transfer) {
		System.out.println("this is reserve2");
		return true;
	}
	
	public void confirm2(Transfer transfer) {
		System.out.println("this is confirm2");
	}

	@Transactional
	public void cancel2(Transfer transfer) {
		System.out.println("this is cancle2");
	}

}
