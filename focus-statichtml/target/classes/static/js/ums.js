var days = new Array("日", "一", "二", "三", "四", "五", "六");
function showDT() {
	var currentDT = new Date();
	var y, m, date, day, hs, ms, ss, theDateStr;
	y = currentDT.getFullYear(); //四位整数表示的年份  
	m = currentDT.getMonth()+1; //月  
 	m =(m<10 ? "0"+m:m);
	date = currentDT.getDate(); //日  
	day = currentDT.getDay(); //星期  
	hs = currentDT.getHours(); //时  
	ms = currentDT.getMinutes(); //分  
	ss = currentDT.getSeconds(); //秒 
	if (hs >= 0 && hs < 10) {
		hs = "0" + hs;
	}
	if (ms >= 0 && ms < 10) {
		ms = "0" + ms;
	}
	if (ss >= 0 && ss < 10) {
		ss = "0" + ss;
	}
	theDateStr = y + "年" + m + "月" + date + "日 星期" + days[day] + " " + hs + ":"
			+ ms + ":" + ss;

	document.getElementById("theClock").innerHTML = theDateStr;
	// setTimeout 在执行时,是在载入后延迟指定时间后,去执行一次表达式,仅执行一次  
	window.setTimeout(showDT, 1000);
}
function loadTableData(murl, tid) {
	$.ajax( {
		type : "POST",
		url : murl,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			$("#" + tid + " tbody").empty();
			$.each(jsondata, function(name, value) {
				var newRow = getRow(name, value);
				$("#" + tid + " tbody").append(newRow);
			});
		}
	});
}
function getRow(name, value, k) {
	var row = '';
	if (name == '0') {
		row = '<thead><tr><th colspan="1001">' + value["title"] + "</th></tr>";
	} else if (name == '1') {
		row = '<tr>';
		if (typeof value["th"] == "string") {
			var thvalue = value["th"].split(",");
			for ( var i = 0; i < thvalue.length; i++) {
				row += "<td>" + thvalue[i] + '</td>';
			}
			row += "</tr></thead><tbody>";
		}

	} else {
		if (typeof value["tr"] == "string") {
			row = '<tr>';
			var trvalue = value["tr"].split(",");
			for ( var i = 0; i < trvalue.length; i++) {
				row += "<td>" + trvalue[i] + '</td>';
			}
			row += "</tr>";
		} else {
			row = '<tr><td>' + parseInt(name - 1) + '</td>';
			var i = 1;
			for ( var key in value) {
				if (i <= k) {
					row += "<td>" + value[key] + '</td>';
				}
				i++;
			}
			row += "</tr>";
		}

	}
	return row;
}
function loadData(murl, datatype, divid) {
	$.ajax( {
		type : "POST",
		url : murl + "&datatype=" + datatype,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			require.config( {
				paths : {
					echarts : './js/echarts-2.2.7/build/dist/'
				}
			});
			require( [ 'echarts', 'echarts/chart/' + datatype ], function(ec) {
				var myChart = ec.init(document.getElementById(divid));
				var option = jsondata;
				myChart.setOption(option);
				window.onresize = myChart.resize;
			});
		}
	});
}
function loadGroupTableDataTimer(murl, groupId, tid, timerid) {
	//alert($('#'+timerid).val()+'秒');
	$.ajax( {
		type : "POST",
		url : murl,
		data : {
			'groupId' : groupId
		},
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			$("#" + tid + " tbody").empty();
			$.each(jsondata, function(name, value) {
				var newRow = getGroupRow(name, value);
				$("#" + tid + " tbody").append(newRow);
			});
		}
	});
	setTimeout("loadGroupTableDataTimer('" + murl + "','" + groupId + "','"
			+ tid + "','" + timerid + "')",
			parseInt($("#" + timerid).val() * 1000));
}
function getGroupRow(name, value) {
	var row = '<tr>';

	row += "<td>" + value.AGENTNAME + '</td>';
	row += "<td>" + value.AGENTID + '</td>';
	row += "<td>" + value.DNIS + '</td>';
	row += "<td>" + value.GROUPNAME + '</td>';
	row += "<td>" + value.NOWSTATE + '</td>';
	row += "<td>" + value.ANSWERTIMES + '</td>';
	row += "<td>" + value.CALLTIMES + '</td>';
	row += "<td>" + value.AVGANSWERSEC + '</td>';
	row += "<td>" + value.AVGACW + '</td>';
	row += "<td>" + value.AVGRING + '</td>';
	row += "<td>" + value.LOGINSEC + '</td>';
	row += "<td>" + value.ANSWERSEC + '</td>';
	row += "<td>" + value.ACWSEC + '</td>';
	row += "<td>" + value.RESTSEC + '</td>';
	row += "<td>" + value.READYSEC + '</td>';
	row += "<td>" + value.STATETIMES + '</td>';
	row += "<td>" + value.RESTTIMES + '</td>';

	row += "</tr>";
	return row;
}
function loadDataTimer(murl, datatype, divid, refreshRate) {
	$.ajax( {
		type : "POST",
		url : murl + "&datatype=" + datatype,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			require.config( {
				paths : {
					echarts : './js/echarts-2.2.7/build/dist/'
				}
			});
			require( [ 'echarts', 'echarts/chart/' + datatype ], function(ec) {
				var myChart = ec.init(document.getElementById(divid));
				var option = jsondata;
				myChart.setOption(option);
				window.onresize = myChart.resize;
			});
		}
	});
	setTimeout("loadDataTimer('" + murl + "','" + datatype + "','" + divid
			+ "','" + refreshRate + "')", parseInt(refreshRate * 1000));
}

