/*--------------------------------------USER ERRORS-----------------------------*/

var userError = [ "Enter a valid email-id", "Enter a valid first-name",
		"Enter a valid last-name",
		"Email-id doesnot exists please sign up to login",
		"Account not activated/Requested account is deleted",
		"Enter a valid orphanage name" ];
var passError = [ "Password cannot be empty", "Please enter the password",
		"Password mismatch", "Minimum 8 characters",
		"New and Confirm Password mismatch", "Current Password mismatch",
		"Password Updated" ];
var phoneNumberError = [ "Please enter a 10 digit valid mobile number",
		"Alternate number is same as primary" ];
var addressError = [ "Please fill out the address properly",
		"Please enter valid city", "Please enter valid state name",
		" Please enter valid zipcode", "Address already taken" ];
var lengthError = [ "Maximum 30 characters", "Maximum 255 characters",
		"Maximum 50 characters" ];
var websiteError = [ "Please enter a valid website", "Website already taken" ]
var emailStatus = [ "Email available", "Email id already taken" ]
var names = [];
var orphanageId = 0;
var orphanageState = "";
var eventError = [ "Select an event",
		"Invalid orphanage name / \n Orphanage doesn't added yet",
		"Date Required", "Past date is not accepted",
		"Pre-Requisiton time is over Event can't be added" ];
/*---------------------------------------REGEX-----------------------------------*/

var nameReg = /^[a-zA-Z]+$/;
var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var phoneRegex = /^[0-9]{10}$/;
var addRegex = /^[0-9a-z A-Z-,'/ / ]+$/;
var zipRegex = /^[0-9]{6}$/;
var stateReg = /^[a-zA-Z ]*$/;
var webReg = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;

/*---------------------------------------NAVIGATION TAB-----------------------------------*/

function login() {
	location.href = "login-user";
}

/*---------------------------------------ORPHANAGE USERS SIGN UP FORM RESET-----------------------------------*/

function loadOrpSignUp() {
	document.getElementById("orpsignup").reset();
}

/*---------------------------------------GETTING VALUES FROM REQUEST-----------------------------------*/

function getValue(idName) {
	return document.getElementById(idName).value.trim();
}

/*---------------------------------------DISPLAY ERRORS-----------------------------------*/

function dispError(idName, arrName, index) {
	// console.log(idName);
	document.getElementById(idName).innerHTML = arrName[index];
}

/*---------------------------------------AJAX REQUESTS-----------------------------------*/

function ajaxRequest(functionName, url, method, parameters) {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open(method, url, true);
	if (method == "post") {
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
	}
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			functionName(this.responseText);
		}
	}
	if (parameters === "") {
		ajax.send();
	} else {
		ajax.send(parameters);
	}
}

/*---------------------------------------AVAILABILITY OF MAIL ID-----------------------------------*/

function checkUserAvailability(idName, errorTag) {
	var email = getValue(idName);
	// console.log(email);
	if (checkEmail(email)) {
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "availability", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				if (this.responseText === "available") {
					dispError(errorTag, emailStatus, 0);
				} else if (this.responseText === "already exists") {
					dispError(errorTag, emailStatus, 1);
				}
			}
		}
		ajax.send("email=" + email);
	} else {
		dispError(errorTag, userError, 0);
	}
}

function returnStatus(response) {
	// console.log(response);
	if (response === "available") {
		console.log("returning success");
		return "success";
	} else if (response === "already exists") {
		console.log("Returning fail");
		return "fail";
	}
}

/*---------------------------------------SUBSCRIBE FOR NOTIFICATIONS-----------------------------------*/
function subscribe() {
	var status = true;
	var name = getValue("subscriber-name");
	var email = getValue("email");
	// console.log(name + " " + email);
	if (checkState(name) === false || checkField(name) === false) {
		dispError("name-error", userError, 1);
		status = false;
	}
	if (checkEmail(email) === false) {
		dispError("mail-error", userError, 0);
		status = false;
	}
	if (status) {
		// console.log("entered");
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "notify-users", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				if (this.responseText === "error") {
					alert("Server error plese try after some time")
				} else if (this.responseText === "success") {
					alert("Subscribed Successfully!!!\n\nYou will receive notification")
				} else {
					alert(this.responseText);
				}
			}
		}
		// console.log("name=" + name + "&mail=" + email);
		ajax.send("username=" + name + "&mail=" + email);
	}
	return false;
}
/*---------------------------------------LOGIN FORM VALIDATION-----------------------------------*/

function validateLogin() {
	// console.log("Success");
	var username = getValue("user-email");
	var password = getValue("user-password");
	if (checkEmail(username) === false) {
		dispError("username-error", userError, 0);
	}

	if (checkField(password) === false) {
		dispError("password-error", passError, 0);
	}

	if (checkEmail(username) === true && checkField(password) === false) {
		dispError("password-error", passError, 1);
	}

	if (checkEmail(username) && checkField(password)) {
		var parameter = "email=" + username + "&pass=" + password;
		ajaxRequest(loginCheck, "login-user", "post", parameter);
	}
	return false;
}

