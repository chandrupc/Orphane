var userError = [ "Enter a valid email-id", "Enter a valid first-name",
		"Enter a valid last-name" ];
var passError = [ "Password cannot be empty", "Please enter the password",
		"Password mismatch", "Minimum 8 characters" ];
var phoneNumberError = [ "Please enter a 10 digit valid mobile number",
		"Alternate number is same as primary" ];
var addressError = [ "Please fill out the address", "Please enter valid city",
		"Please enter valid state name", " Please enter valid zipcode" ];

var nameReg = /^[a-zA-Z]+$/;
var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var phoneRegex = /^[0-9]{10}$/;
var addRegex = /^[0-9a-z A-Z-,'/ / ]+$/;
var zipRegex = /^[0-9]{6}$/;
var stateReg = /^[a-zA-Z ]*$/;

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
		status = true;
	}
	return status;
}

function checkDetails() {
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
	(checkState(city) === false) ? dispError("city-error", addressError, 1)
			: count++;
	console.log(checkState(state));
	(checkState(state) === false) ? dispError("state-error", addressError, 2)
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
		document.getElementById(idName).innerHTML = "";
		break;
	}
}

/* onkeyup functions */

function validatePhoneNumber() {
	var x = document.getElementById("phoneNumber");
	if (!(/^[0-9]+$/.test(x.value))) {
		x.value = x.value.slice(0, x.value.length - 1);
	}
}

function validateAlternatePhoneNumber() {
	var x = document.getElementById("altNumber");
	if (!(/^[0-9]+$/.test(x.value))) {
		x.value = x.value.slice(0, x.value.length - 1);
	}
}

function firstNameLength() {
	var x = document.getElementById('firstName');
	if (x.value.length > 30) {
		x.value = x.value.slice(0, x.value.length - 1);
		document.getElementById('fname-error').innerHTML = 'Maximum 30 Characters';
	} else {
		document.getElementById('fname-error').innerHTML = '';
	}
}

function lastNameLength() {
	var x = document.getElementById('lastName');
	if (x.value.length > 30) {
		x.value = x.value.slice(0, x.value.length - 1);
		document.getElementById('lname-error').innerHTML = 'Maximum 30 Characters';
	} else {
		document.getElementById('lname-error').innerHTML = '';
	}
}

function addressLength() {
	var x = document.getElementById('address');
	if (x.value.length > 255) {
		x.value = x.value.slice(0, x.value.length - 1);
		document.getElementById('address-error').innerHTML = 'Maximum 255 Characters';
	} else {
		document.getElementById('address-error').innerHTML = '';
	}
}

function cityLength() {
	var x = document.getElementById('city');
	if (x.value.length > 15) {
		x.value = x.value.slice(0, x.value.length - 1);
		document.getElementById('city-error').innerHTML = 'Maximum 15 Characters';
	} else {
		document.getElementById('city-error').innerHTML = '';
	}
}

function stateLength() {
	var x = document.getElementById('state');
	if (x.value.length > 15) {
		x.value = x.value.slice(0, x.value.length - 1);
		document.getElementById('state-error').innerHTML = 'Maximum 15 Characters';
	} else {
		document.getElementById('state-error').innerHTML = '';
	}
}