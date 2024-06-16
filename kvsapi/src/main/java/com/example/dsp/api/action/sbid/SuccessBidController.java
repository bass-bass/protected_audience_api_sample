package com.example.dsp.api.action.sbid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.dsp.common.logger.Logger;

@Controller
public class SuccessBidController {
	private static final Logger logger = Logger.getLogger(SuccessBidController.class.getSimpleName());
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = { "/sbid/{action}" })
	public void dispatch(
			@PathVariable("action") String action,
			@RequestParam(value = "bid") int bidPrice,
			@RequestParam(value = "ssp") String sspDomain,
			@RequestParam(value = "publisher") String publisher,
			@RequestParam(value = "render_url") String renderUrl) throws Exception {
		try {
			switch (action) {
				case "b":
					logger.info("bid: bidPrice -> " + bidPrice + " sspDomain -> " + sspDomain + " publisher -> " + publisher + " renderUrl -> " + renderUrl);
					return;

				default:
					throw new IllegalArgumentException("action " + action + " is not supported.");
			}

		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
}
