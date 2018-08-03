    var _msgdiv = $("#_gwdmetrics");
    0 == _msgdiv.length && (_msgdiv = $('<div id="_gwdmetrics"></div>'), _msgdiv.appendTo($("body")));
    var _dom = $(".qa-perf-dom-base");
        _base = $(".qa-perf-win-base");
        _h1tags = $("h1").length;
		_siteRoot = $("meta[name='siteRoot']").attr("content");
		_canonical = $("link[rel='canonical']");
		_canonHref = _canonical.attr("href");
		_rootRegex = new RegExp("(" + _siteRoot.replace("/", "\/") + ")", "g");
		_siteRootCount = (_canonHref.match(_rootRegex) || []).length;
		_canonicalMatchCurrentPage = ((_canonHref.substring(_canonHref.indexOf("/", _canonHref.indexOf("//")+2))) == location.pathname.replace(location.origin, ""));
        _res = [];
		_res[_res.length] = "\n------------------- SEO CHECK ---------------------------\n"; 
		_res[_res.length] = "<span style='color:" + (1 == _h1tags ? "green" : "red") + "'>H1 tag count:" + _h1tags + "</span>";
		_res[_res.length] = "<span style='color:" + (1 == _canonical.length ? "green" : "red") + "'>Canonical tag count:" + _canonical.length + "</span>";
		_res[_res.length] = "<span style='color:" + (_canonHref.indexOf("http") == 0 ? "green" : "red") + "'>Canonical tag url starts with <b>'http':</b>" + _canonHref + "</span>";
		_res[_res.length] = "<span style='color:" + (_siteRootCount == 1 ? "green" : "red") + "'>Canonical tag siteroot count:" + _siteRootCount + "</span>";

		if(!_canonicalMatchCurrentPage)
		{
			_res[_res.length] = "<span style='color:" + (_canonicalMatchCurrentPage ? "green" : "orange") + "'>Canonical tag value [" +  (_canonHref.substring(_canonHref.indexOf("/", _canonHref.indexOf("//")+2))) + "] does not match current page url [" + (location.pathname.replace(location.origin, "")) + "]</span>";
		}
		
		_res[_res.length] = "\n------------------ PAGE TIMINGS -------------------------\n";
		_res[_res.length] = _base.eq(2).text();
		_res[_res.length] = _base.eq(0).text();
		_res[_res.length] = _base.eq(2).text();
		_res[_res.length] = _base.eq(4).text();
		_res[_res.length] = "All resources " + _base.eq(5).text();		
		_res[_res.length] = "------------ Resource count WITHIN domReady ----------------\n";
		_res[_res.length] = _dom.eq(0).text();
		_res[_res.length] = _dom.eq(1).text();
		_res[_res.length] = _dom.eq(2).text();
    var _img = $(".qa-perf-dom-img");
    _img.each(function() {
        _res[_res.length] = $(this).text().split("?")[0]
    });
	_msgdiv.html(_res.join("<div/>"));
	_msgdiv.dialog({
        width: "75%",
        modal: !0,
        title: location.href,
        buttons: {
            Ok: function() {
                $(this).dialog("close")
            }
        }
    })