function loginCheck(message) {
	// console.log(message);
	if (message === "no cookies") {
		location.href = "login.html";
	}
	if (message === "invalid user") {
		dispError("username-error", userError, 3);
	} else if (message === "account not activated") {
		dispError("username-error", userError, 4);
	} else if (message === "Incorrect Password") {
		alert("Incorrect password")
	} else if (message === "success") {
		location.href = "orphanage.jsp";
	}
}

/*---------------------------------------REGULAR SIGN UP FORM VALIDATION-----------------------------------*/

function regCheckDetails() {
	var valid = true;
	var firstName = getValue("firstName");
	var lastName = getValue("lastName");
	var phoneNumber = getValue("phoneNumber");
	var altNumber = getValue("altNumber");
	var address = getValue("address");
	var city = getValue("city");
	var state = getValue("state");
	var zip = getValue("zip");
	var emailId = getValue("emailId");
	var pass = getValue("pass");
	var checkPass = getValue("checkPass");
	var dob = getValue("dateOfBirth");
	if (checkState(firstName) === false || firstName === ""
			|| firstName === null) {
		dispError("fname-error", userError, 1);
		valid = false;
	}

	if (checkState(lastName) === false || lastName === "" || lastName === null) {
		dispError("lname-error", userError, 2);
		valid = false;
	}

	if (checkPhoneNumber(phoneNumber) === false) {
		dispError("number-error", phoneNumberError, 0);
		valid = false;
	}
	if (checkField(altNumber)) {
		if (checkPhoneNumber(altNumber) === false) {
			dispError("altnum-error", phoneNumberError, 0);
			valid = false;
		}
	}
	if (phoneNumber === altNumber && (checkField(altNumber))
			&& (checkField(phoneNumber))) {
		dispError("number-error", phoneNumberError, 1);
		dispError("altnum-error", phoneNumberError, 1);
		valid = false;
	}
	if (checkAddress(address) === false) {
		dispError("address-error", addressError, 0);
		valid = false;
	}

	if (checkState(city) === false || city === "" || city === null) {
		dispError("city-error", addressError, 1);
		valid = false;
	}

	if (checkState(state) === false || state === "" || state === null) {
		dispError("state-error", addressError, 2);
		valid = false;
	}

	if (checkZip(zip) === false) {
		dispError("zip-error", addressError, 3);
	}

	if (checkField(pass) === false) {
		dispError("pass-error", passError, 0);
		valid = false;
	}
	if (checkPasswordLength(pass) === true) {
		if (checkField(checkPass) === false) {
			dispError("checkpass-error", passError, 0);
			valid = false;
		} else if (checkField(checkPass) === true) {
			if (pass !== checkPass) {
				dispError("pass-error", passError, 2);
				dispError("checkpass-error", passError, 2);
				valid = false;
			}
		}
	} else {
		dispError("pass-error", passError, 3);
		valid = false;
	}
	console.log(valid);
	if (valid === true) {
		if (checkEmail(emailId)) {
			var ajax;
			if (XMLHttpRequest) {
				ajax = new XMLHttpRequest();
			} else {
				ajax = new ActiveXobject("Microsoft.XHTTP");
			}
			ajax.open("post", "availability", true);
			ajax.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			ajax.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					if (this.responseText === "available") {
						var parameter = "firstName=" + firstName + "&lastName="
								+ lastName + "&dateOfBirth=" + dob
								+ "&phoneNumber=" + phoneNumber + "&altNum="
								+ altNumber + "&address=" + address + "&city="
								+ city + "&state=" + state + "&zip=" + zip
								+ "&email=" + emailId + "&password=" + pass;
						ajaxRequest(regSignUp, "rsignup", "post", parameter);
					} else if (this.responseText === "already exists") {
						dispError("email-error", emailStatus, 1);
					}
				}
			}
			ajax.send("email=" + emailId);
		}
	}
	return false;
}

function regSignUp(got) {
	// console.log(got);
	if (got === "Phone Number Taken") {
		document.getElementById("number-error").innerHTML = got;
		document.getElementById("altnum-error").innerHTML = got;
		alert(got);
	} else if (got === "Address Already Taken") {
		alert(got);
		document.getElementById("address-error").innerHTML = got;
	} else if (got === "error") {
		alert("Servor error occured\nPlease Try after sometimes")
	} else if (got === "Network Error") {
		alert("Network is not connected/Server error Try after sometimes");
	} else if (got === "success") {
		location.href = "index.html";
	}

}

/*---------------------------------------ORPHANAGE SIGN UP FORM VALIDATAION-----------------------------------*/

