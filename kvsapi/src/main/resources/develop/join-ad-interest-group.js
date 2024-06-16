(function () {
    'use strict';

    window.MyFunction = window.MyFunction || {};

    window.MyFunction.joinAdInterestGroup = function () {
        var rf = window.top.location.href, prf = window.top.document.referrer;
        var iframe = document.createElement('iframe');
        iframe.src = 'https://localhost:44303/dsp/join?rf=' + encodeURIComponent(rf) + '&prf=' + encodeURIComponent(prf);
        iframe.allow = 'join-ad-interest-group';
        iframe.width = '0';
        iframe.height = '0';
        iframe.style = 'display: none; visibility: hidden;';

        document.body.appendChild(iframe);
    }
})();
