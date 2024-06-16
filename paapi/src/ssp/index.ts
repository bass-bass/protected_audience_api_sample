window.addEventListener("DOMContentLoaded", async (event) => {
	const auctionConfig = {
		seller: "https://localhost:44304",
		decisionLogicUrl: "https://localhost:44304/ssp/logic.js",
		interestGroupBuyers: ["https://localhost:44303"],
		auctionSignals: {
			publisherId: "12345",
			auctionType: "first-price",
		},
		requestedSize: { width: "304", height: "144"},
		/*
		perBuyerGroupLimits: {'https://localhost:44303': 2,
        },
  		perBuyerPrioritySignals: {'https://localhost:44303': {
			'signal1': 0.5,
            'signal2': 1.0,
            'signal3': 1.5,                        
			},
		},
		*/
		resolveToConfig: true, // true: fencedframe, false: iframe (ref : https://developers.google.com/privacy-sandbox/relevance/fenced-frame?hl=ja#examples)
	};
	const result = await navigator.runAdAuction(auctionConfig);
	const container = document.getElementById("pa-ad-container");
	//container.src = result; // iframe
	container.config = result; // fencedframe
});
