package com.example.dsp.api.action.kvs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.example.dsp.common.io.JsonParser;
import com.example.dsp.common.logger.Logger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class KvsService {
	/**
	 * <pre>
	 * jsonで返す際のtype
	 */
	@Data
	static class KVResult {
		@SuppressWarnings("unused")
		private Map<String, ArbitraryJson> keys = new HashMap<>();
		private Map<String, PerInterestGroupData> perInterestGroupData = new HashMap<>();
		@Data
		@AllArgsConstructor
		static class ArbitraryJson {
			private int bidPrice;
			private int budget;
		}
		@Data
		static class PerInterestGroupData {
			private Map<String, Double> priorityVector = new HashMap<>();
			public void putVector(String key, Double value) {
				priorityVector.put(key, value);
			}
		}
		public void putKeys(String key, ArbitraryJson value) {
			keys.put(key, value);
		}
		public void putData(String key, PerInterestGroupData value) {
			perInterestGroupData.put(key, value);
		}
		/**
		 * <pre>
		 * {keys : {key1 : value1, key2 : value2, ...}}
		 * の形式で返す必要がある
		 */
		@Override
		public String toString() {
			return JsonParser.toJson(this);
		}
	}
	private static final Logger logger = Logger.getLogger(KvsService.class.getSimpleName());
	private static final String COMMA = ",";
	public String getValues(String host, String keyNames, String interestGroupNames) throws Exception {
		String[] keys = keyNames.split(COMMA);
		String[] interestGroups = interestGroupNames.split(COMMA);
		logger.info("host : " + host + ", keys : " + Arrays.toString(keys) + ", interestGroups : " + Arrays.toString(interestGroups));
		KVResult result = new KVResult();
		// set keys
		Map<String, String> keyMap = new HashMap<>();
		Random rnd = new Random(100);
		for(String key : keys) {
			int bidPrice = rnd.nextInt(101) + 1; // 1~100
			int budget = bidPrice * 2000;
			result.putKeys(key, new KVResult.ArbitraryJson(bidPrice, budget));
		}

		// set perInterestGroupData
		Map<String, KVResult.PerInterestGroupData> optionalMap = new HashMap<>();
		for(String group : interestGroups) {
			KVResult.PerInterestGroupData data = new KVResult.PerInterestGroupData();
			for(int i=0; i<3; i++) {
				String k = "signal" + (i + 1);
				double v = rnd.nextDouble();
				data.putVector(k, v);
			}
			result.putData(group, data);
		}

		logger.info("result -> " + result);
		return result.toString();
	}

}
