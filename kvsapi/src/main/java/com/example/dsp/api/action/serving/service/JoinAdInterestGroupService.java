package com.example.dsp.api.action.serving.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.api.action.serving.logic.InterestGroupManager;
import com.example.dsp.api.action.serving.logic.ServingLogic;
import com.example.dsp.common.conf.InterestGroup;
import com.example.dsp.common.logger.Logger;

@Component
public class JoinAdInterestGroupService {
	private static final Logger logger = Logger.getLogger(JoinAdInterestGroupService.class.getSimpleName());
	@Autowired
	private ServingLogic logic;

	public ResponseEntity<String> execute(ServingForm form) {
		InterestGroupManager.setInterestGroups(form);
		List<InterestGroup> igs4Join = form.getInterestGroups4Join();
		List<InterestGroup> igs4Leave = form.getInterestGroups4Leave();
		ResponseEntity<String> response = logic.makeJoinTag(form, igs4Join, igs4Leave);
		logger.info("res body : " + response.getBody());
		return response;
	}

}
