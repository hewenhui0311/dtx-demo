package com.dtx.servicecomb.saga.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dtx.servicecomb.saga.entity.Transfer;

@Mapper
public interface TransferDao {

	public int insertTransfer(Transfer transfer);
	
	public int updateTransferStatus(@Param("id")Long id, @Param("status")Integer status);
}
