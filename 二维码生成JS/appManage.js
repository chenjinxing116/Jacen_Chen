$(function() {
	getData(); //初始化表格

	$('#upLoadApk').on('click', uploadAPKDialog);

	//	$('#pagePrev').on('click', pagePrev);
	//	$('#pageNext').on('click', pageNext);
	//
	$('.table').on('click-row.bs.table', function(e, row, $element) {
		$element.addClass('success').siblings().removeClass('success');
	});

	$('.table').on('dbl-click-row.bs.table', function(e, row, $element) {
		//alterDeviceInfo();
	});

});
//格式化序号
function runningFormatter(value, row, index) {
	return index + 1;
}

function sizeFormatter(value, row, index) {
	return value + 'M';
}

function fileName(value, row, index) {
	return value;
}

function uploadTime(value, row, index) {
	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
}

function operator(value, row, index) {
	var html = '';
	html += '<span style="color:#12AC3C;"><a href="javascript:void(0)" onclick="onDelete(\'' + row.id + '\')">删除</a></span>';
	html += '<span>   <span>'
	html += '<span style="color:#12AC3C;"><a href="javascript:void(0)" onclick="downloadApp(\'' + row.appPath + '\')">下载</a></span>';
	return html;
}
//初始化表格
function initTable(data) {
	//先销毁表格
	$('.table').bootstrapTable('destroy');

	$('.table').bootstrapTable({
		data: data,
		pagination: false,
		height: '600px'
	});
}
//5.6.2	apk信息列表 
function getData() {
	$.ajax({
		url: "/csMgrSys/version/list_apk.action",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
		async: true,
		cache: false,
		error: function() {
			return;
		},

		success: function(ret) {
			if(ret.headers.ret != 0) {
				alert('异常！');
				return;
			}
			if(ret.headers.ret == 0) {
				var body = ret.body;
				initTable(body);
			}
		}
	});
}

function onDelete(id) {
	if(confirm("你确定删除吗？")) {
		deleteInfo(id);
	} else {}
}
//5.6.3	apk上传信息删除 
function deleteInfo(id) {
	var params = {
		'id': id,
	}
	$.ajax({
		url: "/csMgrSys/version/del_apk.action",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
		data: params,
		async: true,
		cache: false,
		error: function() {
			return;
		},

		success: function(ret) {
			if(ret.headers.ret != 0) {
				alert('异常！');
				return;
			}
			if(ret.headers.ret == 0) {
				getData();
			}
		}
	});
}

//上传APK 弹窗
function uploadAPKDialog() {
	var editHTML = $('#dialog').clone(true);
	//	editHTML.find("#confirm").on('click', uploadAPK);
	editHTML.find("#close").on('click', cancelDialog);
	editHTML.find("#appversion").attr('id', 'appversions');
	editHTML.find("#describe").attr('id', "describes");
	editHTML.find("#picker").attr('id', "pick");
	editHTML.find("#confirm").attr('class', "btn_1 sure");
	editHTML.find("#thelist").attr('id', "thelists");
	$.gmModelDialog({
		title: "上传APK",
		dragEnabled: true,
		htmlContent: editHTML,
		width: 358,
		height: 220
	});

	editHTML.show();

	apkUpload();
}

//5.6.1	 apk上传
//function uploadAPK() {
//	var apkVersion = '';
//	var desc = '';
//	var Apk = '';
//
//	var params = {
//		'apkVersion': apkVersion,
//		'desc': desc,
//		'Apk': Apk
//	};
//	$.ajax({
//		url: "/csMgrSys/version/up_apk.action",
//		type: "post",
//		contentType: "application/json",
//		dataType: 'json',
//		data: JSON.stringify(params),
//		async: true,
//		cache: false,
//		error: function() {
//			return;
//		},
//
//		success: function(ret) {
//			if(ret.headers.ret != 0) {
//				alert('异常！');
//				return;
//			}
//			if(ret.headers.ret == 0) {
//				alert('上传成功');
//				cancelDialog();
//			}
//		}
//	});
//}

//关闭弹出窗口
function cancelDialog() {
	$.gmModelDialog("destory");
}

function apkUpload() {
	var uploader = WebUploader.create({
		swf: '/front-resource/webuploader-0.1.5/Uploader.swf',
		server: '/csMgrSys/version/up_apk.action',
		pick: '#pick',
		resize: false
	});
	uploader.on('fileQueued', function(file) {
		$("#thelists").val(file.name);
//				$('#thelists').append('<span class="info" "data-toggle="tooltip" data-placement="top" title="' + file.name + '" style="cursor:pointer" >' + file.name +' </span>');
//		$("#thelists").append('<div id="' + file.id + '" class="fileitem"></div>');
	});
	uploader.on('uploadComplete', function() {
		cancelDialog();
		getData();
	});
	$(".sure").on('click', function() {
		var parm = {
			'appVersion': $('#appversions').val(),
			'desc': $('#describes').val()
		};
		
		var thelists = $('#thelists').val().length;
		
		if(!thelists) {
			alert('请选择文件！');
		} else {
			$.ajax({
				type: "post",
				url: "/csMgrSys/version/up_apk_param.action",
				async: true,
				data: JSON.stringify(parm),
				contentType: 'application/json',
				dataType: 'json',
				success: function(Data) {
					if(Data.headers.ret == 0) {
						uploader.options.formData.appId = Data.body.appId;
						uploader.upload();
					} else if(Data.headers.ret == 4) {
						alert("版本号冲突！");
					} else {
						alert("上传失败！");
						deleteInfo(Data.body.appId);
					}

				},
				error: function(Data) {
					alert("请检查版本号！");
				}
			});
		}
	});
}

function downloadApp(value, row, index) {
	var html = '<div><div id="div_ewm" class="div_ewm"></div></div>';
	html += '<button type="button" class="btn_2 dialog-close to-fix" id="close">关闭</button>';

	$.gmModelDialog({
		title: "下载",
		dragEnabled: true,
		htmlContent: html,
		width: 358,
		height: 250
	});

	$(html).find("#close").on('click', cancelDialog);
	try {
		$('#div_ewm').each(function() {
			$(this).qcode({
				text: value,
				width: 150,
				height: 150
			});
		});
	} catch(e) {
		$('#div_ewm').html('<a href="' + value + '">[下载到本地]</a>');
	}
}

//提示框、
function tipsWindow(str) {
	var html = '<div><div id="div_ewm" class="div_ewm">'+str+'</div></div>';
	html += '<button type="button" class="btn_1 dialog-close to-fix" id="close">确定</button>';

	$.gmModelDialog({
		title: "提示",
		dragEnabled: true,
		htmlContent: html,
		width: 358,
		height: 140
	});
	$(html).find("#close").on('click', cancelDialog);
}
//关闭弹出窗口
function cancelDialog() {
	$.gmModelDialog("destory");
}