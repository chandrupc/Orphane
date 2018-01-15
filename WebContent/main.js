/*--------------------------------------USER ERRORS-----------------------------*/

var userError = [ "Enter a valid email-id", "Enter a valid first-name",
		"Enter a valid last-name",
		"email-id doesnot exists please sign up to login",
		"account not activated", "enter a valid orphanage name" ];
var passError = [ "Password cannot be empty", "Please enter the password",
		"Password mismatch", "Minimum 8 characters" ];
var phoneNumberError = [ "Please enter a 10 digit valid mobile number",
		"Alternate number is same as primary" ];
var addressError = [ "Please fill out the address", "Please enter valid city",
		"Please enter valid state name", " Please enter valid zipcode" ];
var lengthError = [ "Maximum 30 characters", "Maximum 255 characters",
		"Maximum 50 characters" ];
var websiteError = [ "please enter a valid website" ]
var emailStatus = [ "email available", "email id already taken" ]

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
	location.href = "login.html";
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
		ajax = new XMLHttpRequest()
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
	if (checkEmail(email)) {
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XHTTP");
		}
		ajax.open("post", "availability");
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				// console.log(this.getAllResponseHeaders());
				// console.log(this.responseText);
				if (this.responseText === "available") {
					dispError(errorTag, emailStatus, 0);
				} else if (this.responseText === "already exists") {
					dispError(errorTag, emailStatus, 1);
				}
				// console.log(this.responseText);
				return this.responseText;
			}
		}
		ajax.send("email=" + email);
	} else {
		dispError(errorTag, userError, 0);
	}
}

/*---------------------------------------LOGIN FORM VALIDATION-----------------------------------*/

function validateLogin(status) {
	if (status == "true") {
		return true;
	} else {
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
			ajaxRequest(loginCheck, "login", "post", parameter);
		}
	}
	return false;
}

function loginCheck(message) {
	// console.log(message);
	if (message === "invalid user") {
		dispError("username-error", userError, 3);
	} else if (message === "account not activated") {
		dispError("username-error", userError, 4);
	} else if (message === "success") {
		validateLogin("true");
	}
}

/*---------------------------------------REGULAR SIGN UP FORM VALIDATION-----------------------------------*/

function regCheckDetails() {
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
	var count = 0;
	var flag = 0;
	(checkName(firstName) === false) ? dispError("fname-error", userError, 1)
			: count++;

	(checkName(lastName) === false) ? dispError("lname-error", userError, 2)
			: count++;

	(checkPhoneNumber(phoneNumber) === false) ? dispError("number-error",
			phoneNumberError, 0) : count++;

	if (altNumber !== null && altNumber !== "") {
		(checkPhoneNumber(altNumber) === false) ? dispError("altnum-error",
				phoneNumberError, 0) : flag = 1;
		count++;
	}
	if (phoneNumber === altNumber
			&& (phoneNumber !== "" && phoneNumber !== null)
			&& (altNumber !== "" && altNumber !== null)) {
		dispError("number-error", phoneNumberError, 1);
		dispError("altnum-error", phoneNumberError, 1);
		count -= 2;
	}
	(checkAddress(address) === false) ? dispError("address-error",
			addressError, 0) : count++;

	(checkState(city) === false || city === "" || city === null) ? dispError(
			"city-error", addressError, 1) : count++;

	(checkState(state) === false || state === "" || state === null) ? dispError(
			"state-error", addressError, 2)
			: count++;

	(checkZip(zip) === false) ? dispError("zip-error", addressError, 3)
			: count++;

	if (checkUserAvailability("emailId", "email-error") === true) {
		count++;
	}

	(checkField(pass) === false) ? dispError("pass-error", passError, 0)
			: count++;

	(checkField(checkPass) === false) ? dispError("checkpass-error", passError,
			0) : count++;

	(checkPasswordLength(pass) === false) ? dispError("pass-error", passError,
			3) : count++;

	if (checkField(pass) && checkField(checkPass) && pass !== checkPass) {
		dispError("pass-error", passError, 2);
		dispError("checkpass-error", passError, 2);
		count -= 2;
	}
	// console.log(count + " " + flag);
	if ((flag === 1 && count === 11) || (flag === 0 && count === 10)) {
		return true;
	}
	return false;

}

/*---------------------------------------ORPHANAGE SIGN UP FORM VALIDATAION-----------------------------------*/