function loadMapDataTimer(murl, datatype, divid, refreshRate) {
	$.ajax( {
		type : "POST",
		url : murl + "&datatype=" + datatype,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			require.config( {
				paths : {
					echarts : './js/echarts-2.2.7/build/dist/'
				}
			});
			require( [ 'echarts', 'echarts/chart/' + datatype ], function(ec) {
				var myChart = ec.init(document.getElementById(divid));
				var option = jsondata;
				 var ecConfig = require('echarts/config');
            	myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
                var selected = param.selected;
                var urlArr = ['http://127.0.0.1:8080/focus-umsmanager/screenAction.do?method=loadScreenView&departNo=11&displayNo=jyl20180118125425'];
                for (var p in selected) {
                    if (selected[p]) {
                        switch(p){
                            case '北京':
                                location.href = urlArr[0];
                                break;
                            default:
                                break;
                        }

                    }
                }
            });
				myChart.setOption(option);
				window.onresize = myChart.resize;
			});
		}
	});
	setTimeout("loadMapDataTimer('" + murl + "','" + datatype + "','" + divid
			+ "','" + refreshRate + "')", parseInt(refreshRate * 1000));
}
function loadTableDataTimer(murl, tid, refreshRate, k) {
	$.ajax( {
		type : "POST",
		url : murl,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			$("#" + tid).empty();
			var table = "<table  class='zebra'>";
			$.each(jsondata, function(name, value) {
				var newRow = getRow(name, value, k);
				table += newRow;
			});
			table += "</tbody></table>";
			$("#" + tid).append(table);
		}
	});
	setTimeout("loadTableDataTimer('" + murl + "','" + tid + "','"
			+ refreshRate + "'," + k + ")", parseInt(refreshRate * 1000));
}
function loadTitle(divid, title) {
	title = title.replace('!', '<span style="color: red;">');
	title = title.replace('$', '</span>');
	$("#" + divid).append(
			"<center><font  style='color: white;"
					+ "font-size: 30px; font-family: 微软雅黑;'>" + title
					+ "</font></center>");
}
function loadTableDataTimerPage(murl, tid, refreshRate, k, pagenum) {
	$.ajax( {
		type : "POST",
		url : murl + "&pagenum=" + pagenum,
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			$("#" + tid).empty();
			var table = "<table  class='zebra'>";
			$.each(jsondata, function(name, value) {
				var newRow = getRow(name, value, k);
				table += newRow;
			});
			table += "</tbody></table>";
			$("#" + tid).append(table);
		}
	});
	if (parseInt(pagenum) >= parseInt($("#pagenum").val())) {
		pagenum = 1;
	} else {
		pagenum = parseInt(pagenum + 1);
	}
	setTimeout("loadTableDataTimerPage('" + murl + "','" + tid + "','"
			+ refreshRate + "'," + k + "," + pagenum + ")",
			parseInt(refreshRate * 1000));
}
function loadIdValueTimer(murl, refreshRate, cid) {
	$.ajax( {
		type : "POST",
		url : murl,
		datatype : "json",
		success : function(data) {
			$("#" + cid).val(data);
		}
	});
	setTimeout("loadIdValueTimer('" + murl + "','" + refreshRate + "','" + cid
			+ "')", parseInt(refreshRate * 1000));
}
function loadLineTableDataTimer(murl, tid, timerid) {
	$.ajax( {
		type : "POST",
		url : murl,
		datatype : "json",
		success : function(data) {
			if (data != '') {
				var jsondata = eval("(" + data + ")");
				$("#" + tid + " tbody").empty();
				$.each(jsondata, function(name, value) {
					var newRow = getLineRow(name, value);
					$("#" + tid + " tbody").append(newRow);
				});
			}
		}
	});
	setTimeout("loadLineTableDataTimer('" + murl + "','" + tid + "','"
			+ timerid + "')", parseInt($("#" + timerid).val() * 1000));
}
function getLineRow(name, value) {
	var arr = value[1].split(';');
	var row = "<tr class='fuscous' style='background-color:" + arr[1] + "'>";
	for ( var key in value) {
		row += "<td align='center'>" + value[key].split(';')[0] + '</td>';
	}
	row += "</tr>";
	return row;
}
function loadRefreshRate(ids) {
	$.ajax( {
		type : "POST",
		url : "screenAction.do?method=getScreenRefreshRate",
		datatype : "json",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			$.each(jsondata["monitorObjectList"], function(name, value) {
				var idsarray = ids.split(",");
				for (i = 0; i < idsarray.length; i++) {
					if (value["objectId"] == idsarray[i]) {
						$("#" + idsarray[i]).val(value["webRefreshRate"]);
					}
				}

			});
		}
	});
}
function loadNumDataTimer(murl, divid, refreshRate) {
	$.ajax( {
		type : "POST",
		url : murl,
		datatype : "json",
		success : function(data) {
			if (data != '') {
				var nums = data.split(";");
				var values = nums[0].split(",");
				var colors = nums[1].split(",");
				var color = "75923C";
				$("#" + divid).empty();
				for (i = 0; i < values.length; i++) {
					if (typeof colors[i] == 'string') {
						color = colors[i];
					}
					numdiv = "<button  style='background:#" + color
							+ ";' class='bgt'>" + values[i] + "</button>";
					$("#" + divid).append(numdiv);
				}
			}

		}
	});
	setTimeout("loadNumDataTimer('" + murl + "','"+divid+"','"  + refreshRate
			+ "')", parseInt(refreshRate * 1000));
}