function orpCheckDetails() {
	var valid = true;
	var orpName = getValue("orpName");
	var orpPh = getValue("orpPhoneNumber");
	var orpAltNum = getValue("orpAltNumber");
	var orpAdd = getValue("orpAddress");
	var orpCity = getValue("orpCity");
	var orpState = getValue("orpState");
	var orpZip = getValue("orpZip");
	var orpWebsite = getValue("orpWebsite");
	var orpPass = getValue("orpPass");
	var email = getValue("orpEmailId");
	var orpCheckPass = getValue("orpCheckPass");

	if (checkState(orpName) === false || orpName === "" || orpName === null) {
		dispError("orpname-error", userError, 5);
		valid = false;
	}

	if (checkPhoneNumber(orpPh) === false) {
		dispError("orpnumber-error", phoneNumberError, 0);
		valid = false;
	}
	if (checkField(orpAltNum)) {
		if (checkPhoneNumber(orpAltNum) === false) {
			dispError("orpaltnum-error", phoneNumberError, 0);
			valid = false;
		}
	}
	if (orpPh === orpAltNum && (checkField(orpAltNum)) && (checkField(orpPh))) {
		dispError("orpnumber-error", phoneNumberError, 1);
		dispError("orpaltnum-error", phoneNumberError, 1);
		valid = false;
	}
	if (checkAddress(orpAdd) === false) {
		dispError("orpaddress-error", addressError, 0);
		valid = false;
	}

	if (checkState(orpCity) === false || orpCity === "" || orpCity === null) {
		dispError("orpcity-error", addressError, 1);
		valid = false;
	}

	if (checkState(orpState) === false || orpState === "" || orpState === null) {
		dispError("orpstate-error", addressError, 2);
		valid = false;
	}

	if (checkZip(orpZip) === false) {
		dispError("orpzip-error", addressError, 3);
	}

	if (orpWebsite !== "" && orpWebsite !== null) {
		if (checkWebsite(orpWebsite) === false) {
			dispError("orpwebsite-error", websiteError, 0);
			valid = false;
		}
	}

	if (checkField(orpPass) === false) {
		dispError("orppass-error", passError, 0);
		valid = false;
	}
	if (checkPasswordLength(orpPass) === true) {
		if (checkField(orpCheckPass) === false) {
			dispError("orpcheckpass-error", passError, 0);
			valid = false;
		} else if (checkField(orpCheckPass) === true) {
			if (orpPass !== orpCheckPass) {
				dispError("orppass-error", passError, 2);
				dispError("orpcheckpass-error", passError, 2);
				valid = false;
			}
		}
	} else {
		dispError("orppass-error", passError, 3);
		valid = false;
	}
	console.log(valid);
	if (valid === true) {
		if (checkEmail(email)) {
			var ajax;
			if (XMLHttpRequest) {
				ajax = new XMLHttpRequest();
			} else {
				ajax = new ActiveXobject("Microsoft.XHTTP");
			}
			ajax.open("post", "availability", true);
			ajax.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			ajax.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					if (this.responseText === "available") {
						var parameter = "orpName=" + orpName + "&orpPhone="
								+ orpPh + "&orpAltNum=" + orpAltNum
								+ "&orpAddress=" + orpAdd + "&orpCity="
								+ orpCity + "&orpState=" + orpState
								+ "&orpZip=" + orpZip + "&orpWebsite="
								+ orpWebsite + "&orpEmail=" + email
								+ "&orpPassword=" + orpPass;
						ajaxRequest(redirectSignUp, "osignup", "post",
								parameter);
					} else if (this.responseText === "already exists") {
						dispError("orpemail-error", emailStatus, 1);
					}
				}
			}
			ajax.send("email=" + email);
		}
	}
	return false;
}

/*---------------------------------------ORPHANAGE SIGN UP FORM REDIRECT-----------------------------------*/

function redirectSignUp(received) {
	// console.log(received);
	if (received === "Phone Number found") {
		document.getElementById('orpnumber-error').innerHTML = received;
		document.getElementById('orpaltnum-error').innerHTML = received;
		alert(received);
	} else if (received === "Website found") {
		document.getElementById('orpwebsite-error').innerHTML = received;
		alert(received);
	} else if (received === "Address found") {
		document.getElementById("orpaddress-error").innerHTML = received;
		alert(received);
	} else if (received === "Error") {
		alert("Server error occured try again after some times");
	} else if (received == "Network error") {
		alert("Network is not connected/Server error Try after sometimes");
	}
	if (received === "success") {
		location.href = "index.html";
	}
}
/*---------------------------------------REGEX TESTS -----------------------------------*/

function checkState(state) {
	return stateReg.test(state);
}

function checkEmail(email) {
	return emailRegex.test(email);
}

function checkPhoneNumber(no) {
	return phoneRegex.test(no);
}

function checkName(name) {
	return nameReg.test(name);
}

function checkWebsite(website) {
	return webReg.test(website);
}

function checkAddress(address) {
	return addRegex.test(address);
}

function checkZip(zipcode) {
	return zipRegex.test(zipcode);
}

function checkField(value) {
	if (value === "" || value === null) {
		return false;
	} else {
		return true;
	}
}

function checkPasswordLength(value) {
	if (value.length < 8) {
		return false;
	}
	return true;
}

/*---------------------------------------SHOW PASSWORD-----------------------------------*/

function togglePassword() {
	var check = document.getElementById("inputPassword").checked;
	// console.log(check)
	if (check === true) {
		document.getElementById("password-check").setAttribute('type', 'text');
	} else {
		document.getElementById("password-check").setAttribute('type',
				'password');
	}
}

/*---------------------------------------CLEAR ERRORS-----------------------------------*/

