function dbltable(url, dialogid, title, wth, ht) {
	$.pdialog.open(url, dialogid, title, {
		max : false,
		mask : true,
		width : wth,
		height : ht
	});
}