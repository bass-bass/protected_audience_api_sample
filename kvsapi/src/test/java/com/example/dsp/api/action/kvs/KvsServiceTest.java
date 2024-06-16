package com.example.dsp.api.action.kvs;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class KvsServiceTest {
	@Test
	public void test() {
		String[] keys = {"group", "group1", "group2"};
		String[] interestGroups = {"group1", "group2"};
		KvsService.KVResult result = new KvsService.KVResult();
		// set keys
		Map<String, String> keyMap = new HashMap<>();
		Random rnd = new Random(100);
		for(String key : keys) {
			int bidPrice = rnd.nextInt(101) + 1; // 1~100
			int budget = bidPrice * 2000;
			result.putKeys(key, new KvsService.KVResult.ArbitraryJson(bidPrice, budget));
		}

		// set perInterestGroupData
		Map<String, KvsService.KVResult.PerInterestGroupData> optionalMap = new HashMap<>();
		for(String group : interestGroups) {
			KvsService.KVResult.PerInterestGroupData data = new KvsService.KVResult.PerInterestGroupData();
			for(int i=0; i<3; i++) {
				String k = "signal" + (i + 1);
				double v = rnd.nextDouble();
				data.putVector(k, v);
			}
			result.putData(group, data);
		}

		System.out.println(result.toString());
	}
}