function clearError(idName) {
	// console.log(temp);
	switch (idName) {
	case "username-error":
	case "password-error":
	case "fname-error":
	case "lname-error":
	case "number-error":
	case "eventDateError":
	case "eventNameError":
	case "orpNameError":
	case "altnum-error":
	case "address-error":
	case "city-error":
	case "state-error":
	case "zip-error":
	case "email-error":
	case "pass-error":
	case "suggest":
	case "checkpass-error":
	case "orpname-error":
	case "orpnumber-error":
	case "orpaltnum-error":
	case "orpaddress-error":
	case "orpcity-error":
	case "orpstate-error":
	case "orpzip-error":
	case "orpemail-error":
	case "orppass-error":
	case "orpcheckpass-error":
	case "orpwebsite-error":
	case "name-error":
	case "mail-error":
		document.getElementById(idName).innerHTML = "";
		break;
	}
}

/*---------------------------------------FORGOT PASSWORD-----------------------------------*/

function sendForgotMail() {
	var mail = getValue("mailId");
	if (checkField(mail) && checkEmail(mail)) {
		var parameter = "mail=" + mail;
		ajaxRequest(forgotPass, "forgot-pass", "post", parameter);
	} else {
		alert("Error occured Provide valid details");
	}
	return false;
}
function forgotPass(message) {
	console.log(message === "success");
	if (message === "success") {
		alert("Reset Link has been sent to your Mail");
		location.href = "login.html";
	} else if (message === "error") {
		alert("Server Error\n\nPlease try after some time");
	}
}

/*---------------------------------------New Password Validation-----------------------------------*/

function changePassDisable() {
	document.getElementById("mail-id").disabled = true;
}
function checkNewPassword() {
	var mail = getValue("mailId");
	var pass1 = getValue("newPassword1");
	var pass2 = getValue("newPassword2");
	if (checkField(pass1) && checkField(pass2)) {
		if (pass1 == pass2) {
			var parameters = "mail=" + mail + "&pass1=" + pass1 + "&pass2="
					+ pass2;
			ajaxRequest(loginRedirect, "change-password", "post", parameters);
		} else if (pass1 != pass2) {
			alert("Password Mismatch");
		}
	}
	return false;
}

function loginRedirect(message) {
	console.log(message);
	if (message === "success") {
		alert("Password updated successfully");
		location.href = "login.html";
	}
	if (message === "No user") {
		alert("No such user exists");
	}
}

/* onkeyup functions */

/*---------------------------------------CONDITION CHECKERS-----------------------------------*/

function orpNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		console.log(x);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, 2);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function firstNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 30) {
		x = x.slice(0, x.length - 1);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, 0);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function lastNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 30) {
		x = x.slice(0, x.length - 1);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, 0);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function addressLength(idName, errorTag, index) {
	var x = getValue(idName);
	if (x.length > 255) {
		x = x.slice(0, x.length - 1);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, index);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function cityLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, 2);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function stateLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		document.getElementById(idName).value = x;
		dispError(errorTag, lengthError, 2);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

/*---------------------------------------ORPHANAGE PROFILE JAVASCRIPT-----------------------------------*/

function enable() {
	document.getElementById("inputPhoneNumber").disabled = false;
	document.getElementById("inputAltPhoneNumber").disabled = false;
	document.getElementById("inputAddress").disabled = false;
	document.getElementById("inputCity").disabled = false;
	document.getElementById("inputState").disabled = false;
	document.getElementById("inputZip").disabled = false;
	document.getElementById("updateButton").hidden = false;
	document.getElementById("cancelButton").hidden = false;
}

function validate() {
	clearError("number-error");
	clearError("altnum-error");
	clearError("address-error");
	var valid = true;
	var email = getValue("inputEmail");
	var phoneNumber = getValue("inputPhoneNumber");
	var altNumber = getValue("inputAltPhoneNumber");
	var address = getValue("inputAddress");
	var city = getValue("inputCity");
	var state = getValue("inputState");
	var zip = getValue("inputZip");

	if (checkPhoneNumber(phoneNumber) === false) {
		dispError("number-error", phoneNumberError, 0);
		valid = false;
	}
	if (checkField(altNumber)) {
		if (checkPhoneNumber(altNumber) === false) {
			dispError("altnum-error", phoneNumberError, 0);
			valid = false;
		}
	}
	if (phoneNumber === altNumber && (checkField(altNumber))
			&& (checkField(phoneNumber))) {
		dispError("number-error", phoneNumberError, 1);
		dispError("altnum-error", phoneNumberError, 1);
		valid = false;
	}
	if (checkAddress(address) === false) {
		dispError("address-error", addressError, 0);
		valid = false;
	}

	if (checkState(city) === false || city === "" || city === null) {
		dispError("city-error", addressError, 1);
		valid = false;
	}

	if (checkState(state) === false || state === "" || state === null) {
		dispError("state-error", addressError, 2);
		valid = false;
	}

	if (checkZip(zip) === false) {
		dispError("zip-error", addressError, 3);
	}
	if (valid) {
		var ajax, parameter;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "updateuserprofile", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		parameter = "email=" + email + "&phoneNumber=" + phoneNumber
				+ "&altNum=" + altNumber + "&address=" + address + "&city="
				+ city + "&state=" + state + "&zip=" + zip;
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log(this.responseText);
				if (this.responseText === "NUMBER TAKEN") {
					dispError("number-error", phoneNumberError, 2);
				} else if (this.responseText === "ALT NUMBER TAKEN") {
					dispError("altnum-error", phoneNumberError, 2);
				} else if (this.responseText === "ADDRESS TAKEN") {
					dispError("address-error", addressError, 4);
				} else if (this.responseText === "SUCCESS") {
					disable();
				} else {
					alert("Unable to Update")
				}
			}
		}
		ajax.send(parameter);
	} else {
		window.alert("Enter valid values");
	}
	return valid;
}

