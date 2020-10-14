package com.dtx.servicecomb.saga.Service;

import com.dtx.servicecomb.saga.entity.Transfer;

public interface TransferService {

	/**
	 * From账户转出
	 * @param transfer
	 * @return
	 */
	public Boolean transferFrom(Transfer transfer);

	/**
	 * From账户冲正
	 * @param transfer
	 */
	public void compentsateFrom(Transfer transfer);

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
