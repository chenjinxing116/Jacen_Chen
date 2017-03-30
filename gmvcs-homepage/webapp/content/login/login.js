$(function(){
	// 多语言
	$.i18n.properties({
	    name:'gmvcs-language', 
	    path:'/gmvcshomepage/content/login/locales/', 
	    mode:'map',
	    language: 'zh',
	    callback: function() {
	        document.title = $.i18n.prop('title');
	        $('#h1Caption').html($.i18n.prop('caption'));
	        $('#h3UserLogin').html($.i18n.prop('userLogin'));
	        $('#inputUserName').attr('placeholder', $.i18n.prop('inputUser'));
	        $('#input_passwrod').attr('placeholder', $.i18n.prop('inputPWD'));
	        $('#input_placeholder').attr('placeholder', $.i18n.prop('inputPWD'));
	        $('#btn_login').val($.i18n.prop('login'));
	        $('.warning').html($.i18n.prop('loginError'));
	        $('#pHost').html($.i18n.prop('host'));
	    }
	});
	
	var isOperaMini = Object.prototype.toString.call(window.operamini) === '[object OperaMini]';
    var isInputSupported = 'placeholder' in document.createElement('input') && !isOperaMini;
    var isTextareaSupported = 'placeholder' in document.createElement('textarea') && !isOperaMini;
	
	if(!isInputSupported || !isTextareaSupported){
		$('input:not([type=password])').placeholder();
		
		$('#input_placeholder').focus(function(){
			$('#span_placeholder').addClass('hidden');
			$('#input_placeholder').addClass('hidden');
			$('#span_passwrod').removeClass('hidden');
			$('#input_passwrod').removeClass('hidden').focus();
		});
		
		$('input[type=password]').blur(function(){
			if(this.value.length === 0){
				$('#span_placeholder').removeClass('hidden');
				$('#input_placeholder').removeClass('hidden');
				$('#span_passwrod').addClass('hidden');
				$('#input_passwrod').addClass('hidden');
			}
		});
	}
	else{
		$('#span_placeholder').addClass('hidden');
		$('#input_placeholder').addClass('hidden');
		$('#span_passwrod').removeClass('hidden');
		$('#input_passwrod').removeClass('hidden');
	}

	window.onresize = function(){
		$('.footer > p').css('line-height', $('.footer').height()+'px');
	};

	$('.footer > p').css('line-height', $('.footer').height()+'px');
	
	$("form input").keypress(function(event) {
		if(event.keyCode == 13) {
			onlogin();
			return;
		}
		
		$('.warningNotice').addClass('hidden');
		return;
	});
	
	$('#btn_login').click(function(evt) {
		onlogin(evt);
	});
});

function onlogin(evt)
{
	evt && evt.preventDefault();
	
    var userName = $("input[name='j_username']").val();
    var password = $("input[name='j_password']").val();
    
    if(userName.length==0 || password.length==0){
		
		if(userName.length==0){
			$("input[name='j_username']").focus();
			return;
		}
		
		if(password.length==0){
			document.getElementById('input_pwd').focus();
			return;
		}

		return;
    }
    
    var jsonStr = "";
	var url     = "/gmvcshomepage/system/check.do";
		
	var pdata = {
		"userName" : userName,
		"password" : hex_md5(password)
	};
	
	//$('#btn_login').append('<i class="fa fa-spinner fa-spin"></i>');
	$('#btn_login').attr('disabled');

	$.ajax({
		url : url,
		type : 'post',
		contentType : "application/json",
		data : JSON.stringify(pdata),
		async : true,
		cache : true,
		success : function(result) {
			var code = result.code;
			var msg = result.msg;
			if (code != 200) {
				$('.warningNotice').removeClass('hidden');
				$('#input_pwd').focus();
				$('#btn_login > i').remove();
				$('#btn_login').removeAttr('disabled');	
				return;
			}

			//如果有需要保存密码，可以引入jquery.cookie.js保存cookie
			//if(that.bSavePassword){
			//	$.cookie(userName , password , { expires: 7 });
			//}
			
			$("input[name='j_password']").val(hex_md5(password));
			$("form").submit(); //验证成功，直接提交表单，会自动跳转到index.jsp
			return;
		},
		error : function(result) {
			//$('#btn_login > i').remove();
			$('#btn_login').removeAttr('disabled');
		}
	});
}