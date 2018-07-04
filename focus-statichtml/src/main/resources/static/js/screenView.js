var screenDivId = "";//画布的ID
var selectedListDivWidth = 0;
var selectedListDivHeight = 0;
var _pp = 1000000000;//percent pixel
/*
function _clientWidth(){
	return document.documentElement.clientWidth-2;//减body的两个边宽
}
function _clientHeight(){
	return document.documentElement.clientHeight;//自适应高度-标题高度-两个边高;
}*/
/*
function _parserWidthPercent(_pixel){
	if(_pixel.indexOf("px")!=-1){
		_pixel = _pixel.replace("px","");
	}
	return Math.floor(_pixel*_pp/selectedListDivWidth);
}
function _parserHeightPercent(_pixel){
	if(_pixel.indexOf("px")!=-1){
		_pixel = _pixel.replace("px","");
	}
	return Math.floor(_pixel*_pp/selectedListDivHeight);
}*/
function _getWidthPercent(_percent){
	return _percent*100/_pp+"%";
}
function _getHeightPercent(_percent){
	return _percent*100/_pp+"%";
}
function _getWidthPixel(_percent){
	return _percent.replace("px","");
}
function _getHeightPixel(_percent){
	return _percent.replace("px","");
}

var alarmDiv = null;//LED告警显示DIV
var alarmDivWidth = null;//LED告警DIV宽度
var alarmDivLeft = 0;//LED告警DIV起始左边距
var alarmContent = null;//LED告警内容位置
var pageWidth = null;//页面宽度
var pageHeight = null;//页面高度
var alartInterval = null;//LED循环播放的对象
var alarmWindow = null;//告警弹屏窗
var alarmWindowKey = "";//告警弹屏窗KEY，防止相同信息多次弹窗
var _top=0;//屏幕高度
var _left=0;//屏幕宽度
//初始化告警
function initAlarmView(){
	_top = window.screen.availHeight;
	_left = window.screen.availWidth;
	alarmDiv = $("div[id='alarmDiv']");
	//alarmDivWidth = alarmDiv.outerWidth();
	alarmDivWidth = document.getElementById("alarmDiv").offsetWidth;
	alarmContent = $("p[id='alarmContent']");
	pageWidth = _clientWidth();
	//alert(alarmDivWidth+"=="+pageWidth);
	window.setInterval("getLedAlarm()", 1000);
}
function alarmMove_ie6(){
	if(alarmDivWidth+alarmDivLeft+pageWidth<0){
		alarmDivLeft = 0;
	} else {
		alarmDivLeft = alarmDivLeft-1;
	}
	alarmDiv.css("left", alarmDivLeft);
}
//取告警内容
function getLedAlarm(){
	$.ajax( {
		url : './alarmAction.do?method=getLedAlarm',
		type : 'GET',
		dataType : 'json',
		timeout : 3000,
		error : function() {
		},
		success : function(data) {
			var results=data.result;//请求结果
			if(results != "OK"){
			} else {
				setAlarmInfo(data.data);
			}
		},
		complete:function(v){}
	});
}
//设置告警信息
function setAlarmInfo(dataList){
	var isExist = false;//是否存在告警记录
	var htmls = "警告：";//LED告警的信息
	var winHTML = "";//告警弹屏的信息
	var newKey = "";//告警弹屏信息的唯一KEY
	$.each(dataList,function(i,s){
		htmls += (i+1)+"、"+s.msg+"&nbsp;&nbsp;";
		winHTML += (i+1)+"、"+s.msg+"<br/>";
		newKey += s.key;
		isExist = true;
	});
	if(isExist){
		//存在告警记录
		//LED告警
		if(alartInterval==null){
			/*ie6 alartInterval = window.setInterval("alarmMove()", 10);*/
			alartInterval = "Chrome";
			alarmContent.html(htmls);
		}
		//弹屏告警，弹出后不再次弹出，
		if(alarmWindow==null){
			_top = _top-300-38;
			_left = _left-240-25;
			alarmWindowKey = newKey;
			alarmWindow = window.open('ums/manager/monitorareacfg/alarmRing/Alarming.html?msg='+encodeURI(winHTML),'警告','width=240px,height=300px,top='+_top+'px,left='+_left+'px,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		} else if(newKey!=alarmWindowKey) {
			_top = _top-300-38;
			_left = _left-240-25;
			alarmWindowKey = newKey;
			alarmWindow = window.open('ums/manager/monitorareacfg/alarmRing/Alarming.html?msg='+encodeURI(winHTML),'警告','width=240px,height=300px,top='+_top+'px,left='+_left+'px,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		}
	} else {
		//不存在告警记录，停止LED循环播放
		alarmContent.html("");
		if(alartInterval!=null){
			/*ie6 clearInterval(alartInterval);
			alarmDivLeft = 0;*/
			alartInterval = null;
		}
	}
}
//关闭弹框后，alarmWindow=null则再次弹窗，alarmWindow=其他值则不再弹窗，除非告警信息改变
function closeAlarmWindow(){
	alarmWindow = false;
}
/**
 * 初始化监控页面
 */
var intervalArray = null;
function initMonitorView(){
	var json = $("[name='jsonStr']:hidden").val();
	var jsonObject = eval('('+json+')');
	var length = jsonObject.length;
	if(jsonObject.length>0){
		var monitorschema = viewMonitorDiv(jsonObject, 0);
		var inter = "";
		var sno = 0;
		function showMonitor(){
			//$("div[id^='headingDiv']").each(function(){
			//	$(this).hide();
			//});
			$("div[id^='viewMonitorDiv']").each(function(){
				$(this).hide();
			});
			sno++;
			if(parseInt(sno)==parseInt(length)){
				sno = 0;
				//location.reload();
			}
			var monitorschemaInterval = viewMonitorDiv(jsonObject, sno);
			setTimeout(showMonitor, monitorschemaInterval.keepTime*1000);
		}
		
		setTimeout(showMonitor, monitorschema.keepTime*1000);
	}
}
	
/**
 * 定时显示某个监控方案
 * @param jsonObject
 * @param index
 * @returns
 */
function viewMonitorDiv(jsonObject, index){
	var monitorschema = jsonObject[index];
	var displayNo = monitorschema.displayNo;
	//var headingDivId = 'headingDiv'+displayNo;
	var viewMonitorDivId = 'viewMonitorDiv'+displayNo;
	var isHave = false;
	$("div[id^='viewMonitorDiv']").each(function(){
		if($(this).attr('id')==viewMonitorDivId){
			isHave = true;
		}
	});
	if(!isHave){
		var viewMonitorDiv = "<div id='"+viewMonitorDivId+"' style='width:100%;height:100%;'><div>";
		$("#"+screenDivId).append(viewMonitorDiv);
	}else{
		$("#"+viewMonitorDivId).show();
	}
	clearIntervalFrames(intervalArray);
	var frames = monitorschema.frames;
	intervalArray = new Array(frames.length);
	var intervalArrayIndex = 0;
	$.each(frames, function(i){
		var frameNo = frames[i].frameNo;
		var divId = 'selectedDiv'+displayNo+frameNo;
		if(!isHave){
			var selectedDiv = "<div id='"+divId+"' " +
			"style='position:absolute;" +
			"left:"+_getWidthPercent(frames[i].abscissa) +
			";top:"+_getHeightPercent(frames[i].ordinate) +
			";width:"+_getWidthPercent(frames[i].ilength) +
			";height:"+_getHeightPercent(frames[i].iheight) +
			";overflow:none;'></div>";
			$("#"+viewMonitorDivId).append(selectedDiv);
		}
		var displayType = frames[i].displayType;
		if(displayType=='1'){
			char_list_display(divId, displayNo, frameNo, frames[i].objectId, displayType, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight), "0");
		}else if(displayType=='5'){
			char_form_display(divId, displayNo, frameNo, frames[i].objectId, displayType, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight));
		}else if(displayType=="null" && frameNo.indexOf("agentMap")!= -1){
			char_agent_map(divId, frameNo, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight));
		} else{
			char_display(divId, frames[i].objectId, displayType, frameNo, "100%", "100%");
		}
		intervalArray[intervalArrayIndex] = setInterval(function(){
			if(displayType=='1'){
				char_list_display(divId, displayNo, frameNo, frames[i].objectId, displayType, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight), "1");
			}else if(displayType=='5'){
				char_form_display(divId, displayNo, frameNo, frames[i].objectId, displayType, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight));
			}else if(displayType=="null" && frameNo.indexOf("agentMap")!= -1){
				char_agent_map(divId, frameNo, _getWidthPercent(frames[i].ilength), _getHeightPercent(frames[i].iheight));
			}else{
				char_display(divId, frames[i].objectId, displayType, frameNo, "100%", "100%");
			}
		}, frames[i].refreshRate*1000);
		intervalArrayIndex++;
	});
	return monitorschema;
}

