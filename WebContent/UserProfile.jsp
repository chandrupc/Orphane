<%@page import="org.orphane.services.FetchContent"%>
<%@page import="org.orphane.model.RegularUsers"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.orphane.util.CKUtil"%>
<%@ page import="org.orphane.model.Credential"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Profile</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/user.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/profile.css">
<script type="text/javascript" src="main.js"></script>
</head>
<%
	String firstName = null;
	String lastName = null;
	String email = null;
	String dob = null;
	String phoneNumber = null;
	String altNum = null;
	String address = null;
	String city = null;
	String state = null;
	String zip = null;
	Credential user = CKUtil.fetchDetails(request);
	RegularUsers reg = null;
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else {
		reg = FetchContent.getRegUserDetails(user.getEmail());
		if (reg.getAltPhoneNumber() == null) {
			altNum = "";
			/*.out.println(reg.getName());
			System.out.println(reg.getLastName());
			System.out.println(reg.getAddress().address);
			System.out.println(reg.getAltPhoneNumber() + " " + altNum);
			System.out.println(reg.getPhoneNumber());*/
		} else {
			altNum = reg.getAltPhoneNumber().toString();
		}
		firstName = reg.getName();
		lastName = reg.getLastName();
		email = user.getEmail();
		dob = reg.getDate().toString();
		phoneNumber = reg.getPhoneNumber().toString();
		System.out.println(reg.getAddress().address);
		address = reg.getAddress().getAddress();
		System.out.println(address);
		city = reg.getAddress().getCity();
		state = reg.getAddress().getState();
		zip = reg.getAddress().getPincode().toString();
	}
%>
<body>
	<div class="container-fluid">
		<div class="row bg-dark" style="height: 90px">
			<div class="col-12 col-sm-4 col-md-4 align-self-center">
				<img id="prophoto" onclick="photoChoose()" class="profilephoto"
					src="images/sample.png" alt="Photo"> <strong
					style="color: white"><%=firstName + " " + lastName%></strong>
			</div>

			<div class="col-12 col-sm-8 col-md-8  align-self-center"
				style="color: white">
				<ul class="nav justify-content-end">
					<li class="nav-item">
						<button type="button" onclick="deleteAccount('<%=email%>')"
							class="btn btn-danger">Delete Account</button>
					</li>
					<li class="nav-item">
						<button type="button" data-toggle="modal"
							data-target="#exampleModal" data-whatever="@mdo">Change
							Password</button>
					</li>
					<li class="nav-item"><a class="nav-link" href="#"
						onclick="enable()">Edit Profile</a></li>
					<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>


	<div class="container" data-spy="scroll" data-target="userform">
		<form id="userform" action="" onsubmit="return validate()">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputFirstName">First Name</label> <input type="text"
						class="form-control" id="inputFirstName" value="<%=firstName%>"
						disabled="true">
				</div>

				<div class="form-group col-md-6">
					<label for="inputLastName">Last Name</label> <input type="text"
						class="form-control" id="inputLastName" value="<%=lastName%>"
						disabled="true">
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputEmail">Email</label> <input type="text"
						class="form-control" id="inputEmail" value="<%=email%>"
						disabled="true">
				</div>

				<div class="form-group col-md-6">
					<label for="inputdob">Date Of Birth</label> <input type="text"
						class="form-control" id="inputdob" value="<%=dob%>"
						disabled="true">
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="phonenumber">Phone Number</label> <input type="text"
						class="form-control" id="inputPhoneNumber"
						onfocus="clearError('number-error')"
						placeholder="Your phone number here" value="<%=phoneNumber%>"
						disabled="true"> <span id="number-error"></span>
				</div>

				<div class="form-group col-md-6">
					<label for="altphonenumber">Alternate Phone Number</label> <input
						type="text" class="form-control" id="inputAltPhoneNumber"
						onfocus="clearError('altnum-error')"
						placeholder="Your phone number here" value="<%=altNum%>"
						disabled="true"> <span id="altnum-error"></span>
				</div>
			</div>

			<div class="form-group">
				<label for="inputAddress">Address</label>
				<textarea class="form-control" id="inputAddress"
					onfocus="clearError('address-error')"
					onkeyup="addressLength('inputAddress','address-error')"
					placeholder="Address" disabled="true"><%=address%></textarea>
				<span id="address-error"></span>
			</div>

			<div class="form-row">
				<div class="form-group col col-md-4">
					<label for="inputCity">City</label> <input type="text"
						id="inputCity" onfocus="clearError('city-error')"
						onkeyup="cityLength('inputCity','city-error')" value="<%=city%>"
						placeholder="City" class="form-control" disabled="true"> <span
						id="city-error"></span>
				</div>

				<div class="form-group col col-md-4">
					<label for="inputState">State</label> <input type="text"
						id="inputState" onfocus="clearError('state-error')"
						onkeyup="stateLength('inputState','state-error')"
						onkeyup="stateLength('state','state-error')" value="<%=state%>"
						class="form-control" onkeyup="cityLength('city','city-error')"
						placeholder="State" disabled="true"> <span
						id="state-error"></span>
				</div>

				<div class="form-group col col-md-4">
					<label for="inputZip">Zip</label> <input type="text" id="inputZip"
						placeholder="zip" class="form-control"
						onfocus="clearError('zip-error')" value="<%=zip%>" disabled="true">
					<span id="zip-error"></span>
				</div>
			</div>
			<div>
				<button id="updateButton" type="submit" hidden="true">Update</button>
				<button id="cancelButton" type="button" hidden="true"
					onclick="disable()" style="padding: 10px">Cancel</button>
			</div>

		</form>

	</div>

	<footer class="bg-dark">
	<p>
		Developed by <span style="color: #33ff33">Orphane Team</span>
	</p>
	</footer>

	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Change Password</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<span id="pass-error"></span>
					<form onsubmit="return updatePassword()">
						<div class="form-group">
							<label for="currentPassword" class="col-form-label">Current
								Password:</label> <input type="password" class="form-control"
								id="currentPassword" onfocus="clearError('pass-error')">
						</div>
						<div class="form-group">
							<label for="newPassword" class="col-form-label">New
								Password:</label> <input type="password" class="form-control"
								id="newPassword" onfocus="clearError('pass-error')">
						</div>
						<div class="form-group">
							<label for="confirmPassword" class="col-form-label">Confirm
								Password:</label> <input type="password" class="form-control"
								id="confirmPassword" onfocus="clearError('pass-error')">
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal">Close</button>
							<button type="submit">Change Password</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<script src="jscript/jquery-3.2.1.slim.min.js"></script>
	<script src="jscript/popper.js"></script>
	<script type="text/javascript" src="jscript/bootstrap.min.js"></script>
</body>
</html>