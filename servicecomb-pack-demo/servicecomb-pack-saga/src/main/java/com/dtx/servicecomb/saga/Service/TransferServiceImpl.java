package com.dtx.servicecomb.saga.Service;

import org.apache.servicecomb.pack.omega.transaction.annotations.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.demo.common.contant.TransferStatus;
import com.dtx.servicecomb.saga.dao.AccountDao;
import com.dtx.servicecomb.saga.dao.TransferDao;
import com.dtx.servicecomb.saga.entity.Transfer;

@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, 
	timeout = 1000, rollbackFor = Exception.class)
public class TransferServiceImpl implements TransferService {

	@Autowired
	private AccountDao dao;

	@Autowired
	private TransferDao transferDao;

	@Override
	@Compensable(compensationMethod = "compentsateFrom")
	public Boolean transferFrom(Transfer transfer) {
		if (dao.decreaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 0)
			return false;
		transfer.setStatus(TransferStatus.TRANSFER_FROM_SUCCESS.value());
		transferDao.insertTransfer(transfer);
		return true;
	}

	@Override
	public void compentsateFrom(Transfer transfer) {
		dao.increaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount());
		transferDao.updateTransferStatus(transfer.getId(), TransferStatus.TRANSFER_FROM_FAILURE.value());
	}

	@Override
	@Compensable(compensationMethod = "compentsateTo")
	public Boolean transferTo(Transfer transfer) {
		if (dao.increaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount()) == 0)
			return false;
		return true;
	}

	public void compentsateTo(Transfer transfer) {
		dao.decreaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount());
	}

	@Compensable(compensationMethod = "updateTxFailState")
	@Override
	public void doTxSuccessState(Long id) {
		transferDao.updateTransferStatus(id, TransferStatus.TRANSFER_TO_SUCCESS.value());
	}

	public void updateTxFailState(Long id) {
		transferDao.updateTransferStatus(id, TransferStatus.TRANSFER_TO_FAILURE.value());
	}

}