/**
 * 停止定时刷新监控对象
 */
function clearIntervalFrames(intervalArray){
	if(intervalArray!=null){
		$.each(intervalArray, function(index){
			clearInterval(intervalArray[index]);
		});
		intervalArray = null;
	}
}

/**
 * 展示图表
 * @param obj 图表所在的DIVID
 * @param objectId
 * @param displayType
 * @param frameNo
 * @param ilength
 * @param iheight
 */
function char_display(obj, objectId, displayType, frameNo, ilength, iheight){
	var url = "monitorAreaCfgAction.do?method=getMonitorFrameFusionXml&objectId="+objectId+"&frameNo="+frameNo+"&displayType="+displayType;
	var dataXml = "";
	$.ajax({
		type: "post",
		url: url,
		async: false,
		success: function(data){
			dataXml = data;
			delete data;data = null;
		}
	});
	var chart = "";
	if($("#"+obj+"Chart").length>0){
		updateChartXML(obj+"Chart", dataXml);
	} else {
		if(displayType=="2"){
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/Pie3D.swf?ChartNoDataText=数据格式不合法，请检查！",obj+"Chart",ilength,iheight,"0","1");
		} else if(displayType=="3"){
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/MSColumn2D.swf?ChartNoDataText=数据格式不合法，请检查！",obj+"Chart",ilength,iheight,"0","1");
		} else if(displayType=="4"){
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/MSLine.swf?ChartNoDataText=数据格式不合法，请检查！",obj+"Chart",ilength,iheight,"0","1");
		} else{
			alert("展示类型不正确");
		}
		chart.addParam("wmode", "transparent");
		chart.setDataXML(dataXml);
		chart.render(obj);
	}
	delete dataXml;dataXml = null;
	delete chart;chart = null;
	//$("#"+obj+" > *").each(function(){remove(this);});
}
/**
 * 坐席工作区展现方式
 * @param obj
 * @param frameNo
 * @param ilength
 * @param iheight
 */
