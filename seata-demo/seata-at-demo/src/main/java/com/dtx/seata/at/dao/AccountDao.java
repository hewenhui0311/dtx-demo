package com.dtx.seata.at.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dtx.seata.at.entity.Account;

@Mapper
public interface AccountDao {

	public Account selectByNo(String accountNo);
	
	public int increaseBalanceByNo(@Param("accountNo")String accountNo, @Param("amount")BigDecimal amount);
	
	public int decreaseBalanceByNo(@Param("accountNo")String accountNo, @Param("amount")BigDecimal amount);
	
	public int increaseFrozenByNo(@Param("accountNo")String accountNo, @Param("amount")BigDecimal amount);
	
	public int decreaseFrozenByNo(@Param("accountNo")String accountNo, @Param("amount")BigDecimal amount);
	
	public int insertAccount(Account account);
	
}
