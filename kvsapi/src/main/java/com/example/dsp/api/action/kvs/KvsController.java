package com.example.dsp.api.action.kvs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.dsp.common.logger.Logger;

@Controller
public class KvsController {
	private static final Logger logger = Logger.getLogger(KvsController.class.getSimpleName());
	@Autowired
	private KvsService service;

	@ModelAttribute
	public void setResponseHeader(HttpServletResponse response) {
		// ref: https://github.com/WICG/turtledove/blob/main/FLEDGE.md#3-buyers-provide-ads-and-bidding-functions-byos-for-now
		response.setHeader("X-fledge-bidding-signals-format-version", "2");
		response.setHeader("Ad-Auction-Allowed", "true");
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = { "/kvs/{action}" })
	public String dispatch(
			@PathVariable("action") String action,
			@RequestParam(value = "hostname") String host,
			@RequestParam(value = "keys") String keys,
			@RequestParam(value = "interestGroupNames") String interestGroupNames) throws Exception {

		try {
			switch (action) {
				case "getvalues":
					return service.getValues(host, keys, interestGroupNames);

				default:
					throw new IllegalArgumentException("only supported for action getvalues.");
			}

		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
}
