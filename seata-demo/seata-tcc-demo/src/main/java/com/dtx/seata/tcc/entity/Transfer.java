package com.dtx.seata.tcc.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
public class Transfer implements Serializable {

	private static final long serialVersionUID = 536746355380294718L;

	@Setter @Getter private Long id;
	
	@Setter @Getter private String fromAccountNo;
	
	@Setter @Getter private String toAccountNo;
	
	@Setter @Getter private BigDecimal amount;
	
	@Setter @Getter private Integer status;
	
	@Setter @Getter private Date startTime;
	
	@Setter @Getter private Date endTime;
	
	
	
}