function loadSeatsNumDataTimer(murl, ids, timerid) {
	loadSeatsNumData(murl, ids);
	setTimeout("loadSeatsNumDataTimer('" + murl + "','" + ids + "','" + timerid
			+ "')", parseInt($("#" + timerid).val() * 1000));
}

function loadSeatsNumData(murl, ids) {
	var workspace_no = $('#workspace_no').val();
	if (workspace_no != 0) {

		$.ajax( {
			type : "POST",
			url : murl,
			data : {
				'workspace_no' : workspace_no
			},
			datatype : "json",
			success : function(data) {
				if (data != '') {
					var values = data.split(",");
					var idsarray = ids.split(",");
					for (i = 0; i < idsarray.length; i++) {
						$("#" + idsarray[i]).html(values[i]);
					}
				}

			}
		});
	}
}

function loadAreaShowTimer(murl, tid, timerid) {

	loadAreaShow(murl, tid);
	setTimeout("loadAreaShowTimer('" + murl + "','" + tid + "','" + timerid
			+ "')", parseInt($("#" + timerid).val() * 1000));
}

function loadAreaShow(murl, tid) {
	var workspace_no = $('#workspace_no').val();
	if (workspace_no != 0) {
		$
				.post(
						murl,
						{
							'workspace_no' : workspace_no
						},
						function(data) {
							$("#" + tid).empty();
							for ( var i = 0; i < data.length; i++) {
								var row = "<tr><td width='30px'><font class='ws_font_a'>";
								row += data[i][0].YID
										+ "</font></td><td width='97%'>";
								for ( var j = 0; j < data[i].length; j++) {
									row += "<div class='ws_div01'>";

									row += "<img border='1' class='ws_img' title='";

									row += "坐席名：" + data[i][j].AGENTNAME;
									if (data[i][j].AGENTSTATE != "") {
										row += ",分机号：" + data[i][j].EXTENSION;
									}
									row += ",ID：" + data[i][j].AGENTID;

									if (data[i][j].AGENTSTATE != "") {
										row += ",登录时间："
												+ data[i][j].LASTLOGINTIME;
									}
									row += ",当前状态：";
									if (data[i][j].AGENTSTATE == "") {
										row += "未登录";
										row += "' src='./ums/manager/zxjk/d.png'";
									}
									if (data[i][j].AGENTSTATE == 1) {
										row += "就绪";
										row += "' src='./ums/manager/zxjk/1.png'";
									}
									if (data[i][j].AGENTSTATE == 2) {
										row += "小休";
										row += "' src='./ums/manager/zxjk/2.png'";
									}
									if (data[i][j].AGENTSTATE == 3) {
										row += "振铃";
										row += "' src='./ums/manager/zxjk/3.png'";
									}
									if (data[i][j].AGENTSTATE == 4) {
										row += "呼出";
										row += "' src='./ums/manager/zxjk/4.png'";
									}
									if (data[i][j].AGENTSTATE == 5) {
										row += "事后处理";
										row += "' src='./ums/manager/zxjk/5.png'";
									}
									if (data[i][j].AGENTSTATE == 6) {
										row += "保持";
										row += "' src='./ums/manager/zxjk/6.png'";
									}
									if (data[i][j].AGENTSTATE == 7) {
										row += "呼入";
										row += "' src='./ums/manager/zxjk/7.png'";
									}

									if (data[i][j].AGENTSTATE == "") {
										row += "'./ums/manager/zxjk/d.png'";
									}
									if ((data[i][j].AGENTSTATE == 2 && data[i][j].STATEKEEPSEC > parseInt($(
											"#fjxsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 7 && data[i][j].STATEKEEPSEC > parseInt($(
													"#jrthsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 4 && data[i][j].STATEKEEPSEC > parseInt($(
													"#wbthsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 3 && data[i][j].STATEKEEPSEC > parseInt($(
													"#zlsc").val()))) {
										row += " style='box-shadow:red 3px -3px 5px;'";
									}
									row += "/><div class='ws_div02' region='center'><font class='ws_font'><b>";
									row += data[i][j].AGENTNAME;
									row += "</b></font></div>";
									row += "</div>"
								}
								row += "</td></tr>";
								$("#" + tid).append(row);
							}

						}, 'json');
	}
}
function loadAreaShowTimerIE(murl, tid, timerid) {

	loadAreaShowIE(murl, tid);
	setTimeout("loadAreaShowTimerIE('" + murl + "','" + tid + "','" + timerid
			+ "')", parseInt($("#" + timerid).val() * 1000));
}
function formatSeconds(value) {
	var theTime = parseInt(value);// 秒 
	var theTime1 = 0;// 分 
	var theTime2 = 0;// 小时 
	// alert(theTime); 
	if (theTime > 60) {
		theTime1 = parseInt(theTime / 60);
		theTime = parseInt(theTime % 60);
		// alert(theTime1+"-"+theTime); 
		if (theTime1 > 60) {
			theTime2 = parseInt(theTime1 / 60);
			theTime1 = parseInt(theTime1 % 60);
		}
	}
	var result = "" + parseInt(theTime) + "";
	if (theTime1 > 0) {
		result = "" + parseInt(theTime1) + ":" + result;
	}else{
		result = "00"  + ":" + result;
	}
	if (theTime2 > 0) {
		result = "" + parseInt(theTime2) + ":" + result;
	}else{
		result = "00"  + ":" + result;
	}
	return result;
}
function loadAreaShowIE(murl, tid) {
	var workspace_no = $('#workspace_no').val();
	if (workspace_no != 0) {
		$
				.post(
						murl,
						{
							'workspace_no' : workspace_no
						},
						function(data) {
							$("#" + tid).empty();
							for ( var i = 0; i < data.length; i++) {
								var row = "<tr><td width='30px'><font class='ws_font_a'>";
								row += (i + 1) + "</font></td><td width='97%'>";
								for ( var j = 0; j < data[i].length; j++) {
									var STATEKEEPSEC = "\n当前状态时长："
											+ formatSeconds(data[i][j].STATEKEEPSEC);
									row += "<center><div class='ws_div01'>";

									row += "<img border='1' class='ws_img' title='";

									row += "坐 席 名：" + data[i][j].AGENTNAME;
									if (data[i][j].AGENTSTATE != "") {
										row += "\n分 机 号：" + data[i][j].EXTENSION;
									}
									row += "\n坐 席 ID：" + data[i][j].AGENTID;

									if (data[i][j].AGENTSTATE != "") {
										row += "\n最近登录时间："
												+ data[i][j].LASTLOGINTIME;
									}
									row += "\n当  前  状  态：";
									if (data[i][j].AGENTSTATE == "") {
										row += "未登录";
										row += "' src='./ums/manager/zxjk/d.png'";
									}
									if (data[i][j].AGENTSTATE == 1) {
										row += "就绪" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/1.png'";
									}
									if (data[i][j].AGENTSTATE == 2) {
										row += "小休" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/2.png'";
									}
									if (data[i][j].AGENTSTATE == 3) {
										row += "振铃" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/3.png'";
									}
									if (data[i][j].AGENTSTATE == 4) {
										row += "呼出" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/4.png'";
									}
									if (data[i][j].AGENTSTATE == 5) {
										row += "事后处理" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/5.png'";
									}
									if (data[i][j].AGENTSTATE == 6) {
										row += "保持" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/6.png'";
									}
									if (data[i][j].AGENTSTATE == 7) {
										row += "呼入" + STATEKEEPSEC;
										row += "' src='./ums/manager/zxjk/7.png'";
									}

									if (data[i][j].AGENTSTATE == "") {
										row += "'./ums/manager/zxjk/d.png'";
									}
									if ((data[i][j].AGENTSTATE == 2 && data[i][j].STATEKEEPSEC > parseInt($(
											"#fjxsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 7 && data[i][j].STATEKEEPSEC > parseInt($(
													"#jrthsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 4 && data[i][j].STATEKEEPSEC > parseInt($(
													"#wbthsc").val() * 60))
											|| (data[i][j].AGENTSTATE == 3 && data[i][j].STATEKEEPSEC > parseInt($(
													"#zlsc").val()))) {
										row += " style='border-color: red'";
									}
									row += "/><div class='ws_div02' region='center'><font class='ws_font'>";
									row += data[i][j].AGENTNAME;
									row += "</font></div>";
									row += "</div></center>"
								}
								row += "</td></tr>";
								$("#" + tid).append(row);
							}

						}, 'json');
	}
}