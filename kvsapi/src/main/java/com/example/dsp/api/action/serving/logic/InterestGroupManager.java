package com.example.dsp.api.action.serving.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.common.conf.InterestGroup;
import com.example.dsp.common.io.CustomCipher;
import com.example.dsp.common.logger.Logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class InterestGroupManager {
	@Getter
	@AllArgsConstructor
	public static class IgInfo {
		int id;
		int[] adIds;
		int seed;
	}
	private static final Logger logger = Logger.getLogger(InterestGroupManager.class.getSimpleName());
	private static final CustomCipher cipher = new CustomCipher("mykey", "Blowfish");
	private static final int lifeTimeDays = 30; // interestGroupの期限 (day)
	private static final int kSecsPerDay = 1000 * 60 * 60 * 24; // (ms/day)
	private static final long lifeTimeMs = (long) lifeTimeDays * kSecsPerDay; // (ms)
	private static final String OWNER_ORIGIN = "https://localhost:44303";
	private static final String ALLOWED_REPORTING_ORIGIN = OWNER_ORIGIN;

	public static void setInterestGroups(ServingForm form) {
		List<IgInfo> joinIgInfoList = createIgInfoList(100);
		form.setInterestGroups4Join(createInterestGroups(form, joinIgInfoList));

		List<IgInfo> leaveIgInfoList = createIgInfoList(100);
		form.setInterestGroups4Leave(createInterestGroups(form, leaveIgInfoList));
	}

	/**
	 * update用のinterestGroup作成
	 * ads, trustedBiddingSignalsKeysの更新
	 * @param form
	 */
	public static void setInterestGroupById(ServingForm form) {
		InterestGroup interestGroup = new InterestGroup();
		IgInfo igInfo = createIgInfo(form.getIgId(), 500);
		try {
			interestGroup.setAds(createAds(igInfo));
			interestGroup.setTrustedBiddingSignalsKeys(createSignalKeys(igInfo));
			form.setInterestGroup4Update(interestGroup);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static List<IgInfo> createIgInfoList(int seed) {
		Random r = new Random(seed);
		List<IgInfo> igInfoList = new ArrayList<>();
		igInfoList.add(createIgInfo(r.nextInt(10000), r.nextInt(1000)));
		igInfoList.add(createIgInfo(r.nextInt(10000), r.nextInt(1000)));
		igInfoList.add(createIgInfo(r.nextInt(10000), r.nextInt(1000)));
		return igInfoList;
	}

	public static IgInfo createIgInfo(int id, int seed) {
		Random r = new Random(seed);
		return new IgInfo(id, new int[] {r.nextInt(1000), r.nextInt(1000), r.nextInt(1000)}, seed);
	}

	public static List<InterestGroup> createInterestGroups(ServingForm form, List<IgInfo> igInfoList) {
		List<InterestGroup> interestGroups = new ArrayList<>();
		for (IgInfo igInfo : igInfoList) {
			try {
				interestGroups.add(createInterestGroup(form, igInfo));
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return interestGroups;
	}

	public static InterestGroup createInterestGroup(ServingForm form, IgInfo info) throws Exception {
		InterestGroup interestGroup = new InterestGroup();
		interestGroup.setOwner(OWNER_ORIGIN);
		interestGroup.setName(String.valueOf(info.getId()));
		interestGroup.setLifetimeMs("" + lifeTimeMs);
		interestGroup.setUserBiddingSignals(createUserBiddingSignals(form));
		interestGroup.setBiddingLogicUrl(createBiddingLogicUrl());
		interestGroup.setDailyUpdateUrl(createDailyUpdateUrl(info.getId()));
		interestGroup.setAds(createAds(info));
		interestGroup.setTrustedBiddingSignalsUrl(createTrustedBiddingSignalsUrl());
		interestGroup.setTrustedBiddingSignalsKeys(createSignalKeys(info));
		return interestGroup;
	}

	private static InterestGroup.UserBiddingSignals createUserBiddingSignals(ServingForm form) {
		InterestGroup.UserBiddingSignals userBiddingSignals = new InterestGroup.UserBiddingSignals();
		return userBiddingSignals;
	}

	private static String createBiddingLogicUrl() {
		return OWNER_ORIGIN + "/dsp/logic";
	}

	private static String createDailyUpdateUrl(int igId) throws Exception {
		return OWNER_ORIGIN + "/dsp/update?ig=" + igId;
	}
	private static String createTrustedBiddingSignalsUrl() {
		return OWNER_ORIGIN + "/kvs/getvalues";
	}

	private static int[] createSignalKeys(IgInfo igInfo) {
		return igInfo.getAdIds();
	}

	private static String createRenderUrl(int adId) throws Exception {
		String encryptedData = cipher.encrypt("" + adId);
		return OWNER_ORIGIN + "/dsp/creative/?ad=" + encryptedData;
	}

	private static InterestGroup.AdMetadata createAdMetaData(int adId) {
		InterestGroup.AdMetadata adMetadata = new InterestGroup.AdMetadata();
		adMetadata.setAd(adId);
		return adMetadata;
	}

	private static List<InterestGroup.Ad> createAds(IgInfo info) throws Exception {
		List<InterestGroup.Ad> ads = new ArrayList<>();
		for (int adId : info.getAdIds()) {
			InterestGroup.Ad ad = new InterestGroup.Ad();
			ad.setRenderUrl(createRenderUrl(adId));
			ad.setAllowedReportingOrigins(new String[] { ALLOWED_REPORTING_ORIGIN });
			ad.setMetadata(createAdMetaData(adId));
			ads.add(ad);
		}
		return ads;
	}
}