function disable() {
	document.getElementById("inputPhoneNumber").disabled = true;
	document.getElementById("inputAltPhoneNumber").disabled = true;
	document.getElementById("inputAddress").disabled = true;
	document.getElementById("inputCity").disabled = true;
	document.getElementById("inputState").disabled = true;
	document.getElementById("inputZip").disabled = true;
	document.getElementById("updateButton").hidden = true;
	document.getElementById("cancelButton").hidden = true;
}

/*---------------------------------------REGULAR PROFILE SHOW ORPHANAGES-----------------------------------*/

function changePage() {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "showOrphanages.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			document.getElementById("home").innerHTML = this.responseText;
		}
	}
	ajax.send();
}
/*---------------------------------------REGULAR PROFILE SHOW ADDED ORPHANAGES-----------------------------------*/

function loadShowOrp() {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "showOrp.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			document.getElementById("home").innerHTML = this.responseText;
		}
	}
	ajax.send();
}

/*---------------------------------------REGULAR PROFILE SHOW ADDED ORPHANAGES BY STATE-----------------------------------*/

function fetchByStateInOrp(anchor) {
	// console.log(anchor);
	var selectedState = anchor.getAttribute('value');
	// console.log(selectedState);
	var splitContent = selectedState.split("-");
	// console.log(splitContent);
	var state = splitContent[0];
	var regId = splitContent[1];
	// console.log(state + " " + regId);
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "fetch-added-details", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var arr = JSON.parse(this.responseText);
			console.log(arr);
			var setContent = "";
			for (i = 1; i <= arr.length; i++) {
				setContent += "<tr class='table-secondary'>"
						+ "<th scope='row'>"
						+ i
						+ "</th>"
						+ "<td id='name'>"
						+ arr[i - 1][1]
						+ "</td>"
						+ "<td id='state'>"
						+ arr[i - 1][2]
						+ "</td>"
						+ "<td id='number'>"
						+ arr[i - 1][3]
						+ "</td>"
						+ "<td onclick='deleteOrphanage("
						+ '"'
						+ arr[i - 1][0]
						+ '"'
						+ ")'"
						+ "class='float-right'><button class='btn btn-danger'>UNFOLLOW</button></td>"
						+ "</tr>";
				// console.log(setContent);
			}
			document.getElementById("setSelectedFilter").innerHTML = setContent;
		}
	}
	ajax.send("state=" + state + "&regId=" + regId);
}

/*---------------------------------------REGULAR PROFILE SHOW ORPHANAGES BY STATE-----------------------------------*/

function fetchByState(anchor) {
	var selectedState = anchor.getAttribute('value');
	// console.log(selectedState);
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "fetch-details", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var arr = JSON.parse(this.responseText);
			console.log(arr);
			var setContent = "";
			for (i = 1; i <= arr.length; i++) {
				setContent += "<tr class='table-secondary'>"
						+ "<th scope='row'>"
						+ i
						+ "</th>"
						+ "<td id='name'>"
						+ arr[i - 1][1]
						+ "</td>"
						+ "<td id='state'>"
						+ arr[i - 1][2]
						+ "</td>"
						+ "<td id='number'>"
						+ arr[i - 1][3]
						+ "</td>"
						+ "<td onclick='addOrphanage("
						+ '"'
						+ arr[i - 1][1]
						+ ','
						+ arr[i - 1][2]
						+ '"'
						+ ")'"
						+ "class='float-right'><button class='btn btn-success'>FOLLOW</button></td>"
						+ "</tr>";
				// console.log(setContent);
			}
			document.getElementById("setFilter").innerHTML = setContent;
		}
	}
	ajax.send("name=" + selectedState);
}

/*---------------------------------------ADD ORPHANAGES TO REGULAR USERS-----------------------------------*/

function addOrphanage(clickedRow) {
	var content = clickedRow.split(",");
	var state = content[1];
	var name = content[0];
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "add-orphanage", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var response = this.responseText;
			if (response === "error") {
				alert("Error occured please try after sometimes");
			} else if (response === "success") {
				alert("Successfully added\nYou will receive notifications if they post a need");
				changePage();
			}
		}
	}
	var parameter = "orpName=" + name + "&orpState=" + state;
	// console.log(parameter);
	ajax.send(parameter);
}

/*---------------------------------------DELETE ORPHANAGES TO REGULAR USERS-----------------------------------*/

function deleteOrphanage(id) {
	// console.log(id);var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "delete-orp-reg-maintenance", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			loadShowOrp();
		}
	}
	ajax.send("id=" + id);
}

/*---------------------------------------DELETE REGULAR USER PROFILE-----------------------------------*/

function deleteAccount(email) {
	console.log(email);
	var status = confirm("This will leads to permanently delete your profile");
	if (status) {
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XMLHTTP");
		}
		ajax.open("post", "delete-reg-profile", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log("success");
				if (this.responseText === "success") {
					location.href = "index.html";
				} else if (this.responseText === "error") {
					alert("Account doesn't exists/server error try again later");
				}
			}
		}
		ajax.send("email=" + email);
	}
}

