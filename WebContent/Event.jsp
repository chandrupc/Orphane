<%@page import="org.orphane.util.HBUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.orphane.model.RegularUsers"%>
<%@page import="org.orphane.services.FetchContent"%>
<%@page import="org.orphane.model.Orphanage"%>
<%@page import="org.orphane.util.CKUtil"%>
<%@page import="org.orphane.model.Credential"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Credential user = CKUtil.fetchDetails(request);
	RegularUsers reg = null;
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else {
		String mail = user.getEmail();
		reg = FetchContent.getRegUserDetails(mail);
		//System.out.println(reg);
	}
%>
<div class="container">

	<div class="text-center ">
		<h1 class="heading">ADD EVENTS</h1>
	</div>
	<br> <br>
	<div class="col-lg-12 well top">

		<form onsubmit="return validateEventDetails('<%=user.getEmail()%>')">
			<div class="row">
				<div class="col-4">
					<label>EVENT DATE</label>
				</div>
				<div class="col-5">
					<input onfocus="clearError('eventDateError')" id="eventDate"
						type="date" class="form-control"> <br> <span
						id="eventDateError"></span> <br>
				</div>
			</div>



			<div class="row">
				<div class="col-4">
					<label>EVENT</label>
				</div>
				<div class="col-5">
					<div class="dropdown">
						<button class="btn btn-outline-dark dropdown-toggle" type="button"
							id="eventName" onfocus="clearError('eventNameError')"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--Select--</button>
						<div class="dropdown-menu" aria-labelledby="eventName">
							<a onclick="setEvent(this)" value="BREAKFAST" id="option1"
								title="From 7 AM - 10 AM" class="dropdown-item" href="#">BREAKFAST</a>
							<a onclick="setEvent(this)" value="LUNCH" id="option2"
								title="From 12 NOON -2 PM" class="dropdown-item" href="#">LUNCH</a>
							<a onclick="setEvent(this)" value="DINNER" id="option3"
								title="From 6 PM - 8 PM" class="dropdown-item" href="#">DINNER</a>
						</div>
						<span id="eventNameError"></span>
					</div>
				</div>
				<br> <br> <br> <br>
			</div>

			<div class="row">
				<div class="col-4">
					<label>Orphanage Name</label>
				</div>
				<div class="col-5">
					<input onkeyup="showOrpNames()"
						onfocus="clearError('orpNameError')" id="orpName" type="text"
						placeholder="Orphanage name here" class="form-control"
						autocomplete="off"> <span id="orpNameError"></span>
					<div id="suggest"></div>
				</div>
			</div>

			<div class="form-group top">
				<br> <br> <label>EVENT DESCRIPTION</label> <br> <br>
				<textarea id="eventDescription"
					onfocus="clearError('descriptionError')"
					onkeyup="addressLength('eventDescription','descriptionError')"
					placeholder="*What's this event for (eg. Your birthday)" rows="3"
					class="form-control" style="resize: none"></textarea>
				<span id="descriptionError"></span>
			</div>

			<div class="text-center"
				style="padding-top: 50px; padding-bottom: 80px;">
				<button class="btn btn-lg btn-success" type="submit">Add
					Event</button>
			</div>
		</form>
	</div>
</div>