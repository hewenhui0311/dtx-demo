package com.dtx.seata.saga.service;

import java.util.Map;

public interface TransferService {

	/**
	 * From账户转出
	 * @param transfer
	 * @return
	 */
	public Boolean transferFrom(Map<String, Object> params);

	/**
	 * From账户冲正
	 * @param transfer
	 */
	public void compentsateFrom(Map<String, Object> params);

	/**
	 * To账户转入
	 * @param transfer
	 * @return
	 */
	public Boolean transferTo(Map<String, Object> params);
	
	/**
	 * 更新转账成功状态
	 * @param id
	 */
	public Boolean doTxSuccessState(Map<String, Object> params);
	
}
