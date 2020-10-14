package com.dtx.seata.tcc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dtx.seata.tcc.entity.Transfer;

@Mapper
public interface TransferDao {

	public int insertTransfer(Transfer transfer);
	
	public int updateTransferStatus(@Param("id")Long id, @Param("status")Integer status);
}
