
<%
	// show orphanages page
%>


<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="org.orphane.services.GetAllDetails"%>
<%@page import="org.orphane.modelenum.UserType"%>
<%@page import="org.orphane.modelenum.UserStatus"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.orphane.services.CredentialService"%>
<%@ page import="org.orphane.model.Credential"%>
<%@ page import="org.orphane.model.RegularUsers"%>
<%@ page import="org.orphane.model.Orphanage"%>
<%@ page import="org.orphane.util.CKUtil"%>
<%@ page import="org.orphane.services.FetchContent"%>
<%
	Credential user = CKUtil.fetchDetails(request);
	RegularUsers reg;
	List<String> state = null;
	int i = 1;
	Long id = 0L;
	List<Orphanage> orp = new LinkedList<Orphanage>();
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else if (user != null) {
		reg = FetchContent.getRegUserDetails(user.getEmail());
		id = reg.getId();
		orp = FetchContent.getSubscribedOrphanage(reg.getId());
		state = GetAllDetails.getRegularUserSubscribedOrphanageState(reg.getId());
		for (Orphanage each : orp) {
			System.out.println(orp);
		}
	}
%>
<%
	if (orp == null || orp.isEmpty()) {
%>
<div class="text-center" style="padding-top: 100px;">
	<h1>No orphanges added</h1>
	<div class="col text-center" style="padding-top: 60px;">
		<button onclick="changePage()" class="btn btn-success">Add
			Orphanage</button>
	</div>
</div>
<%
	} else {
%>
<div class="container" style="padding-top: 80px;">
	<div class="row">
		<div class="col-md-4 offset-8" style="padding-bottom: 50px">
			<div class="dropdown">
				<button class="btn btn-success dropdown-toggle" type="button"
					id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">Filter By State</button>
				<button onclick="loadShowOrp()" class="btn btn-success float-right"
					id="showButton">Show All</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<%
						for (String each : state) {
					%>
					<a class="dropdown-item" id="selectedState"
						onclick="fetchByStateInOrp(this)" value="<%=each + "-" + id%>"
						href="#"><%=each%></a>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<table class="table">
		<thead>
			<tr class="table-success">
				<th scope="col">S.No</th>
				<th scope="col">Orphanage Name</th>
				<th>State</th>
				<th></th>
			</tr>
		</thead>
		<tbody id="setSelectedFilter">
			<%
				for (Orphanage each : orp) {
						if (each.getCredential().status.equals(UserStatus.ACTIVATED)) {
			%>
			<tr class="table-secondary">
				<th scope="row"><%=i%></th>
				<td id="name"><%=each.getName()%></td>
				<td id="state"><%=each.getAddress().state%></td>
				<td onclick='deleteOrphanage("<%=each.getId()%>")'
					class="float-right"><button class="btn btn-danger">Unfollow</button></td>
			</tr>
			<%
				i++;
						}
			%>
			<%
				}
			%>
		</tbody>
	</table>
</div>
<%
	}
%>
