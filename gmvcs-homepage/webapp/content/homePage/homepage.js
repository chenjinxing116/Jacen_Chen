$(document).ready(function() {
	
	var language = (navigator.language || navigator.browserLanguage).toLowerCase();
	var imagePath = language.indexOf('zh') > -1 ? '/gmvcshomepage/content/homePage/images/zh/' : '/gmvcshomepage/content/homePage/images/other/';
	//var imagePath = '/gmvcshomepage/content/homePage/images/other/';
	
	$.i18n.properties({
		name:'gmvcs-language', 
	    path:'/gmvcshomepage/content/homePage/locales/', 
	    mode:'map',
	    language: 'zh',
	    callback: function() {
	    	document.title = $.i18n.prop('title');
	        $('#h1Caption').html($.i18n.prop('caption')).prev().attr('src', imagePath + 'jh.png');
	        $('#aLogout').append($.i18n.prop('logout')).children(':first').attr('src', imagePath + 'config.png');
	        $('#spanManage').html($.i18n.prop('manage'));
	        $('#spanApp').html($.i18n.prop('app'));
	        $('#bsConfig').append($.i18n.prop('config')).children(':first').attr('src', imagePath + 'apps.png');
	        $('#sysConfig').append($.i18n.prop('config')).children(':first').attr('src', imagePath + 'apps.png');;
	        $('#spanDL').html($.i18n.prop('download'));
	        $('#aMediaDL').html($.i18n.prop('mediaActiveDL'));
	        $('#aTalkDL').html($.i18n.prop('clusterTalkDL'));
	    }
	});
	
	$('#labelWelCome').next().attr('src', imagePath + 'user.png');
	
	// 从服务器获取配置数据
	var BUSINISE_CONFIG = [];
	var SYSTEM_CONFIG   = [];
	
	getUserInfo();
	
	getShowConfig(function(ret){
		if(ret === false){
			alert($.i18n.prop('getConfigFail'));
			return;
		}
		
		getHideConfig(function(ret){
			if(ret === false){
				alert($.i18n.prop('getConfigFail'));
				return;
			}
			
			initPage();
		});
	});
	
	function getUserInfo() {
		gmLibrary.ajaxGet('/gmvcshomepage/system/user/current.action', null, function(ret){
			if(ret.headers.ret === 0){
				$('#labelWelCome').text($.i18n.prop('welcome') + ', '+ ret.body.displayName);
			}
		});
	}
	
	function getShowConfig(callback){
		gmLibrary.ajaxGet('/gmvcshomepage/shortcut/set/list.action', {set:true}, function(ret){
			if(ret.headers.ret != 0){
				callback && callback(false);
				return;
			}
			
			ret.body.appShortcut.forEach(function(item, index){
				item.icon  = imagePath + item.icon;
				item.show  = true;
				item.table = index;
			});
			
			BUSINISE_CONFIG = BUSINISE_CONFIG.concat(ret.body.appShortcut);
			
			ret.body.mgrShortcut.forEach(function(item, index){
				item.icon  = imagePath + item.icon;
				item.show  = true;
				item.table = index;
			});
			
			SYSTEM_CONFIG = SYSTEM_CONFIG.concat(ret.body.mgrShortcut);
			
			callback && callback(true);
		});
	}
	
	function getHideConfig(callback){
		gmLibrary.ajaxGet('/gmvcshomepage/shortcut/set/list.action', {set:false}, function(ret){
			if(ret.headers.ret != 0){
				callback && callback(false);
				return;
			}
			
			ret.body.appShortcut.forEach(function(item, index){
				item.icon  = imagePath + item.icon;
				item.show  = false;
				item.table = index;
			});
			
			BUSINISE_CONFIG = BUSINISE_CONFIG.concat(ret.body.appShortcut);
			
			ret.body.mgrShortcut.forEach(function(item, index){
				item.icon  = imagePath + item.icon;
				item.show  = false;
				item.table = index;
			});
			
			SYSTEM_CONFIG = SYSTEM_CONFIG.concat(ret.body.mgrShortcut);
			
			callback && callback(true);
		});
	}
	
	function setConfig(id, del){
		gmLibrary.ajaxPost('/gmvcshomepage/shortcut/set/modify.action?itemId='+id+"&delete="+del, null, function(ret){
			
		});
	}
	
	var $bsUl = $('#bsUl');
	var $selectBS = $('#selectBS');
	var $sysUl = $('#sysUl');
	var $selectSYS = $('#selectSYS');
	function initPage(){
		/*应用配置*/
		var bushtml = "";
		var selbshtml = '';
		for(var i=0;i<BUSINISE_CONFIG.length; i++){
			if(!BUSINISE_CONFIG[i].show){
				selbshtml += '<option value=' + BUSINISE_CONFIG[i].type + '>' + BUSINISE_CONFIG[i].title + '</option>';
				continue;
			}
			
			bushtml += '<li><a target="_blank" href='+ BUSINISE_CONFIG[i].url +'><img src=' + BUSINISE_CONFIG[i].icon + ' title=' + BUSINISE_CONFIG[i].title + '></a></li>';
		}
		
		$bsUl.append(bushtml);
		$selectBS.append(selbshtml);
		
		/*系统配置*/
		var syshtml = "";
		var selsyshtml = '';
		for(var i=0;i<SYSTEM_CONFIG.length; i++){
			if(!SYSTEM_CONFIG[i].show){
				selsyshtml += '<option value=' + SYSTEM_CONFIG[i].type + '>' + SYSTEM_CONFIG[i].title + '</option>';
				continue;
			}
			
			syshtml += '<li><a target="_blank" href=' + SYSTEM_CONFIG[i].url + '><img src=' + SYSTEM_CONFIG[i].icon + ' title=' + SYSTEM_CONFIG[i].title + '></a></li>';
		}
		
		$sysUl.append(syshtml);
		$selectSYS.append(selsyshtml);
	}
	
	$('#bsAdd').click(function(){
		var $selopt = $("#selectBS option:selected");
		var seltext = $selopt.text();
		for(var i=0;i<BUSINISE_CONFIG.length; i++){
			if(BUSINISE_CONFIG[i].title == seltext){
				$bsUl.append('<li><a target="_blank" href='+ BUSINISE_CONFIG[i].url +'><img src=' + BUSINISE_CONFIG[i].icon + ' title=' + BUSINISE_CONFIG[i].title + '></a></li>');
				$selopt.remove();
				
				BUSINISE_CONFIG[i].show = true;
					
				setConfig(BUSINISE_CONFIG[i].id, false);
				break;
			}
		}
					
		var $opt = $("#selectBS option");
		if($opt.length == 0){
			$selectBS.text("");
		}
	});
	
	$('#bsConfig').click(function(){
		var $btn = $('#bsConfig');
		var $topele = $('.business');
		
		if($btn.hasClass('configing')){
			$btn.removeClass('configing');
			
			$bsUl.css('border', 'none');
			$('.tools',$topele).css('display', 'none');
			
			$(".dragglist").dragsort("destroy");
		}
		else{
			$btn.addClass('configing');
			
			$bsUl.css('border', '1px dashed white');
			$('.tools',$topele).css('display', 'block');
			
			$(".dragglist").dragsort({ 
				dragSelector: "li", 
				dragBetween: true, 
				dragEnd: saveOrder, 
				placeHolderTemplate: "<li class='placeHolder'><div></div></li>" 
			});
			
			function saveOrder() {
				var $this = $(this);
				
				var parent = $this.parent().attr('id');
				if(parent == 'bsUl'){
					return;
				}
				
				var title = $('img', $this).attr('title');
				for(var i=0;i<BUSINISE_CONFIG.length; i++){
					if(BUSINISE_CONFIG[i].title == title){
						$selectBS.append('<option value=' + BUSINISE_CONFIG[i].type + '>' + BUSINISE_CONFIG[i].title + '</option>');
						
						BUSINISE_CONFIG[i].show = false;
						setConfig(BUSINISE_CONFIG[i].id, true);
						break;
					}
				}
				
				$this.remove();
			};
		}
	});
	
	$('#sysAdd').click(function(){
		var $selopt = $("#selectSYS option:selected");
		var seltext = $selopt.text();
		for(var i=0;i<SYSTEM_CONFIG.length; i++){
			if(SYSTEM_CONFIG[i].title == seltext){
				$sysUl.append('<li><a target="_blank" href=' + SYSTEM_CONFIG[i].url + '><img src=' + SYSTEM_CONFIG[i].icon + ' title=' + SYSTEM_CONFIG[i].title + '></a></li>');
				$selopt.remove();
				
				SYSTEM_CONFIG[i].show = true;
				setConfig(SYSTEM_CONFIG[i].id, false);
				break;
			}
		}
					
		var $opt = $("#selectSYS option");
		if($opt.length == 0){
			$("#selectSYS").text("");
		}
	});
	
	$('#sysConfig').click(function(){
		var $btn = $('#sysConfig');
		var $topele = $('.system');
		
		if($btn.hasClass('configing')){
			$btn.removeClass('configing');
			
			$sysUl.css('border', 'none');
			$('.tools', $topele).css('display', 'none');
			
			$(".syslist").dragsort("destroy");
		}
		else{
			$btn.addClass('configing');
			
			$sysUl.css('border', '1px dashed white');
			$('.tools', $topele).css('display', 'block');
			
			$(".syslist").dragsort({ 
				dragSelector: "li", 
				dragBetween: true, 
				dragEnd: saveOrder, 
				placeHolderTemplate: "<li class='placeHolder'><div></div></li>" 
			});
			
			function saveOrder() {
				var $this = $(this);
				
				var parent = $this.parent().attr('id');
				if(parent == 'sysUl'){
					return;
				}
				
				var title = $('img', $this).attr('title');
				for(var i=0;i<SYSTEM_CONFIG.length; i++){
					if(SYSTEM_CONFIG[i].title == title){
						$selectSYS.append('<option value=' + SYSTEM_CONFIG[i].type + '>' + SYSTEM_CONFIG[i].title + '</option>');
						
						SYSTEM_CONFIG[i].show = false;
						setConfig(SYSTEM_CONFIG[i].id, true);
						break;
					}
				}
				
				$this.remove();
			};
		}
	});
	
	$bsUl.on('click', 'li', function(){
		
		var str = $bsUl.css('border-style');
		if(str != 'none' && str != ""){
			return false;
		}
		
		sessionStorage.clear();
		
		var $this = $(this);
		var title = $('img', $this).attr('title');
		for(var i=0;i<BUSINISE_CONFIG.length; i++){
			if(BUSINISE_CONFIG[i].title == title){
				var url = BUSINISE_CONFIG[i].type;
				var tabbed = url.substring(url.indexOf("/")+1, url.lastIndexOf("/"));
				
				//sessionStorage.setItem('tabbed', i);
				sessionStorage.setItem('tabbed', BUSINISE_CONFIG[i].table);
				sessionStorage.setItem('url', url);
				
				return true;
			}
		}
		
		return true;
	});
	
	$sysUl.on('click', 'li', function(){
		
		var str = $sysUl.css('border-style');
		if(str != 'none' && str != ""){
			return false;
		}
		
		sessionStorage.clear();
		
		var $this = $(this);
		var title = $('img', $this).attr('title');
		for(var i=0;i<SYSTEM_CONFIG.length; i++){
			if(SYSTEM_CONFIG[i].title == title){
				var url = SYSTEM_CONFIG[i].type;
				var tabbed = url.substring(url.indexOf("/")+1, url.lastIndexOf("/"));
				
				//sessionStorage.setItem('tabbed', i);
				sessionStorage.setItem('tabbed', SYSTEM_CONFIG[i].table);
				sessionStorage.setItem('url', url);
				
				return true;
			}
		}
		
		return true;
	});
});