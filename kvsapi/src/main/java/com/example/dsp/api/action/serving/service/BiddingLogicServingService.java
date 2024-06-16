package com.example.dsp.api.action.serving.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.api.action.serving.logic.ServingLogic;
import com.example.dsp.common.logger.Logger;

@Component
public class BiddingLogicServingService {
	private static final Logger logger = Logger.getLogger(BiddingLogicServingService.class.getSimpleName());
	@Autowired
	private ServingLogic logic;
	public ResponseEntity<String> execute(ServingForm form) throws IOException {
		ResponseEntity<String> response = logic.makeLogicTag(form);
		logger.info("res body : " + response.getBody());
		return response;
	}
}
