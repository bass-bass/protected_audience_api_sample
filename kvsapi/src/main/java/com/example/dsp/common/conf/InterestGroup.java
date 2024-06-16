package com.example.dsp.common.conf;

import java.io.Serializable;
import java.util.List;

import com.example.dsp.common.io.JsonParser;

import lombok.Data;

/**
 * <pre>
 *     navigator.joinAdInterestGroup()で与えるinterestGroupのデータ構造
 *     owner,nameが必須項目
 *     オークションに参加する場合はbiddingLogicUrl,adsが必須
 *     updateで更新可能な項目は現状以下
 *     		- biddingLogicUrl
 *     		- biddingWasmHelperUrl
 *     	 	- trustedBiddingSignalsUrl
 *     	 	- trustedBiddingSignalsKeys
 *     	 	- ads
 *     	 	- priority
 *
 *     ref : https://github.com/WICG/turtledove/blob/main/FLEDGE.md#1-browsers-record-interest-groups
 *     ref : https://developers.google.com/privacy-sandbox/blog/fledge-api?hl=ja#interest-group-properties
 * </pre>
 */
@Data
public class InterestGroup implements Serializable {
	private static final long serialVersionUID = 7909638583535755329L;

	@Data
	public static class Ad implements Serializable {
		private static final long serialVersionUID = -8791836284809027840L;
		private String renderUrl;
		private AdMetadata metadata;
		private String[] allowedReportingOrigins;
	}

	@Data
	public static class AdMetadata implements Serializable {
		private static final long serialVersionUID = 3675549162211424217L;
		private int ad; // adId
	}

	@Data
	public static class UserBiddingSignals implements Serializable {
		private static final long serialVersionUID = 7960477125461476769L;
	}

	private String owner;
	private String name;
	private String lifetimeMs;
	private UserBiddingSignals userBiddingSignals;
	private String biddingLogicUrl;
	private String biddingWasmHelperUrl;
	private String dailyUpdateUrl;
	private List<Ad> ads;
	private List<Ad> adComponents;
	private String trustedBiddingSignalsUrl;
	private int[] trustedBiddingSignalsKeys;

	public String toJson() {
		return JsonParser.toJsonWithoutHTMLSafe(this);
	}
}
