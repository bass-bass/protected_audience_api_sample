# Structure

`api/action/`
* `kvs`
  * PAオークション開始前に呼び出し -> generateBidで使用
  * key : adId, value : 残予算など
  * e.g. `https://www.kv-server.example/getvalues?hostname=publisher.com&keys=key1,key2&interestGroupNames=name1,name2&experimentGroupId=12345&slotSize=100,200`
  * ref : https://github.com/WICG/turtledove/blob/main/FLEDGE.md#3-buyers-provide-ads-and-bidding-functions-byos-for-now
* `sbid`
  * 落札通知先
  * `reportWin.sendReportTo()`で叩く
* `acq`
  * view, clickの通知先
  * FencedFrameの`reportEvent`から発火して叩く
  * ref : https://github.com/WICG/turtledove/blob/main/Fenced_Frames_Ads_Reporting.md
* `serving `
  * PAで使用するhtml,jsの提供,dailyUpdateUrlの呼び出し先
  * `index` -> `JoinAdInterestGroupService` -> joinAdInterestGroup.html
  * `creative` -> `CreativeServingService` -> creative.html
  * `logic` -> `BiddingLogicServingService` -> bidding_logic.js
  * `update` -> `UpdateInterestGroupService` -> updateInterestGroup.json