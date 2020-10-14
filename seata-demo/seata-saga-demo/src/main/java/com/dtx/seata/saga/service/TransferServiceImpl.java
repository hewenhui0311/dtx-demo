package com.dtx.seata.saga.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.demo.common.contant.TransferStatus;
import com.dtx.seata.saga.dao.AccountDao;
import com.dtx.seata.saga.dao.TransferDao;
import com.dtx.seata.saga.entity.Transfer;

@Service("transferService")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, timeout = 1000, rollbackFor = Exception.class)
public class TransferServiceImpl implements TransferService {

	@Autowired
	private AccountDao dao;

	@Autowired
	private TransferDao transferDao;

	@Override
	public Boolean transferFrom(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		if (dao.decreaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 0)
			return false;
		transfer.setStatus(TransferStatus.TRANSFER_FROM_SUCCESS.value());
		transferDao.insertTransfer(transfer);
		return true;
	}

	@Override
	public void compentsateFrom(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		dao.increaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount());
		transferDao.updateTransferStatus(transfer.getId(), TransferStatus.TRANSFER_FROM_FAILURE.value());
	}

	@Override
	public Boolean transferTo(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		return dao.increaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount()) == 1;
		
	}

	public void compentsateTo(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		dao.decreaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount());
	}

	@Override
	public Boolean doTxSuccessState(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		return transferDao.updateTransferStatus(transfer.getId(), TransferStatus.TRANSFER_TO_SUCCESS.value()) == 1;
	}

	public void updateTxFailState(Map<String, Object> params) {
		Transfer transfer = (Transfer) params.get("transfer");
		transferDao.updateTransferStatus(transfer.getId(), TransferStatus.TRANSFER_TO_FAILURE.value());
	}

}
