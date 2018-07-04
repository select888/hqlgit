var screenDivId = "";//������ID
var selectedListDivWidth = 0;
var selectedListDivHeight = 0;
var _pp = 1000000000;//percent pixel
/*
function _clientWidth(){
	return document.documentElement.clientWidth-2;//��body�������߿�
}
function _clientHeight(){
	return document.documentElement.clientHeight;//����Ӧ�߶�-����߶�-�����߸�;
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

var alarmDiv = null;//LED�澯��ʾDIV
var alarmDivWidth = null;//LED�澯DIV���
var alarmDivLeft = 0;//LED�澯DIV��ʼ��߾�
var alarmContent = null;//LED�澯����λ��
var pageWidth = null;//ҳ����
var pageHeight = null;//ҳ��߶�
var alartInterval = null;//LEDѭ�����ŵĶ���
var alarmWindow = null;//�澯������
var alarmWindowKey = "";//�澯������KEY����ֹ��ͬ��Ϣ��ε���
var _top=0;//��Ļ�߶�
var _left=0;//��Ļ���
//��ʼ���澯
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
//ȡ�澯����
function getLedAlarm(){
	$.ajax( {
		url : './alarmAction.do?method=getLedAlarm',
		type : 'GET',
		dataType : 'json',
		timeout : 3000,
		error : function() {
		},
		success : function(data) {
			var results=data.result;//������
			if(results != "OK"){
			} else {
				setAlarmInfo(data.data);
			}
		},
		complete:function(v){}
	});
}
//���ø澯��Ϣ
function setAlarmInfo(dataList){
	var isExist = false;//�Ƿ���ڸ澯��¼
	var htmls = "���棺";//LED�澯����Ϣ
	var winHTML = "";//�澯��������Ϣ
	var newKey = "";//�澯������Ϣ��ΨһKEY
	$.each(dataList,function(i,s){
		htmls += (i+1)+"��"+s.msg+"&nbsp;&nbsp;";
		winHTML += (i+1)+"��"+s.msg+"<br/>";
		newKey += s.key;
		isExist = true;
	});
	if(isExist){
		//���ڸ澯��¼
		//LED�澯
		if(alartInterval==null){
			/*ie6 alartInterval = window.setInterval("alarmMove()", 10);*/
			alartInterval = "Chrome";
			alarmContent.html(htmls);
		}
		//�����澯���������ٴε�����
		if(alarmWindow==null){
			_top = _top-300-38;
			_left = _left-240-25;
			alarmWindowKey = newKey;
			alarmWindow = window.open('ums/manager/monitorareacfg/alarmRing/Alarming.html?msg='+encodeURI(winHTML),'����','width=240px,height=300px,top='+_top+'px,left='+_left+'px,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		} else if(newKey!=alarmWindowKey) {
			_top = _top-300-38;
			_left = _left-240-25;
			alarmWindowKey = newKey;
			alarmWindow = window.open('ums/manager/monitorareacfg/alarmRing/Alarming.html?msg='+encodeURI(winHTML),'����','width=240px,height=300px,top='+_top+'px,left='+_left+'px,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		}
	} else {
		//�����ڸ澯��¼��ֹͣLEDѭ������
		alarmContent.html("");
		if(alartInterval!=null){
			/*ie6 clearInterval(alartInterval);
			alarmDivLeft = 0;*/
			alartInterval = null;
		}
	}
}
//�رյ����alarmWindow=null���ٴε�����alarmWindow=����ֵ���ٵ��������Ǹ澯��Ϣ�ı�
function closeAlarmWindow(){
	alarmWindow = false;
}
/**
 * ��ʼ�����ҳ��
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
 * ��ʱ��ʾĳ����ط���
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
 * ֹͣ��ʱˢ�¼�ض���
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
 * չʾͼ��
 * @param obj ͼ�����ڵ�DIVID
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
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/Pie3D.swf?ChartNoDataText=���ݸ�ʽ���Ϸ������飡",obj+"Chart",ilength,iheight,"0","1");
		} else if(displayType=="3"){
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/MSColumn2D.swf?ChartNoDataText=���ݸ�ʽ���Ϸ������飡",obj+"Chart",ilength,iheight,"0","1");
		} else if(displayType=="4"){
			chart=new FusionCharts(obj,"./js/fusionCharts/chart/MSLine.swf?ChartNoDataText=���ݸ�ʽ���Ϸ������飡",obj+"Chart",ilength,iheight,"0","1");
		} else{
			alert("չʾ���Ͳ���ȷ");
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
 * ��ϯ������չ�ַ�ʽ
 * @param obj
 * @param frameNo
 * @param ilength
 * @param iheight
 */
function char_agent_map(obj, frameNo, ilength, iheight){
	var isExist = false;//�Ƿ���ڸ�ID��IFAME
	frameNo = frameNo.replace("agentMap", "");//��ʵ������ID
	$("#"+obj+" > *").each(function(){
		remove(this);
		if(this.id==("iframe"+obj+frameNo)){//����IFAME����ҳ��
			this.src = "./workMonitorAction.do?method=loadMonitorOne&workspaceNo="+frameNo;
			this.style.display = "block";
			isExist = true;
		}
	});
	if(!isExist){//�����ڣ�����IFRAME�����״δ�ʱ����
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
 * �б�չ�ַ�ʽ
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
 * ���չ�ַ�ʽ
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
 * ��קԤ��
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
//�Ƴ����󣬵����agentMap��Ԫ�أ�ֻ���ز��Ƴ���
function remove(thisObj){
	if(thisObj.id != "" && thisObj.id.indexOf("agentMap")!=-1){
		//AgentMap��ʽ��ֹͣ���ú�̨
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
    //������
    }
}