function char_agent_map(obj, frameNo, ilength, iheight){
	var isExist = false;//是否存在该ID的IFAME
	frameNo = frameNo.replace("agentMap", "");//真实工作区ID
	$("#"+obj+" > *").each(function(){
		remove(this);
		if(this.id==("iframe"+obj+frameNo)){//存在IFAME，打开页面
			this.src = "./workMonitorAction.do?method=loadMonitorOne&workspaceNo="+frameNo;
			this.style.display = "block";
			isExist = true;
		}
	});
	if(!isExist){//不存在，新增IFRAME。仅首次打开时创建
		var iframe = document.createElement("iframe");
		iframe.id="iframe"+obj+frameNo;
		iframe.width = ilength+"px";
		iframe.height = iheight+"px";
		iframe.setAttribute("frameborder", "0", 0);  
		iframe.src = "./workMonitorAction.do?method=loadMonitorOne&workspaceNo="+frameNo;
		$("#"+obj).append(iframe);
		//var _obj = document.getElementById(obj);
		//_obj.innerHTML = "";
		//_obj.innerHTML = iframe;
		//delete iframe; iframe = null;
	}
}
/**
 * 列表展现方式
 * @param obj
 * @param displayNo
 * @param frameNo
 * @param objectId
 * @param displayType
 * @param ilength
 * @param iheight
 * @param requestFlag
 */
