package com.dtx.seata.at.service;

import com.dtx.seata.at.entity.Transfer;

public interface TransferService {

	/**
	 * From账户转出
	 * @param transfer
	 * @return
	 */
	public Boolean transferFrom(Transfer transfer);

	/**
	 * To账户转入
	 * @param transfer
	 * @return
	 */
	public Boolean transferTo(Transfer transfer);
	
	/**
	 * 更新转账成功状态
	 * @param id
	 */
	public void doTxSuccessState(Long id);
	
}
