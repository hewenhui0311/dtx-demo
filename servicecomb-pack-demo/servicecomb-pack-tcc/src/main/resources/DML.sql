CREATE TABLE IF NOT EXISTS ACCOUNT (
	ID bigint(19) not null primary key auto_increment,
	ACCOUNT_NO varchar(20) not null,
	BALANCE numeric(10,2) default 0,
	FROZEN numeric(10,2) default 0,
	UNIQUE INDEX UNIQUE_INDEX_ACCOUNTNO(ACCOUNT_NO)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS TRANSFER (
	ID bigint(19) not null primary key,
	FROM_ACCOUNT_NO varchar(20) not null,
	TO_ACCOUNT_NO varchar(20) not null,
	AMOUNT numeric(10,2) not null,
	STATUS int not null,
	START_TIME datetime not null default current_timestamp,
	END_TIME datetime not null default current_timestamp
) DEFAULT CHARSET=utf8mb4;