function char_list_display(obj, displayNo, frameNo, objectId, displayType, ilength, iheight, requestFlag){
	var url = "monitorAreaCfgAction.do?method=getMonitorFrameFusionXml&displayNo="+displayNo+"&frameNo="+frameNo+"&objectId="+objectId+"&displayType="+displayType+"&requestFlag="+requestFlag;
	$.ajax({
		type: "post",
		url: url,
		async: false,
		success: function(data){
			var _obj = document.getElementById(obj);
			_obj.innerHTML = "";
			_obj.innerHTML = data;
			delete data;data = null;
			delete _obj; _obj = null;
			//delete data; data = null;
			//$("#"+obj+" > *").each(function(){var s = $(this).remove();delete s;s=null;});
			//$("#"+obj).empty();
			//$("#"+obj).append(data);
			drawPie();
			//$("#"+obj+" > *").each(function(){alert(this.innerHTML);});
		}
	});
}

/**
 * 表格展现方式
 * @param obj
 * @param displayNo
 * @param frameNo
 * @param objectId
 * @param displayType
 * @param ilength
 * @param iheight
 */
function char_form_display(obj, displayNo, frameNo, objectId, displayType, ilength, iheight){
	//var displayNo = $("[name='displayNo']").val();
	var url = "monitorAreaCfgAction.do?method=getMonitorFrameFusionXml&displayNo="+displayNo+"&frameNo="+frameNo+"&objectId="+objectId+"&displayType="+displayType;
	//var dataXml = "";
	$.ajax({
		type: "post",
		url: url,
		async: false,
		success: function(data){
			var _obj = document.getElementById(obj);
			_obj.innerHTML = "";
			_obj.innerHTML = data;
			delete data; data = null;
			//$("#"+obj).empty();
			//$("#"+obj).append(data);
			//delete data;data = null;
			drawPie();
		}
	});
	//$("#"+obj+" > *").each(function(){remove(this);});
	//$("#"+obj).empty(); 
	//$("#"+obj).append(dataXml);
	//drawPie();
	//$("#"+obj).css({
	//	  "padding":"0px"
	//});

}
/**
 * 拖拽预览
 * @param frameNo
 * @param objectId
 * @param displayType
 */
function do_view(frameNo, objectId, displayType){
	var displayNo = $("[name='displayNo']").val();
	var url = "monitorAreaCfgAction.do?method=getMonitorFrameFusionXmlView&frameNo="+frameNo+"&objectId="+objectId+"&displayType="+displayType;
	var dataXml = "";
	$.ajax({
		type: "post",
		url: url,
		async: false,
		success: function(data){
			dataXml = data;
		}
	});
	return dataXml;
}
//移除对象，但针对agentMap的元素，只隐藏不移除。
function remove(thisObj){
	if(thisObj.id != "" && thisObj.id.indexOf("agentMap")!=-1){
		//AgentMap格式，停止调用后台
		thisObj.src = "about:blank";
		thisObj.style.display = "none";
	} else {
		$(thisObj).remove();
	}
}
function drawPie(){
	try{
		if (window.PIE) {
	        $('.cls_pie').each(function() {
	            PIE.attach(this);
	        });
	    }
    }catch(e){
    //不处理
    }
}