function deleteOrpAccount(email) {
	console.log(email);
	var status = confirm("This will leads to permanently delete your profile");
	if (status) {
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XMLHTTP");
		}
		ajax.open("post", "delete-orp-profile", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log(this.responseText);
				if (this.responseText === "success") {
					location.href = "index.html";
				} else if (this.responseText === "error") {
					alert("Account doesn't exists/server error try again later");
				}
			}
		}
		ajax.send("email=" + email);
	}
}

/*-----------------------------USERPROFILE PASSWORD UPDATION-------------------------------*/

function updatePassword(check) {
	clearError("pass-error");
	var email;
	if (check === 1) {
		email = getValue("inputOrpEmail");
	} else {
		email = getValue("inputEmail");
	}
	var curpassword = getValue("currentPassword");
	var newpassword = getValue("newPassword");
	var conpassword = getValue("confirmPassword");
	if (conpassword === newpassword) {
		var ajax, parameter;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "updatepassword", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		parameter = "email=" + email + "&currentPassword=" + curpassword
				+ "&newPassword=" + newpassword;
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log(this.responseText);
				if (this.responseText === "CURRENT PASSWORD MISMATCH") {
					document.getElementById("pass-error").style.color = "red";
					dispError("pass-error", passError, 5);
					return false;
				} else if (this.responseText === "SUCCESS") {
					document.getElementById("pass-error").style.color = "green";
					dispError("pass-error", passError, 6);
					return true;
				}
			}
		}
		ajax.send(parameter);
	} else {
		document.getElementById("pass-error").style.color = "red";
		dispError("pass-error", passError, 4);
		return false;
	}
}

/*----------------------------ORPHANAGE PROFILE UPDATION----------------------*/

function validateOrphanageProfile() {
	clearError("number-error");
	clearError("altnum-error");
	clearError("address-error");
	clearError("orpwebsite-error");
	var valid = true;
	var email = getValue("inputOrpEmail");
	var website = getValue("inputWebsite");
	var phoneNumber = getValue("inputOrpPhoneNumber");
	var altNumber = getValue("inputOrpAltPhoneNumber");
	var address = getValue("inputOrpAddress");
	var city = getValue("inputOrpCity");
	var state = getValue("inputOrpState");
	var zip = getValue("inputOrpZip");

	if (checkPhoneNumber(phoneNumber) === false) {
		dispError("number-error", phoneNumberError, 0);
		valid = false;
	}
	if (website !== "" && website !== null) {
		if (checkWebsite(website) === false) {
			dispError("orpwebsite-error", websiteError, 0);
			valid = false;
		}
	}
	if (checkField(altNumber)) {
		if (checkPhoneNumber(altNumber) === false) {
			dispError("altnum-error", phoneNumberError, 0);
			valid = false;
		}
	}
	if (phoneNumber === altNumber && (checkField(altNumber))
			&& (checkField(phoneNumber))) {
		dispError("number-error", phoneNumberError, 1);
		dispError("altnum-error", phoneNumberError, 1);
		valid = false;
	}
	if (checkAddress(address) === false) {
		dispError("address-error", addressError, 0);
		valid = false;
	}

	if (checkState(city) === false || city === "" || city === null) {
		dispError("city-error", addressError, 1);
		valid = false;
	}

	if (checkState(state) === false || state === "" || state === null) {
		dispError("state-error", addressError, 2);
		valid = false;
	}

	if (checkZip(zip) === false) {
		dispError("zip-error", addressError, 3);
	}
	if (valid) {
		var ajax, parameter;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "updateorphanageprofile", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		parameter = "email=" + email + "&phoneNumber=" + phoneNumber
				+ "&website=" + website + "&altNum=" + altNumber + "&address="
				+ address + "&city=" + city + "&state=" + state + "&zip=" + zip;
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log(this.responseText);
				if (this.responseText === "NUMBER TAKEN") {
					dispError("number-error", phoneNumberError, 2);
				} else if (this.responseText === "ALT NUMBER TAKEN") {
					dispError("altnum-error", phoneNumberError, 2);
				} else if (this.responseText === "ADDRESS TAKEN") {
					dispError("address-error", addressError, 4);
				} else if (this.responseText === "WEBSITE TAKEN") {
					dispError("orpwebsite-error", websiteError, 1);
				} else if (this.responseText === "SUCCESS") {
					disableOrphanage();
				} else {
					alert("Unable to Update")
				}
			}
		}
		ajax.send(parameter);
	}
	return valid;
}

function enableOrphanage() {
	document.getElementById("inputWebsite").disabled = false;
	document.getElementById("inputOrpPhoneNumber").disabled = false;
	document.getElementById("inputOrpAltPhoneNumber").disabled = false;
	document.getElementById("inputOrpAddress").disabled = false;
	document.getElementById("inputOrpCity").disabled = false;
	document.getElementById("inputOrpState").disabled = false;
	document.getElementById("inputOrpZip").disabled = false;
	document.getElementById("updateButton").hidden = false;
	document.getElementById("cancelButton").hidden = false;
}

