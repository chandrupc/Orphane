<%@page import="org.orphane.modelenum.UserType"%>
<%@page import="org.orphane.modelenum.UserStatus"%>
<%@page import="org.orphane.services.GetAllDetails"%>
<%@page import="java.util.List"%>
<%@page import="org.orphane.model.Orphanage"%>
<%@page import="org.orphane.model.Credential"%>
<%@page import="org.orphane.util.CKUtil"%>
<%
	Credential user = CKUtil.fetchDetails(request);
	List<Orphanage> orp = null;
	List<String> state = null;
	int i = 1;
	if (user == null) {
		request.getRequestDispatcher("login.html").forward(request, response);
	} else if (user != null) {
		orp = GetAllDetails.getOrphanages();
		state = GetAllDetails.getOrphanageState();
	}
%>
<%
	if (orp == null || orp.isEmpty()) {
%>
<div class="text-center" style="padding-top: 100px;">
	<h1>No more orphanges to add</h1>
	<div class="col text-center" style="padding-top: 60px;">
		<button onclick="loadShowOrp()" class="btn btn-success">Show
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
				<button type="button" onclick="changePage()"
					class="btn btn-success float-right" id="showButton">Show
					All</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<%
						for (String each : state) {
					%>
					<a class="dropdown-item" id="selectedState"
						onclick="fetchByState(this)" value="<%=each%>" href="#"><%=each%></a>
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
		<tbody id="setFilter">
			<%
				for (Orphanage each : orp) {
						if (each.getCredential().status.equals(UserStatus.ACTIVATED)) {
			%>
			<tr class="table-secondary">
				<th scope="row"><%=i%></th>
				<td id="name"><%=each.getName()%></td>
				<td id="state"><%=each.getAddress().state%></td>
				<td
					onclick='addOrphanage("<%=each.getName() + "," + each.getAddress().state%>")'
					class="float-right"><button class="btn btn-success">Add</button></td>
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