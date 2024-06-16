package com.example.dsp.api.action.serving;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.api.action.serving.service.BiddingLogicServingService;
import com.example.dsp.api.action.serving.service.CreativeServingService;
import com.example.dsp.api.action.serving.service.IndexServingService;
import com.example.dsp.api.action.serving.service.JoinAdInterestGroupService;
import com.example.dsp.api.action.serving.service.UpdateInterestGroupService;
import com.example.dsp.common.logger.Logger;

@Controller
public class ServingController {
	private static final Logger logger = Logger.getLogger(ServingController.class.getSimpleName());
	private static final String EMPTY = "";
	@Autowired
	private IndexServingService indexService;
	@Autowired
	private JoinAdInterestGroupService joinAdIgService;
	@Autowired
	private CreativeServingService creativeService;
	@Autowired
	private BiddingLogicServingService biddingLogicService;
	@Autowired
	private UpdateInterestGroupService updateIgService;

	@GetMapping(value = {"/dsp/{action}"})
	public ResponseEntity<String> dispatch(
			@PathVariable("action") String action,
			@RequestHeader(value = "referer", defaultValue = EMPTY) String refererH,
			@RequestHeader(value = "User-Agent", defaultValue = EMPTY) String userAgent,
			@RequestParam(value = "prf", defaultValue = EMPTY) String preReferer,
			@RequestParam(value = "rf", defaultValue = EMPTY) String referer,
			@RequestParam(value = "ig", defaultValue = EMPTY) String igId,
			@RequestParam(value = "ed", defaultValue = EMPTY) String encryptedData,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServingForm form = new ServingForm(request, response);
		form.setRequest(request);
		form.setResponse(response);
		form.setAction(action);
		form.setUserAgent(userAgent);
		form.setReferer(referer);
		form.setPreReferer(preReferer);
		form.setRefererH(refererH);
		form.setEncryptedData(encryptedData);
		form.setLpUrl("https://localhost:44301/advertiser/lp.html");
		form.setCreativeUrl("https://localhost:44301/advertiser/banner.png");
		form.setIgId(igId);
		try {
			form.init();
			switch (action) {
				case "index.js":
					return indexService.execute(form);
				case "join":
					return joinAdIgService.execute(form);
				case "creative":
					return creativeService.execute(form);
				case "logic":
					return biddingLogicService.execute(form);
				case "update":
					return updateIgService.execute(form);
				default:
					throw new IllegalArgumentException("action " + action + " is not supported.");
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
}
