export type JSONPrimitive = string | number | boolean | null;
export type JSONValue = JSONPrimitive | JSONObject | JSONArray;
export type JSONArray = Array<JSONValue>;
/**
 * something json serializable
 */
export interface JSONObject {
	[key: string]: JSONValue;
}

/**
 * ref : https://developers.google.com/privacy-sandbox/relevance/protected-audience-api/interest-groups?hl=ja#interest-group-properties
 */
export interface InterestGroup {
	owner: string;
	name: string[];
	biddingLogicUrl?: string;
	biddingWasmHelperUrl1?: string;
	dailyUpdateUrl?: string;
	/*
	priority: number;
	priorityVector: { [key: string]: number };
	prioritySignalsOverrides: { [key: string]: number };
	useBiddingSignalsPrioritization : boolean;
	*/
	
	trustedBiddingSignalsUrl?: string;
	trustedBiddingSignalsKeys?: string[];
	userBiddingSignals?: { [key: string]: string };
	// ref : https://github.com/WICG/turtledove/blob/main/FLEDGE.md#3-buyers-provide-ads-and-bidding-functions-byos-for-now:~:text=the%20interest%20group.-,The,-ads%20list%20contains
	ads?: Array<{ renderUrl: string, allowedReportingOrigins: Array<string> }>;
	adComponents?: AdComponents[];
}

export interface AdComponents {
	renderUrl: string;
	adRenderId?: string;
	metadata?: JSONObject;
}

/**
 * ref : https://github.com/WICG/turtledove/blob/main/FLEDGE.md#21-initiating-an-on-device-auction
 */
export interface AuctionConfig {
	seller: string;
	decisionLogicUrl?: string;
	trustedScoringSignalsUrl?: string;
	interestGroupBuyers?: string[];
	auctionSignals?: JSONObject;
	sellerSignals?: JSONObject;
	sellerTimeout?: number;
	perBuyerSignals?: {
		[key: string]: unknown;
	};
	perBuyerTimeouts?: {
		[key: string]: number;
	};
	componentAuctions?: unknown[];
}

/**
 * chromeでの結果から抽出
 */
export interface BrowserSignals4DSP {
	topWindowHostname: string;
	interestGroupOwner: string;
	interestGroupName: string;
	renderURL: string;
	renderUrl: string;
	seller: string;
	bid: number;
	bidCurrency: string;
	joinCount: number;
	bidCount: number;
	recency: number;
	prevWins: Array<[number, RenderUrlObject]>;
	prevWinsMs: Array<[number, RenderUrlObject]>;
	highestScoringOtherBid?: number;
	highestScoringOtherBidCurrency?: string;
}

export interface BrowserSignals4SSP {
	topWindowHostname: string;
	interestGroupOwner: string;
	renderURL: string;
	renderUrl: string;
	bid?: number;
	biddingDurationMsec: number;
	bidCurrency: string; // or a more specific type if you have a limited set of currencies
	highestScoringOtherBid?: number;
	highestScoringOtherBidCurrency?: string;
}

export interface RenderUrlObject {
	renderURL: string;
	render_url: string;
}

declare global {
	export interface Navigator {
		joinAdInterestGroup: (group: InterestGroup, duration: number) => void;
		/**
		 * 広告オークション結果を表す URN（urn:uuid:<something>）に解決される Promise を返します
		 * @param config
		 * @returns
		 */
		runAdAuction: (config: AuctionConfig) => Promise<string>;
	}

	export interface HTMLElement {
		/**
		 * Navigator.runAdAuction で返された `URN に解決されるPromise` が入る.
		 */
		src: string;
		config: string;
	}
	export interface InterestGroupReportingScriptRunnerGlobalScope {
  		sendReportTo: (url: string) => void;
  		registerAdBeacon: (map: Map<string, string>) => void;
  		registerAdMacro: (name: string, value: string) => void;
	}
}
