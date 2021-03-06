<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
	    <meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	    <meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=EDGE;chrome=1" />
	    <meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no,width=device-width,height=device-height">
		
		<title></title>
		
		<link rel="stylesheet" type="text/css" href="/front-resource/bootstrap-3.3.5/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="/front-resource/font-awesome/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/content/login/login.css"/>
	</head>
	<body>
		<div class="wrap">
			<div class="head">
				<div class="longin">
					<div class="title">
						<img class="iconLogo" src="${pageContext.request.contextPath}/content/login/images/icon-logo.png" />
						<h1 id="h1Caption"></h1>
					</div>
					<img class="leftPic" src="${pageContext.request.contextPath}/content/login/images/L-pic.png" />
					<div class="loginForm">
						<form method="post" action="j_security_check" autocomplete="off">
							<div class="filter"></div>
							
							<div class="formTitle">
								<img src="${pageContext.request.contextPath}/content/login/images/login.png" />
								<h3 id="h3UserLogin"></h3>
							</div>
							
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon">
										<span class="icon glyphicon glyphicon-user"></span>
									</span>
									<input id="inputUserName" name="j_username" type="text" class="form-control" placeholder="" />
								</div>

								<div id="inputGroundTip" class="input-group">
									<span id="span_placeholder" class="input-group-addon">
										<span class="icon glyphicon glyphicon-lock"></span>
									</span>
									<input id="input_placeholder" class="form-control" type="text" placeholder="" />
									
									<span id="span_passwrod" class="input-group-addon first-span hidden">
										<span class="icon glyphicon glyphicon-lock"></span>
									</span>
									<input id="input_passwrod" name="j_password" type="password" class="form-control hidden" placeholder="" />
								</div>

								<div class="btn_sub">
									<input id="btn_login" type="button" class="btn btn-success btn-lg col-sm-12" value="" />
								</div>
		
								<div class="warningNotice hidden">
									<p class="warning"></p>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="footer">
				<p id="pHost"></p>
			</div>
		</div>
		
		<script src="/front-resource/jquery/jquery-1.12.1.min.js"></script>
		<script src="/front-resource/jquery-i18n-properties/jquery.i18n.properties.min.js"></script>
		<script src="/front-resource/placeholder/jquery.placeholder.min.js"></script>
		<script src="${pageContext.request.contextPath}/content/login/md5.js"></script>
		<script src="${pageContext.request.contextPath}/content/login/login.js"></script>
	</body>
</html>
