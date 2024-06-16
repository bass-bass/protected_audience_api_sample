package com.example.dsp.api.action.serving.form;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dsp.common.conf.InterestGroup;
import com.example.dsp.common.io.CustomCipher;
import com.example.dsp.common.logger.Logger;

import lombok.Data;

@Data
public class ServingForm {
	private static final Logger logger = Logger.getLogger(ServingForm.class.getSimpleName());
	private CustomCipher cipher;
	private String scheme;

	private InterestGroup ig;
	private String action;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String referer;
	private String preReferer;
	private String refererH;
	private String userAgent;
	private String encryptedData;
	private int igId;
	private String target;
	private boolean isJoin;
	private boolean isUpdate;
	private boolean isCreative;
	private boolean isLogic;
	private boolean isIndex;

	// join
	private List<InterestGroup> interestGroups4Join;
	private List<InterestGroup> interestGroups4Leave;
	// update
	private InterestGroup interestGroup4Update;
	// creative
	private int adId;
	private int sbId;
	private String lpUrl;
	private String creativeUrl;

	public ServingForm(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void init() throws Exception {
		setTarget("_blank");
		this.cipher = new CustomCipher("mykey", "Blowfish");
	}

	public void setAction(String action) throws Exception {
		this.action = action;
		switch (action) {
			case "join":
				this.isJoin = true;
				return;

			case "update":
				this.isUpdate = true;
				return;

			case "creative":
				this.isCreative = true;
				return;

			case "logic":
				this.isLogic = true;
				return;

			case "index.js":
				this.isIndex = true;
				return;

			default:
				logger.error("Invalid action : " + action);
				throw new Exception("Invalid action : " + action);
		}
	}

	public void setReferer(String referer) throws UnsupportedEncodingException {
		this.referer = URLDecoder.decode(referer, "UTF-8");
	}

	public void setPreReferer(String preReferer) throws UnsupportedEncodingException {
		this.preReferer = URLDecoder.decode(preReferer, "UTF-8");
	}

	public void setRefererH(String refererH) throws UnsupportedEncodingException {
		this.refererH = URLDecoder.decode(refererH, "UTF-8");
	}

	public void setIgId(String igId) {
		if (igId != null && !igId.isEmpty()) {
			try {
				this.igId = Integer.parseInt(igId);
			} catch (NumberFormatException e) {
				logger.error("igId is not a number: " + igId);
			}
		}
	}

}
