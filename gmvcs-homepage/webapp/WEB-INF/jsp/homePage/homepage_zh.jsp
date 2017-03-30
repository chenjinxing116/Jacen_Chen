<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" >
		<meta name="renderer" content="webkit" >
	    <meta http-equiv="X-UA-Compatible" content="IE=EDGE;chrome=1" >
	    <meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no,width=device-width,height=device-height" >
		
		<title>视频一体化指挥平台-首页</title>
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/content/homePage/homepage.css"/>
	</head>
	<body>	
		<div class="wrapper">
			<div class="header">
				<img src="${pageContext.request.contextPath}/content/homePage/images/jh.png">
				<h1>视频一体化指挥平台</h1>
				
				<div class="loginfo">
					<label id="labelWelCome">欢迎您</label>
					<img src='${pageContext.request.contextPath}/content/homePage/images/user.png'>
					<div class="userTip">
						<a href="/gmvcshomepage/system/logout.action">
							<img src="${pageContext.request.contextPath}/content/homePage/images/config.png"/> 退出系统
						</a>
					</div>
				</div>
			</div>
			<div class="content">
				<div class="business">
					<div class="bsheader">
						<span>应用</span>
						<a id="bsConfig"><img src="${pageContext.request.contextPath}/content/homePage/images/apps.png"> 配置</a>
					</div>
					
					<ul id="bsUl" class="dragglist">
					</ul>
					<div class="tools">
						<div class="delete">
							<ul class="dragglist">
							</ul>
						</div>
						
						<div class="add">
							<select id="selectBS">
							</select>
							<a href="javascript:void(0);" id="bsAdd">
							</a>
						</div>
					</div>
				</div>
				<div class="system">
					<div class="sysheader">
						<span>管理</span>
						<a id="sysConfig"><img src="${pageContext.request.contextPath}/content/homePage/images/apps.png"> 配置</a>
					</div>
					
					<ul id="sysUl" class="syslist">
					</ul>
					<div class="tools">
						<div class="delete">
							<ul class="syslist">
							</ul>
						</div>
						
						<div class="add">
							<select id="selectSYS">
							</select>
							<a href="javascript:void(0);" id="sysAdd">
							</a>
						</div>
					</div>
				</div>
				<div class="right">
					<%-- <div class="notice">
						<div class="ntheader">
							<span>通知公告</span>
							<a href="#">更多>></a>
						</div>
						
						<div class="msg">
							<div class="shade"></div>
							<ul>
								<li><a href="#">欢迎使用一体化指挥平台</a></li>
							</ul>
						</div>
					</div>--%>
					
					<div class="">
						<div class="ntheader">
							<span>下载中心</span>
							<!--<a href="#">更多>></a>-->
						</div>
						
						<div class="msg">
							<div class="shade"></div>
							<ul>
								<li><a href="/front-resource/GSVideoOcxSetup.exe">视频播放器插件下载</a></li>
								<li><a href="/front-resource/TonmxIntercomOCX.exe">集群对讲插件下载</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="footer">
				<!--<p>公司主页: http://www.goldmsg.com | 联系电话: 020-2839 8008 | 国迈科技 版权所有</p>-->
			</div>
		</div>
		
		<!--[if lte IE 8]>
			<script src="/front-resource/es5shim/es5-shim.min.js"></script>
		<![endif]-->
		
		<script src="/front-resource/jquery/jquery-1.12.1.min.js"></script>
		<script src="/front-resource/jquery.dragsort/jquery.dragsort-0.5.2.min.js"></script>
		<script src="/front-resource/gmTools/common/js/gmLibrary.js"></script>
		<script src="${pageContext.request.contextPath}/content/homePage/config.js"></script>
		<script src="${pageContext.request.contextPath}/content/homePage/homepage.js"></script>
	</body>
</html>

