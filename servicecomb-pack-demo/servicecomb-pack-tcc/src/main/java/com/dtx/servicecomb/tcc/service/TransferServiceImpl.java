package com.dtx.servicecomb.tcc.service;

import org.apache.servicecomb.pack.omega.transaction.annotations.Participate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.servicecomb.tcc.dao.AccountDao;
import com.dtx.servicecomb.tcc.entity.Transfer;

@Service
public class TransferServiceImpl implements TransferService {
	
	@Autowired
	AccountDao acctDao;

	@Participate(confirmMethod = "confirmFrom", cancelMethod = "cancelFrom")
	@Transactional
	@Override
	public boolean reserveFrom(Transfer transfer) {
		if(acctDao.decreaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount());
			return true;
		}
		return false;
	}

	@Transactional
	@Participate(confirmMethod = "confirmTo", cancelMethod = "cancelTo")
	@Override
	public boolean reserveTo(Transfer transfer) {
		acctDao.increaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount());
		return true;
	}

	@Transactional
	@Override
	public boolean confirmFrom(Transfer transfer) {
		if(acctDao.decreaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean confirmTo(Transfer transfer) {
		if(acctDao.decreaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount());
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean cancelFrom(Transfer transfer) {
		if(acctDao.decreaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount());
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean cancelTo(Transfer transfer) {
		if(acctDao.decreaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount()) == 1) 
			return true;
		return false;
	}

}