function orpCheckDetails(status) {
	if (status === "true") {
		return true;
	}
	var flag = 0;
	var count = 0;
	var orpName = getValue("orpName");
	var orpPh = getValue("orpPhoneNumber");
	var orpAltNum = getValue("orpAltNumber");
	var orpAdd = getValue("orpAddress");
	var orpCity = getValue("orpCity");
	var orpState = getValue("orpState");
	var orpZip = getValue("orpZip");
	var orpWebsite = getValue("orpWebsite");
	var orpPass = getValue("orpPass");
	var orpCheckPass = getValue("orpCheckPass");
	(checkState(orpName) === false || orpName === "" || orpName === null) ? dispError(
			"orpname-error", userError, 5)
			: count++;

	if (checkPhoneNumber(orpPh) === false) {
		dispError("orpnumber-error", phoneNumberError, 0);
	} else if (checkPhoneNumber(orpPh) === true) {
		count++;
		if (orpAltNum !== "" && orpAltNum !== null) {
			if (checkPhoneNumber(orpAltNum) === false) {
				dispError("orpaltnum-error", phoneNumberError, 0);
				flag = 1;
			} else if (checkPhoneNumber(orpAltNum) === true) {
				if (orpPh === orpAltNum) {
					dispError("orpnumber-error", phoneNumberError, 1);
					dispError("orpaltnum-error", phoneNumberError, 1);
				} else if (orpPh !== orpAltNum) {
					count++;
				}
			}
		}
	}
	(checkAddress(orpAdd) === false) ? dispError("orpaddress-error",
			addressError, 0) : count++;

	(checkState(orpCity) === false || orpCity === "" || orpCity === null) ? dispError(
			"orpcity-error", addressError, 1)
			: count++;

	(checkState(orpState) === false || orpState === "" || orpState === null) ? dispError(
			"orpstate-error", addressError, 2)
			: count++;

	(checkZip(orpZip) === false) ? dispError("orpzip-error", addressError, 3)
			: count++;

	if (orpWebsite !== "" && orpWebsite !== null) {
		(checkWebsite(orpWebsite) === false) ? dispError("orpwebsite-error",
				websiteError, 0) : count++;
	}

	if (checkEmail(orpEmailId) === false) {
		dispError("orpemail-error", userError, 0);
	}

	if (checkField(orpPass) === false) {
		dispError("orppass-error", passError, 0);
	} else if (checkField(orpPass) === true) {
		if (checkPasswordLength(orpPass) === true) {
			count++;
			if (checkField(orpCheckPass) === false) {
				dispError("orpcheckpass-error", passError, 0);
			} else if (checkField(orpCheckPass) === true) {
				if (orpPass === orpCheckPass) {
					count++;
				} else if (orpPass !== orpCheckPass) {
					dispError("orppass-error", passError, 2);
					dispError("orpcheckpass-error", passError, 2);
				}
			}
		} else {
			dispError("orppass-error", passError, 3);
		}
	}
	// console.log(document.getElementById("orpemail-error").innerHTML);
	console.log(flag + " " + count);
	if ((count === 10 && flag === 1) || (count === 9 && flag === 1)
			|| (count === 8 && flag === 0) || (count === 9 && flag === 0)) {
		if (checkEmail(email)) {
			var ajax;
			if (XMLHttpRequest) {
				ajax = new XMLHttpRequest();
			} else {
				ajax = new ActiveXobject("Microsoft.XHTTP");
			}
			ajax.open("post", "availability");
			ajax.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			ajax.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					if (this.responseText === "available") {
						console.log(this.responseText);
						var parameter = "orpName=" + orpName + "&orpPhone="
								+ orpPh + "&orpAltNum=" + orpAltNum
								+ "&orpAddress=" + orpAdd + "&orpCity="
								+ orpCity + "&orpState=" + orpState
								+ "&orpZip=" + orpZip + "&orpWebsite="
								+ orpWebsite + "&orpEmail=" + orpEmailId
								+ "&orpPassword=" + orpPass;
						ajaxRequest(redirectSignUp, "osignup", "post",
								parameters);
					} else if (this.responseText === "already exists") {
						dispError(errorTag, emailStatus, 1);
					}
				}
			}
		}

	}
	// console.log(status);
	return false;
}

function redirectSignUp(message) {
	console.log(message);
	if (message === "error") {
		alert("Server problem please try after sometimes");
	} else if (message === "success") {
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
	case "altnum-error":
	case "address-error":
	case "city-error":
	case "state-error":
	case "zip-error":
	case "email-error":
	case "pass-error":
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
		document.getElementById(idName).innerHTML = "";
		break;
	}
}

/* onkeyup functions */

/*---------------------------------------CONDITION CHECKERS-----------------------------------*/

function orpNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 0);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function firstNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 30) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 0);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function lastNameLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 30) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 0);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function addressLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 255) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 1);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function cityLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 2);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}

function stateLength(idName, errorTag) {
	var x = getValue(idName);
	if (x.length > 50) {
		x = x.slice(0, x.length - 1);
		dispError(errorTag, lengthError, 2);
	} else {
		document.getElementById(errorTag).innerHTML = '';
	}
}