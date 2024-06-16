package com.example.dsp.api.action.acq;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.dsp.common.logger.Logger;

@Controller
public class AcqController {
	private static final Logger logger = Logger.getLogger(AcqController.class.getSimpleName());

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = { "/acq/{action}" })
	public void dispatch(
			@PathVariable("action") String action,
			@RequestParam(value = "bid") int bidPrice,
			@RequestParam(value = "ssp") String sspDomain,
			@RequestParam(value = "publisher") String publisher,
			@RequestParam(value = "render_url") String renderUrl) throws Exception {
		try {
			switch (action) {
				case "view":
					// AcqViewController (tpas_api)
					logger.info("imp: bidPrice -> " + bidPrice + " sspDomain -> " + sspDomain + " publisher -> " + publisher + " renderUrl -> " + renderUrl);
					return;

				case "dac":
					// AcqDacController (tpas_api)
					logger.info("click: bidPrice -> " + bidPrice + " sspDomain -> " + sspDomain + " publisher -> " + publisher + " renderUrl -> " + renderUrl);
					return;

				default:
					throw new IllegalArgumentException("action " + action + " is not supported.");
			}

		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping(value = { "/acq/{action}" })
	public void dispatch2(
			@PathVariable("action") String action,
			@RequestParam(value = "bid") int bidPrice,
			@RequestParam(value = "ssp") String sspDomain,
			@RequestParam(value = "publisher") String publisher,
			@RequestParam(value = "render_url") String renderUrl,
			@RequestBody(required = false) String data) throws Exception {
		try {
			switch (action) {
				case "cv":
					// AcqViewController (tpas_api)
					logger.info("cv: bidPrice -> " + bidPrice + " sspDomain -> " + sspDomain + " publisher -> " + publisher + " renderUrl -> " + renderUrl);
					logger.info("data -> " + data);
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
