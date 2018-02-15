<%@page import="org.orphane.modelenum.UserType"%>
<%@page import="org.orphane.services.FetchContent"%>
<%@page import="org.orphane.model.Orphanage"%>
<%@page import="org.orphane.model.Credential"%>
<%@page import="org.orphane.util.CKUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Orphanage Profile</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/orphanage.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/Orpprofile.css">
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src="jscript/link1.js"></script>
<script type="text/javascript" src="jscript/link2.js"></script>
<script type="text/javascript" src="jscript/link3.js"></script>
<script type="text/javascript" src="jscript/link5.js"></script>
<script type="text/javascript" src="jscript/link6.js"></script>
<script type="text/javascript" src="jscript/link7.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

</head>
<%
	Credential user = CKUtil.fetchDetails(request);
	Orphanage orp = null;
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else {
		if (user.getUserType().equals(UserType.REGULAR)) {
			System.out.println("Success");
			request.getRequestDispatcher("RegularUser.jsp").forward(request, response);
		} else {
			orp = FetchContent.getOrphanageDetails(user.getEmail());
			System.out.println(orp);
		}
	}
%>
<body>
	<div class="container-fluid">
		<div class="row bg-dark" style="height: 90px">
			<div class="col-12 col-sm-4 col-md-4 align-self-center">
				<a href="#"><img class="profilephoto" src="images/sample.png"
					alt="Photo"></a> <strong style="color: white"><%=orp.getName()%></strong>
			</div>
			<div class="col-12 col-sm-8 col-md-8  align-self-center"
				style="color: white">
				<ul class="nav justify-content-end">
					<!-- <li class="nav-item"><a class="nav-link active"
						href="Trustee.html">Trustee</a></li> -->
					<li class="nav-item"><a class="nav-link active"
						href="OrphanageProfile.jsp">Profile</a></li>
					<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container form-group">
		<div id="heading">POST YOUR NEEDS</div>
		<textarea class="form-control" id="exampleFormControlTextarea1"
			rows="5"></textarea>
		<div class="center">
			<button type="button" class="btn btn-success btn-lg">Success</button>
		</div>
	</div>

	<!--<div id="shad1" class="container dotted shad" style="margin: 20px;">
        <div class="row">
            <div class="col">
                <strong style="font-family:  sans-serif">Orphanges</strong>
            </div>
        </div>

        <div class="row" style="margin: 5px;">
            <div class="col">
                <a href="#"><img src="icons/add.jpg" alt="add Orphanges" style="width: 30px">Add Orphanages</a>
            </div>
        </div>

        <div class="row" style="margin: 5px;">
            <div class="col">
                <p style="color:#6b6b47;text-align: center;">No Orphanages added yet.</p>
            </div>
        </div>
    </div>

    <div id="shad2" class="container dotted shad" style="margin: 20px;">
        <div class="row">
            <div class="col">
                <strong style="font-family:  sans-serif">Events</strong>
            </div>
        </div>

        <div class="row" style="margin: 5px;">
            <div class="col">
                <a href="#"><img src="icons/add.jpg" alt="add Orphanges" style="width: 30px">Add Events</a>
            </div>
        </div>

        <div class="row" style="margin: 5px;">
            <div class="col">
                <p style="color:#6b6b47;text-align: center;">No Events added yet.</p>
            </div>
        </div>
    </div>

    <div id="shad3" class="container dotted shad" style="margin: 20px;">
        <div class="row">
            <div class="col">
                <strong style="font-family:  sans-serif">Notifications</strong>
            </div>
        </div>

        <div class="row" style="margin: 20px;">
            <div class="col">
                <p style="color:#6b6b47;text-align: center;">No Notifications.</p>
            </div>
        </div>
    </div>-->

	<footer class="bg-dark">
		<p>
			Developed by <span style="color: #33ff33">Orphane Team</span>
		</p>
	</footer>

</body>

</html>