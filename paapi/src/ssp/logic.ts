import { AuctionConfig, BrowserSignals4SSP } from "../types/types";

globalThis.scoreAd = (
	adMetadata: {
		adName: string;
	},
	bid: number,
	auctionConfig: AuctionConfig,
	trustedScoringSignals,
	browserSignals: BrowserSignals4SSP,
) => {
	console.log(
		"scoreAd",
		JSON.stringify({
			adMetadata,
			bid,
			auctionConfig,
			trustedScoringSignals,
			browserSignals,
		}),
	);
	return bid;
};

globalThis.reportResult = (
	auctionConfig: AuctionConfig,
	browserSignals: BrowserSignals4SSP,
) => {
	console.log(
		"reportResult",
		JSON.stringify({
			auctionConfig,
			browserSignals,
		}),
	);
};