function disableOrphanage() {
	document.getElementById("inputWebsite").disabled = true;
	document.getElementById("inputOrpPhoneNumber").disabled = true;
	document.getElementById("inputOrpAltPhoneNumber").disabled = true;
	document.getElementById("inputOrpAddress").disabled = true;
	document.getElementById("inputOrpCity").disabled = true;
	document.getElementById("inputOrpState").disabled = true;
	document.getElementById("inputOrpZip").disabled = true;
	document.getElementById("updateButton").hidden = true;
	document.getElementById("cancelButton").hidden = true;
}

/*----------------------------ORPHANAGE PROFILE UPDATION----------------------*/

function loadEventJsp(id) {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "Event.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			showSuggestion(id);
			document.getElementById("home").innerHTML = this.responseText;
		}
	}
	ajax.send();
}

/*----------------------------EVENT NAME SETTING----------------------*/

function setEvent(anchor, id) {
	// console.log(anchor);
	var selected = anchor.getAttribute('value');
	document.getElementById("eventName").innerHTML = selected;
}

/*----------------------------EVENT FORM SUGGESTION FOR ORPHANAGE NAME----------------------*/

function showSuggestion(id) {
	// console.log("success");
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "show-suggestions", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			if (this.responseText === "error") {
				alert("Please try after some times");
			} else {
				localStorage.clear();
				localStorage.setItem("orphanageName", JSON
						.parse(this.responseText));
				fetchLocalStorage();
			}
		}
	}
	ajax.send("id=" + id);
}

/*----------------------------EVENT FORM LOCAL STORAGE FETCH CONTENT----------------------*/

function fetchLocalStorage() {
	var content = localStorage.getItem("orphanageName");
	names = content.split(',');
	// console.log(names);
}

/*----------------------------EVENT FORM SHOW ORPHANAGE SUGGESTIONS----------------------*/

function showOrpNames() {
	// console.log(names);
	var textEntered = getValue("orpName");
	var setContent = "<div class='results'>";
	var len = 0;
	for (var i = 0; i < names.length; i++) {
		// console.log(names[i]);
		if (names[i].toLowerCase().startsWith(textEntered.toLowerCase())) {
			setContent += "<a class='col5' onclick='setOrp(this)' value='"
					+ names[i] + "'><p>" + names[i].split('-')[0] + ", "
					+ names[i].split('-')[2] + "</p></a>";
			len++;
			// console.log(names[i].split('-')[0]);
		}
		if (len > 5) {
			break;
		}
	}
	setContent += "</div>";
	// console.log(setContent);
	document.getElementById("suggest").innerHTML = setContent;
}

/*----------------------------EVENT FORM SET ORPHANAGE NAME----------------------*/

function setOrp(clickedRow) {
	var orpName = clickedRow.getAttribute('value');
	document.getElementById("orpName").value = orpName.split('-')[0];
	orphanageId = orpName.split('-')[1];
	orphanageState = orpName.split('-')[2];
	document.getElementById("suggest").innerHTML = "";
}

/*----------------------------EVENT FORM VALIDATION----------------------*/

function validateEventDetails(regEmail) {
	var status = true;
	var eventDate = getValue("eventDate");
	var orpName = getValue("orpName");
	var eventName = document.getElementById("eventName").innerHTML;
	var eventDescription = getValue("eventDescription");
	var userDate = eventDate.split('-');
	var date = new Date();
	var curDay = date.getDate();
	var curMonth = date.getMonth();
	var curYear = date.getFullYear();
	var hour = date.getHours();
	var min = date.getMinutes();
	// console.log(hour + ":" + min);
	curMonth += 1;
	// console.log(userDate);
	// console.log(curDay + "-" + curMonth + "-" + curYear);
	if (eventDate === "" || eventDate === null) {
		dispError("eventDateError", eventError, 2);
		status = false;
	}
	if (eventName === "--Select--") {
		dispError("eventNameError", eventError, 0);
		status = false;
	}
	if (names.indexOf(orpName + '-' + orphanageId + '-' + orphanageState) < 0) {
		dispError("orpNameError", eventError, 1);
		status = false;
	}
	if (parseInt(userDate[0]) < curYear) {
		dispError("eventDateError", eventError, 3);
		status = false;
	} else if (parseInt(userDate[0]) === curYear) {
		if (parseInt(userDate[1]) < curMonth) {
			dispError("eventDateError", eventError, 3);
			status = false;
		} else if (parseInt(userDate[1]) === curMonth) {
			if (parseInt(userDate[2]) < curDay) {
				dispError("eventDateError", eventError, 3);
				status = false;
			} else if (parseInt(userDate[2]) === curDay) {
				console.log("todays date" + userDate[2]);
				if ((hour >= 6 && hour <= 10) && eventName === "BREAKFAST") {
					dispError("eventNameError", eventError, 4);
					status = false;
				}
				if ((hour > 10 && hour <= 15)
						&& (eventName === "LUNCH" || eventName === "BREAKFAST")) {
					dispError("eventNameError", eventError, 4);
					status = false;
				}
				if (hour > 15) {
					dispError("eventNameError", eventError, 4);
					status = false;
				}
			}
		}
	}
	if (status) {
		var parameters = "eventDate=" + eventDate + "&eventName=" + eventName
				+ "&orpId=" + orphanageId + "&description=" + eventDescription
				+ "&regEmail=" + regEmail;
		// console.log(parameters);
		ajaxRequest(redirectEvent, "add-event", "post", parameters);

	}
	return status;
}

