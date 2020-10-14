package com.dtx.seata.at.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.demo.common.contant.TransferStatus;
import com.dtx.seata.at.dao.AccountDao;
import com.dtx.seata.at.dao.TransferDao;
import com.dtx.seata.at.entity.Transfer;

import io.seata.spring.annotation.GlobalLock;

@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, 
	timeout = 1000, rollbackFor = Exception.class)
public class TransferServiceImpl implements TransferService {

	@Autowired
	private AccountDao dao;

	@Autowired
	private TransferDao transferDao;

	@GlobalLock
	@Override
	public Boolean transferFrom(Transfer transfer) {
		if (dao.decreaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 0)
			return false;
		transfer.setStatus(TransferStatus.TRANSFER_FROM_SUCCESS.value());
		transferDao.insertTransfer(transfer);
		return true;
	}

	@GlobalLock
	@Override
	public Boolean transferTo(Transfer transfer) {
		if (dao.increaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount()) == 0)
			return false;
		return true;
	}

	@Override
	public void doTxSuccessState(Long id) {
		transferDao.updateTransferStatus(id, TransferStatus.TRANSFER_TO_SUCCESS.value());
	}

}
