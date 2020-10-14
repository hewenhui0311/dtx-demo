package com.dtx.seata.saga.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
public class Account implements Serializable {

	private static final long serialVersionUID = 348923450090196895L;

	@Setter @Getter @NonNull private Integer id;
	
	@Setter @Getter @NonNull private String accountNo;
	
	@Setter @Getter private BigDecimal blance;
	
	@Setter @Getter  private BigDecimal frozen;
}
