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
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/content/uilibs/bootstrap-3.3.5/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/content/uilibs/font-awesome/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/content/login/login.css"/>
	</head>
	<body>
		<div class="wrap">
			<div class="head">
				<div class="longin">
					<div class="title">
						<img class="iconLogo" src="${pageContext.request.contextPath}/content/login/images/icon-logo.png" />
						<h1 data-gmlang='title'></h1>
					</div>
					<img class="leftPic" src="${pageContext.request.contextPath}/content/login/images/L-pic.png" />
					<div class="loginForm">
						<form method="post" action="j_security_check" autocomplete="off">
							<div class="filter"></div>
							
							<div class="formTitle">
								<img src="${pageContext.request.contextPath}/content/login/images/login.png" />
								<h3 data-gmlang="login-userlogin"></h3>
							</div>
							
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon">
										<span class="icon glyphicon glyphicon-user"></span>
									</span>
									<input name="j_username" type="text" class="form-control" data-gmlang-placeholder="placeholder-user" />
								</div>

								<div class="input-group">
									<span class="input-group-addon">
										<span class="icon glyphicon glyphicon-lock"></span>
									</span>
									<input name="j_password" type="password" class="form-control" data-gmlang-placeholder="placeholder-password" />
								</div>

								<div class="btn_sub">
									<button id="btn_login" class="btn btn-success btn-lg col-sm-12" data-gmlang="login-login"></button>
								</div>
		
								<div class="warningNotice hidden">
									<p class="warning" data-gmlang="login-warnmsg"></p>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="footer">
				<p data-gmlang="login-homepage"></p>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/content/uilibs/jquery/jquery-1.12.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/content/login/locales/gmvcs-language-en.js"></script>
		<script src="${pageContext.request.contextPath}/content/uilibs/placeholder/jquery.placeholder.min.js"></script>
		<script src="${pageContext.request.contextPath}/content/login/md5.js"></script>
		<script src="${pageContext.request.contextPath}/content/login/login.js"></script>
	</body>
</html>
