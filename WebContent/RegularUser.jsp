<%@page import="org.orphane.modelenum.UserType"%>
<%@page import="org.orphane.modelenum.UserStatus"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.orphane.services.CredentialService"%>
<%@ page import="org.orphane.model.Credential"%>
<%@ page import="org.orphane.model.RegularUsers"%>
<%@ page import="org.orphane.util.CKUtil"%>
<%@ page import="org.orphane.services.FetchContent"%>
<%
	Credential user = CKUtil.fetchDetails(request);
	RegularUsers reg;
	String fname = "";
	String lname = "";
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else if (user != null) {
		if (user.getUserType().equals(UserType.REGULAR) == false || user.getUserType() == null) {
			response.sendRedirect("/orphanage.jsp");
		}
		reg = FetchContent.getRegUserDetails(user.getEmail());
		lname = reg.getLastName();
		fname = reg.getName();
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Regular User Profile">
<meta name="author" content="sabari kumar">

<title>User Page</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/user.css" rel="stylesheet">
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src="jscript/link1.js"></script>
<script type="text/javascript" src="jscript/link2.js"></script>
<script type="text/javascript" src="jscript/link3.js"></script>
<script type="text/javascript" src="jscript/link5.js"></script>
<script type="text/javascript" src="jscript/link6.js"></script>
<script type="text/javascript" src="jscript/link7.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
</head>
<body>


	<div class="container-fluid">
		<div class="row bg-dark" style="height: 90px;">
			<div class="col-12 col-sm-4 col-md-4 align-self-center">
				<a href="#"> <img class="profilephoto" src="images/sample.png"
					alt="Photo" /></a> <strong id="username" style="color: white"><%=fname + "  " + lname%></strong>
			</div>
			<div class="col-12 col-sm-8 col-md-8  align-self-center"
				style="color: white">
				<ul class="nav justify-content-end">
					<li class="nav-item"><a href="#" onclick="loadShowOrp()"
						class="nav-link active">Show Orphanage</a></li>
					<li class="nav-item"><a href="UserProfile.jsp"
						class="nav-link active">Profile</a></li>
					<li class="nav-item"><a class="nav-link" href="logout
					">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div id="home">
		<div id="shad1" class="container dotted shad" style="margin: 20px;">
			<div class="row">
				<div class="col">
					<strong style="font-family: sans-serif">Orphanges</strong>
				</div>
			</div>

			<div class="row" style="margin: 5px;">
				<div class="col">
					<a onclick="changePage()"><img src="images/add.JPG"
						alt="add Orphanges" style="width: 30px">Add Orphanages</a>
				</div>
			</div>

			<div class="row" style="margin: 5px;">
				<div class="col">
					<p style="color: #6b6b47; text-align: center;">Orphanges</p>
				</div>
			</div>
		</div>

		<div id="shad2" class="container dotted shad" style="margin: 20px;">
			<div class="row">
				<div class="col">
					<strong style="font-family: sans-serif">Events</strong>
				</div>
			</div>

			<div class="row" style="margin: 5px;">
				<div class="col">
					<a href="#"><img src="images/add.JPG" alt="add Orphanges"
						style="width: 30px">Add Events</a>
				</div>
			</div>

			<div class="row" style="margin: 5px;">
				<div class="col">
					<p style="color: #6b6b47; text-align: center;">Events</p>
				</div>
			</div>
		</div>

		<div id="shad3" class="container dotted shad" style="margin: 20px;">
			<div class="row">
				<div class="col">
					<strong style="font-family: sans-serif">Notifications</strong>
				</div>
			</div>

			<div class="row" style="margin: 20px;">
				<div class="col">
					<p style="color: #6b6b47; text-align: center;">Notifications.</p>
				</div>
			</div>
		</div>
	</div>

	<footer class="bg-dark">
		<p>
			Developed by <span style="color: #33ff33">Orphane Team</span>
		</p>
	</footer>

</body>
</html>