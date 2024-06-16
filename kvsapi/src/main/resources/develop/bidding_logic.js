function generateBid(group,auctionSignals,perBuyerSignals,trustedBiddingSignals,browserSignals) {
	console.log(
		"generateBid",
		JSON.stringify({
			group,
			auctionSignals,
			perBuyerSignals,
			trustedBiddingSignals,
			browserSignals,
		}),
	);
	return {
		bid: 150,
		ad: {
			adName: "adName",
		},
		render: group.ads[1].renderUrl,
	};
};

function reportWin(auctionSignals,perBuyerSignals,sellerSignals,browserSignals) {
	console.log(
		"reportWin",
		JSON.stringify({
			auctionSignals,
			perBuyerSignals,
			sellerSignals,
			browserSignals,
		}),
	);

	const bid = browserSignals.bid;
	const ssp = encodeURIComponent(browserSignals.seller);
	const publisher = browserSignals.topWindowHostname;
	const renderUrl = encodeURIComponent(browserSignals.renderURL);
	// bid
	const url = `https://localhost:44303/sbid/b?bid=${bid}&ssp=${ssp}&publisher=${publisher}&render_url=${renderUrl}`;
	sendReportTo(url);

	// imp, click
	registerAdMacro('BID', bid);
	registerAdMacro('SSP', ssp);
	registerAdMacro('PUBLISHER', publisher);
	registerAdMacro('RENDER_URL', renderUrl);


	registerAdBeacon({
		'reserved.top_navigation_start': `https://localhost:44303/acq/cv?bid=${bid}&ssp=${ssp}&publisher=${publisher}&render_url=${renderUrl}`,
		'reserved.top_navigation_commit': `https://localhost:44303/acq/cv?bid=${bid}&ssp=${ssp}&publisher=${publisher}&render_url=${renderUrl}`
	});


};
