package com.dtx.seata.tcc.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dtx.seata.tcc.ResultHolder;
import com.dtx.seata.tcc.dao.AccountDao;
import com.dtx.seata.tcc.entity.Transfer;

import io.seata.rm.tcc.api.BusinessActionContext;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	AccountDao acctDao;

	@Transactional
	@Override
	public boolean reserveFrom(BusinessActionContext context, Transfer transfer) {
		if (acctDao.decreaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount());
			ResultHolder.setActionOneResult(context.getXid(), "T");
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean reserveTo(BusinessActionContext context, Transfer transfer) {
		//TODO: 要做防重处理
		//当该方法失败，并且后面的cancle方法也处理失败时，seata会不停地重试该方法。
		acctDao.increaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount());
		ResultHolder.setActionTwoResult(context.getXid(), "T");
		return true;
	}

	/**
	 * 当reserve方法成功后，confirm方法必须成功，如果confirm方法失败，只能手动处理。
	 */
	@Transactional
	@Override
	public boolean confirmFrom(BusinessActionContext context) {
		if(Objects.isNull(ResultHolder.getActionOneResult(context.getXid()))) return true; //防重
		Transfer transfer = ((JSONObject) context.getActionContext("transfer")).toJavaObject(Transfer.class);
		if (acctDao.decreaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			ResultHolder.removeActionOneResult(context.getXid()); //删除标志
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean confirmTo(BusinessActionContext context) {
		if(Objects.isNull(ResultHolder.getActionTwoResult(context.getXid()))) return true; //防重
		Transfer transfer = ((JSONObject) context.getActionContext("transfer")).toJavaObject(Transfer.class);
		if (acctDao.decreaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseBalanceByNo(transfer.getToAccountNo(), transfer.getAmount());
			ResultHolder.removeActionTwoResult(context.getXid()); //删除标志
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean cancelFrom(BusinessActionContext context) {
		if(Objects.isNull(ResultHolder.getActionOneResult(context.getXid()))) return true; //防重
		Transfer transfer = ((JSONObject) context.getActionContext("transfer")).toJavaObject(Transfer.class);
		if (acctDao.decreaseFrozenByNo(transfer.getFromAccountNo(), transfer.getAmount()) == 1) {
			acctDao.increaseBalanceByNo(transfer.getFromAccountNo(), transfer.getAmount());
			ResultHolder.removeActionOneResult(context.getXid()); //删除标志
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean cancelTo(BusinessActionContext context) {
		if(Objects.isNull(ResultHolder.getActionTwoResult(context.getXid()))) return true; //防重
		Transfer transfer = ((JSONObject) context.getActionContext("transfer")).toJavaObject(Transfer.class);
		if (acctDao.decreaseFrozenByNo(transfer.getToAccountNo(), transfer.getAmount()) == 1) {
			ResultHolder.removeActionTwoResult(context.getXid()); //删除标志
			return true;
		}
		return false;
	}
}
