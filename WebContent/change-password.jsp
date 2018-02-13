<%@page import="org.orphane.services.CredentialService"%>
<%@page import="org.orphane.model.Credential"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String mail = request.getParameter("mail");
	String authKey = request.getParameter("authkey");
	Credential user = CredentialService.getUser(mail);
	if (user != null) {
		if (user.getEmail().equals(mail) == false || user.getAuthKey().equals(authKey) == false) {
			request.getRequestDispatcher("forgotpass.html").forward(request, response);
		}
	}
%>
<!DOCTYPE HTML>
<html>

<head>
<title>Change Password</title>
<link href="css/forgotpass.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Reset Password Form Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<!--google fonts
    <link href='//fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>-->
<script type="text/javascript" src="main.js"></script>
</head>

<body>
	<!--element start here-->
	<div class="elelment">
		<h2>Change Password Form</h2>
		<div class="element-main">
			<h1>Forgot Password</h1>
			<br> <br>
			<form onload="changePassDisable()"
				onsubmit="return checkNewPassword()">
				<input type="email" name="mail" id="mailId" value=<%=mail%>
					disabled="disabled"> <label for="newPassword1">New
					Password</label><input type="password" name="pass1" id="newPassword1"
					placeholder="Your password here"><label for="newPassword2">Retype
					New Password</label> <input type="password" name="pass2" id="newPassword2"
					placeholder="Your password here"> <input type="submit"
					value="Change Password">
			</form>
		</div>
	</div>
	<div class="copy-right">
		<!--<p>© 2016 Reset Password Form. All rights reserved | Template by <a href="http://w3layouts.com/" target="_blank">  W3layouts </a></p>-->
	</div>

	<!--element end here-->
</body>

</html>