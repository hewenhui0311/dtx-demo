package com.dtx.seata.saga.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.contant.ResponseCode;
import com.dtx.demo.common.utils.SequenceGenerator;
import com.dtx.seata.saga.entity.Transfer;

import io.seata.saga.engine.AsyncCallback;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.proctrl.ProcessContext;
import io.seata.saga.statelang.domain.StateMachineInstance;

@RestController
public class TransferController {
	
	@Autowired
	StateMachineEngine stateMachineEngine;

	SequenceGenerator sequence = new SequenceGenerator(0);

	/**
	 *   
	 * @param transfer
	 * @return
	 */
	@PostMapping("/transfer")
	public ServiceResponse<Void> transfer(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		ServiceResponse<Void> response = ServiceResponse.newInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transfer", transfer);
		//同步
		StateMachineInstance inst = stateMachineEngine.start("TransferService", null, params);
		Map<String, Object> context = inst.getContext();
		Map<String, Object> endParams = inst.getEndParams();
		String errorCode = (String)endParams.get("_statemachine_error_code_");
		if(!Objects.isNull(errorCode)) {
			String errorMsg = (String)endParams.get("_statemachine_error_message_");
			response.setResponseCode(ResponseCode.valueOf(errorCode));
			response.setMessage(errorMsg);
		} else {
			response.setMessage("转账成功");
		}
		return response;
	}
	
	@PostMapping("/asynTransfer")
	public ServiceResponse<Void> asynTransfer(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		ServiceResponse<Void> response = ServiceResponse.newInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transfer", transfer);
		stateMachineEngine.startAsync("TransferService", null, params, new AsyncCallback() {
			@Override
			public void onFinished(ProcessContext context, StateMachineInstance stateMachineInstance) {
				Map<String, Object> var = context.getVariables();
				Map<String, Object> context1 = stateMachineInstance.getContext();
				Map<String, Object> endParams = stateMachineInstance.getEndParams();
				String errorCode = (String)endParams.get("_statemachine_error_code_");
				String errorMsg = (String)endParams.get("_statemachine_error_message_");
			}

			@Override
			public void onError(ProcessContext context, StateMachineInstance stateMachineInstance, Exception exp) {
				// TODO Auto-generated method stub
			}
		});
		response.setMessage("转账成功");
		return response;
	}

}