function redirectEvent(message) {
	// console.log("message");
	if (message === "success") {
		orphanageId = 0;
		localStorage.clear();
		alert("Event has been added successfully");
		location.href = "RegularUser.jsp";
	} else if (message === "error") {
		alert("Server error please try after sometimes");
	} else {
		alert(message);
		location.href = "RegularUser.jsp";
	}
}

/*----------------------------FUNCTION SHOW EVENTS----------------------*/

function showEvents() {
	// console.log("showEvents");
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "showEvents.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			document.getElementById("home").innerHTML = this.responseText;
		}
	}
	ajax.send();
}

/*----------------------------DELETE EVENTS FROM SHOW EVENTS----------------------*/

function deleteEvent(id) {
	console.log(id);
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "delete-event", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var text = this.responseText;
			console.log(text);
			if (text === "success") {
				alert("Cancelled successfully");
				showEvents();
			} else if (text === "error") {
				alert("Server error try after some times");
			}

		}
	}
	ajax.send("id=" + id);
}

/*------------------------------------VIEWING ORPHANAGE POSTS--------------------------------*/

function posts(email) {
	var ajax;
	var temp = "";
	var post;
	console.log(email);
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "viewpost", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			if (this.responseText === "NO POSTS") {
				console.log(this.responeText);
				temp = "<div class='row'><div class='col posttext text-center'>No Posts.</div></div>"
						+ "<div class='text-center' style='padding-top:150px'><button onclick='orphanageJsp()' class='btn btn-success'>Post a need</button></div>";
				console.log(temp)
			} else {
				posts = JSON.parse(this.responseText);
				for (i in posts) {
					temp += "<div class='row'><div class='col posttext'>"
							+ posts[i].post
							+ "</div><div class='col text-end datestyle'><p style='color:#adad85'>Posted on : <p style='color: #00cc44;'>"
							+ posts[i].postDate + " " + posts[i].postTime
							+ "</p></p></div><hr></div>";
				}
				console.log(temp);
			}
			temp = "<div class='row'><div class='col posttext text-center'><h4>POSTS</h4></div></div>"
					+ temp;
			document.getElementById("orphome").innerHTML = temp;
		}
	}
	ajax.send("email=" + email);
}

/*-------------------------------ORPHANAGE POST-------------------------------*/

function postOrphanage(email) {
	var text = document.getElementById("postArea").value;
	var correctedText = text.replace(/(\r\n|\n|\r)/gm, "");
	console.log(text + email);
	console.log(correctedText);
	if (text === "") {
		dispError("post-error", post, 0);
	} else {
		var ajax, parameter;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "orphanagepost", true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		parameter = "email=" + email + "&postContent=" + correctedText;
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				console.log(this.responseText);
				if (this.responseText === "SUCCESS") {
					document.getElementById("postArea").value = "";
					alert("Successfully Posted");

				} else {
					alert("Unable to post. Try after sometime.")
				}
			}
		}
		ajax.send(parameter);
	}
}

/*-------------------REGULAR USER SHOW NOTIFICATIONS---------------------------------*/

function loadNotifications(email) {
	var ajax;
	var temp = "";
	console.log(email);
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("post", "notification", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			if (this.responseText === "NO ORPHANAGES ADDED YET") {
				temp = "<div class='row'><div class='col align-self-center othermsg'>No Orphanages Added To Show Notifications</div></div><div class='row justify-content-center'><div class='col text-center'><a class='addbutton' onclick='changePage()'>Add Orphanages</a></div></div>";
			} else if (this.responseText === "NO NOTIFICATIONS") {
				temp = "<div class='row'><div class='col align-self-center othermsg'>No Notifications.</div></div>";
			} else {
				var notifications = JSON.parse(this.responseText);
				for ( var i in notifications) {
					temp += "<div class='row'><div class='col-8 align-self-start'><h4 class='topic'>"
							+ notifications[i].Name
							+ "</h4></div><div class='col-4 text-end datestyle'><p style='color:#adad85;display:inline'>Posted on : <span style='color: #00cc44;'>"
							+ notifications[i].Date
							+ " "
							+ notifications[i].Time
							+ "</span></p></div></div><div class='row endofnotification'><div class='col posttext'>"
							+ notifications[i].Post + "<hr></div></div>";
				}
			}
			console.log(temp);
			document.getElementById("home").innerHTML = temp;
		}
	}
	ajax.send("email=" + email);
}

/*-------------------BACK CHANGE REGULAR USER JSP PAGE--------------------------*/

function regularJsp() {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "RegularUser.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			location.href = "RegularUser.jsp";
		}
	}
	ajax.send();
}

/*-------------------BACK CHANGE REGULAR USER JSP PAGE--------------------------*/

function orphanageJsp() {
	var ajax;
	if (XMLHttpRequest) {
		ajax = new XMLHttpRequest();
	} else {
		ajax = new ActiveXobject("Microsoft.XMLHTTP");
	}
	ajax.open("get", "orphanage.jsp", true);
	ajax.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			location.href = "orphanage.jsp";
		}
	}
	ajax.send();
}