<%@page import="org.orphane.services.FetchContent"%>
<%@page import="org.orphane.services.GetAllDetails"%>
<%@page import="org.orphane.util.CKUtil"%>
<%@page import="org.orphane.model.RegularUsers"%>
<%@page import="org.orphane.model.Credential"%>
<%@page import="org.orphane.model.Events"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Credential user = CKUtil.fetchDetails(request);
	RegularUsers reg = FetchContent.getRegUserDetails(user.getEmail());
	int i = 1;
	List<Events> userEvent = null;
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else if (user != null) {
		userEvent = GetAllDetails.getEventOfUser(reg.getId());
	}
%>
<%
	if (userEvent == null || userEvent.isEmpty() || userEvent.size() == 0) {
%>
<div class="text-center" style="padding-top: 100px;">
	<h1>No Events added</h1>
	<div class="col text-center" style="padding-top: 60px;">
		<button onclick="loadEventJsp(<%=reg.getId()%>)"
			class="btn btn-success">Add Events</button>
	</div>
</div>
<%
	} else {
%>
<div class="container">
	<h1 class="text-center">EVENTS</h1>
	<div style="padding-top: 40px">
		<table class="table">
			<thead>
				<tr class="table-success">
					<th scope="col">S.No</th>
					<th scope="col">Orphanage Name</th>
					<th>EVENT NAME</th>
					<th>EVENT DATE</th>
					<th></th>
				</tr>
			</thead>
			<tbody id="setSelectedFilter">
				<%
					for (Events each : userEvent) {
				%>
				<tr class="table-secondary">
					<th scope="row"><%=i%></th>
					<td id="orpName"><%=each.getOrphanage().getName()%></td>
					<td id="orpEventName"><%=each.getEventName()%></td>
					<td id="eventDate"><%=each.getEventDate()%></td>
					<td onclick='deleteEvent(<%=each.getEventId()%>)'
						class="float-right"><button class="btn btn-danger">Cancel</button></td>
				</tr>
				<%
					i++;
						}
				%>
			</tbody>
		</table>
	</div>
</div>
<%
	}
%>