package com.example.dsp.api.action.serving.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.api.action.serving.logic.InterestGroupManager;

@Component
public class UpdateInterestGroupService {

	public ResponseEntity<String> execute(ServingForm form) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Ad-Auction-Allowed", "true");
		responseHeaders.set("SUPPORTS-LOADING-MODE", "fenced-frame");
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		InterestGroupManager.setInterestGroupById(form);
		return new ResponseEntity<>(form.getInterestGroup4Update().toJson(), responseHeaders, HttpStatus.OK);
	}

}
