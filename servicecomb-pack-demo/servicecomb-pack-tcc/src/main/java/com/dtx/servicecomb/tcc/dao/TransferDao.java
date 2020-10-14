package com.dtx.servicecomb.tcc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dtx.servicecomb.tcc.entity.Transfer;

@Mapper
public interface TransferDao {

	public int insertTransfer(Transfer transfer);
	
	public int updateTransferStatus(@Param("id")Long id, @Param("status")Integer status);
}
