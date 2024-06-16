window.fence.reportEvent({
    'destinationURL' : 'https://localhost:44303/acq/view?bid=${BID}&ssp=${SSP}&publisher=${PUBLISHER}&render_url=${RENDER_URL}',
});

window.fence.setReportEventDataForAutomaticBeacons({
  'eventType': 'reserved.top_navigation_start',
  'eventData' : 'top-level navigation start.',
  'destination': [],
  'crossOriginExposed': true,
  'once' : true,
});

function sendClickEvent() {
    window.fence.reportEvent({
        'destinationURL' : 'https://localhost:44303/acq/dac?bid=${BID}&ssp=${SSP}&publisher=${PUBLISHER}&render_url=${RENDER_URL}',
    });
}
