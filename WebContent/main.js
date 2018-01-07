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

var nameReg = /^[a-zA-Z]+$/;
var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var phoneRegex = /^[0-9]{10}$/;
var addRegex = /^[0-9a-z A-Z-,'/ / ]+$/;
var zipRegex = /^[0-9]{6}$/;
var stateReg = /^[a-zA-Z ]*$/;
var webReg = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;

function login() {
	location.href = "login.html";
}

function getValue(idName) {
	return document.getElementById(idName).value.trim();
}

function dispError(idName, arrName, index) {
	// console.log(idName);
	document.getElementById(idName).innerHTML = arrName[index];
}

function validateLogin() {
	var status = false;
	var username = getValue("user-email");
	var password = getValue("user-password");
	(checkEmail(username) === false) ? dispError("username-error", userError, 0)
			: status = false;
	// alert("UserName Cannot be Empty");
	(checkField(password) === false) ? dispError("password-error", passError, 0)
			: status = false;
	// alert("Password cannot be Empty");
	(checkEmail(username) === true && checkField(password) === false) ? dispError(
			"password-error", passError, 1)
			: status = false;
	if (checkEmail(username) && checkField(password)) {
		var ajax;
		if (XMLHttpRequest) {
			ajax = new XMLHttpRequest();
		} else {
			ajax = new ActiveXobject("Microsoft.XMLHTTP");
		}
		ajax.open("post", "login");
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				if (this.responseText === "invalid user") {
					dispError("username-error", userError, 3);
					status = false;
				} else if (this.responseText === "account not activated") {
					dispErrot("username-error", userError, 4);
					status = false;
				} else if (this.responseText === "success") {
					status = true;
				}

			}
		}
		ajax.send("email=" + username + "&pass=" + password);
	}
	return false;
}

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
	(checkEmail(emailId) === false) ? dispError("email-error", userError, 0)
			: count++;
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
	} else {
		count++;
	}
	if ((flag === 1 && count === 13) || (flag === 0 && count === 12)) {
		return true;
	}
	return false;

}

function orpCheckDetails() {
	var status = false;
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
	var orpEmailId = getValue("orpEmailId");
	var orpPass = getValue("orpPass");
	var orpCheckPass = getValue("orpCheckPass");
	(checkState(orpName) === false || orpName === "" || orpName === null) ? dispError(
			"orpname-error", userError, 5)
			: count++;
	(checkPhoneNumber(orpPh) === false) ? dispError("orpnumber-error",
			phoneNumberError, 0) : count++;
	if (orpAltNum !== null && orpAltNum !== "") {
		(checkPhoneNumber(orpAltNum) === false) ? dispError("orpaltnum-error",
				phoneNumberError, 0) : flag = 1;
		count++;
	}
	if (orpPh === orpAltNum && (orpPh !== "" && orpPh !== null)
			&& (orpAltNum !== "" && orpAltNum !== null)) {
		dispError("orpnumber-error", phoneNumberError, 1);
		dispError("orpaltnum-error", phoneNumberError, 1);
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
	(checkEmail(orpEmailId) === false) ? dispError("orpemail-error", userError,
			0) : count++;
	(checkField(orpPass) === false) ? dispError("orppass-error", passError, 0)
			: count++;
	(checkField(orpCheckPass) === false) ? dispError("orpcheckpass-error",
			passError, 0) : count++;
	(checkPasswordLength(orpPass) === false) ? dispError("orppass-error",
			passError, 3) : count++;
	if (checkField(orpPass) && checkField(orpCheckPass)
			&& orpPass !== orpCheckPass) {
		dispError("orppass-error", passError, 2);
		dispError("orpcheckpass-error", passError, 2);
		count -= 2;
	} else {
		count++;
	}
	//console.log(flag + " " + count);
	if ((count === 13 && flag === 1) || (count === 12 && flag === 1)
			|| (flag === 0 && count === 12) || (count === 11 && flag === 0)) {
		status = true;
	}
	return status;
}

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