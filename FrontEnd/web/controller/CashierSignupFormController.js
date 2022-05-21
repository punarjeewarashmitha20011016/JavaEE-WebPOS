var signUpId = $('#signUpId');
var signUpName = $('#signUpName');
var signUpNic = $('#signUpNic');
var signUpContactNo = $('#signUpContactNo');
var signUpUserName = $('#signUpUserName');
var signUpPassword = $('#signUpPassword');
var signUpAddress = $('#signUpAddress');

var signupIdPattern = /^(C-)[0-9]{3}$/;
var signupNamePattern = /^[A-z0-9]{3,}[ ]*[A-z]*$/;
var signupNicPattern = /^([0-9]{9}[v])|([0-9]{12})$/;
var signupContactNoPattern = /^[0-9]{10}$/;
var signupUserNamePattern = /^([A-z]{3,}[.]*[A-z]*[0-9]*[@]?((gmail.com)|(yahoo.com)?))$/;
var signupPasswordPattern = /^([A-z0-9]{3,}[.]*[A-z0-9]*[@]?)$/;
var signupAddressPattern = /^[A-z0-9,/ ]*[.]?$/;

var addSignUpDetailsBtn = $('#addSignupDetailsBtn');
var updateSignUpDetailsBtn = $('#updateSignupDetailsBtn');
var deleteSignUpDetailsBtn = $('#deleteSignupDetailsBtn');

var signUpIdLbl = $('#signUpIdLbl span');
var signUpNameLbl = $('#signUpNameLbl span');
var signUpNicLbl = $('#signUpNicLbl span');
var signUpContactNoLbl = $('#signUpContactNoLbl span');
var signUpUserNameLbl = $('#signUpUserNameLbl span');
var signUpPasswordLbl = $('#signUpPasswordLbl span');
var signUpAddressLbl = $('#signUpAddressLbl span');

var cashierDetailsTable = $('#cashierDetailsTable');


var fieldsArray = [signUpId, signUpName, signUpNic, signUpContactNo, signUpUserName, signUpPassword, signUpAddress];
var checkIfCashierExists;
console.log("init check bool - " + checkIfCashierExists)


$('#signUpId,#signUpName,#signUpNic,#signUpContactNo,#signUpUserName,#signUpPassword,#signUpAddress').off('keydown');
$('#signUpId,#signUpName,#signUpNic,#signUpContactNo,#signUpUserName,#signUpPassword,#signUpAddress').keydown(function (e) {
    if (e.key == 'Tab') {
        e.preventDefault();
    }
});

generateId();

signUpId.off('keyup');
signUpId.keyup(function (e) {
    let index = 0;
    if (validate(signupIdPattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpIdLbl.text('Id')
        if (e.key == 'Enter') {
            searchSignupDetails();
        }
    } else {
        signUpIdLbl.text('Please enter a valid Id. Hint (C-001)');
        signUpIdLbl.css('font-size', '80%');
    }
})

signUpName.off('keyup');
signUpName.keyup(function (e) {
    let index = 1;
    if (validate(signupNamePattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpNameLbl.text('Name')
    } else {
        signUpNameLbl.text('Please enter a valid Name.');
        signUpNameLbl.css('font-size', '80%');
    }
})

signUpNic.off('keyup');
signUpNic.keyup(function (e) {
    let index = 2;
    if (validate(signupNicPattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpNicLbl.text('Nic')
    } else {
        signUpNicLbl.text('Please enter a valid Nic no');
        signUpNicLbl.css('font-size', '80%');
    }
})

signUpContactNo.off('keyup');
signUpContactNo.keyup(function (e) {
    let index = 3;
    if (validate(signupContactNoPattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpContactNoLbl.text('Contact No')
    } else {
        signUpContactNoLbl.text('Please enter a valid contact number.');
        signUpContactNoLbl.css('font-size', '80%');
    }
})

signUpUserName.off('keyup');
signUpUserName.keyup(function (e) {
    let index = 4;
    if (validate(signupUserNamePattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpUserNameLbl.text('User Name')
    } else {
        signUpUserNameLbl.text('Please enter a valid User Name.');
        signUpUserNameLbl.css('font-size', '80%');
    }
})

signUpPassword.off('keyup');
signUpPassword.keyup(function (e) {
    let index = 5;
    if (validate(signupPasswordPattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpPasswordLbl.text('Password')
    } else {
        signUpPasswordLbl.text('Please enter a valid Password.');
        signUpPasswordLbl.css('font-size', '80%');
    }
})

signUpAddress.off('keyup');
signUpAddress.keyup(function (e) {
    let index = 6;
    if (validate(signupAddressPattern, fieldsArray, index, e, addSignUpDetailsBtn, updateSignUpDetailsBtn, deleteSignUpDetailsBtn) == true) {
        signUpAddressLbl.text('Address')
    } else {
        signUpAddressLbl.text('Please enter a valid Address.');
        signUpAddressLbl.css('font-size', '80%');
    }
})

function setCheckIfCashierExistsVariable(bool) {
    checkIfCashierExists = bool;
}

function ifCashierExists() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/cashier?option=ifCashierExists&cashierId=" + signUpId.val(),
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                setCheckIfCashierExistsVariable(resp.bool);
            } else if (resp.status == 400) {
                console.log("response in check - " + resp.bool)
                setCheckIfCashierExistsVariable(resp.bool);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}

addSignUpDetailsBtn.off('click');
addSignUpDetailsBtn.click(function () {
    let cashierDetails = {
        id: signUpId.val(),
        name: signUpName.val(),
        nic: signUpNic.val(),
        contactNo: signUpContactNo.val(),
        userName: signUpUserName.val(),
        password: signUpPassword.val(),
        address: signUpAddress.val()
    }

    if (confirm("Do you want to add this details. If yes please enter Ok button") == true) {
        ifCashierExists();
        console.log("check if cashier exists in post method - " + checkIfCashierExists)
        if (checkIfCashierExists == false) {
            alert('Cashier exists');
            clearFieldsInSignup()
            generateId();
            setCashierInputBordersReset();
            return;
        } else {
            $.ajax({
                url: "http://localhost:8080/WebPosEE/cashier",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(cashierDetails),
                success: function (resp) {
                    if (resp.status == 200) {
                        alert(resp.message);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else {
                        alert(resp.data);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    }
                },
                error: function (ob, textstatus, error) {
                    alert(error);
                    setDataToTheCashierTable();
                    clearFieldsInSignup();
                    generateId();
                    setCashierInputBordersReset();
                }
            })
        }
    } else {
        alert('Adding signup details for signup Id ' + signUpId.val() + ' is unsuccessful.');
        clearFieldsInSignup()
        generateId();
        setCashierInputBordersReset();
    }
})

function searchSignupDetails() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/cashier?option=searchCashier&cashierId=" + signUpId.val(),
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                signUpId.val(resp.id);
                signUpName.val(resp.name);
                signUpNic.val(resp.nic);
                signUpContactNo.val(resp.contactNo);
                signUpUserName.val(resp.userName);
                signUpPassword.val(resp.password);
                signUpAddress.val(resp.address);
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}

function clearFieldsInSignup() {
    signUpId.val("");
    signUpName.val("");
    signUpNic.val("");
    signUpContactNo.val("");
    signUpUserName.val("");
    signUpPassword.val("");
    signUpAddress.val("");
}

updateSignUpDetailsBtn.off('click');
updateSignUpDetailsBtn.click(function () {
    let cashierDetails = {
        id: signUpId.val(),
        name: signUpName.val(),
        nic: signUpNic.val(),
        contactNo: signUpContactNo.val(),
        userName: signUpUserName.val(),
        password: signUpPassword.val(),
        address: signUpAddress.val()
    }

    if (confirm("Do you want to update this admin details. If yes please enter Ok button") == true) {
        ifCashierExists();
        if (checkIfCashierExists == false) {
            alert('Cashier not exists');
            clearFieldsInSignup()
            generateId();
            setCashierInputBordersReset();
        } else {
            $.ajax({
                url: "http://localhost:8080/WebPosEE/cashier",
                method: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(cashierDetails),
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else {
                        alert(resp.data);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    }
                },
                error: function (ob, textstatus, error) {
                    alert(error);
                    setDataToTheCashierTable();
                    clearFieldsInSignup();
                    generateId();
                    setCashierInputBordersReset();
                }
            })
        }
    } else {
        alert('Updating signup details for signup Id ' + signUpId.val() + ' is unsuccessful.');
        clearFieldsInSignup()
        generateId();
        setCashierInputBordersReset();
    }
})

deleteSignUpDetailsBtn.off('click');
deleteSignUpDetailsBtn.click(function () {
    if (confirm("Do you want to delete this admin. If yes please enter Ok button") == true) {
        ifCashierExists();
        if (checkIfCashierExists == false) {
            alert('Cashier not exists');
            clearFieldsInSignup()
            generateId();
            setCashierInputBordersReset();
        } else {
            console.log('delete method invoked with true cashier check')
            $.ajax({
                url: "http://localhost:8080/WebPosEE/cashier?cashierId=" + signUpId.val(),
                method: "DELETE",
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    } else {
                        alert(resp.data);
                        setDataToTheCashierTable();
                        clearFieldsInSignup();
                        generateId();
                        setCashierInputBordersReset();
                    }
                },
                error: function (ob, textstatus, error) {
                    alert(error);
                    setDataToTheCashierTable();
                    clearFieldsInSignup();
                    generateId();
                    setCashierInputBordersReset();
                }
            })
        }
    } else {
        alert('Deleting signup details for signup Id ' + signUpId.val() + ' is unsuccessful.');
        clearFieldsInSignup()
        generateId();
        setCashierInputBordersReset();
    }
})

function generateId() {
    console.log("Generate cashier id function")
    $.ajax({
        url: "http://localhost:8080/WebPosEE/cashier?option=generateCashierId",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                console.log(resp.cashierId);
                signUpId.val(resp.cashierId);
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}

function setDataToTheCashierTable() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/cashier?option=getAll",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                let arr = resp;
                cashierDetailsTable.children('tbody').children('tr').remove();
                for (let i = 0; i < arr.length; i++) {
                    cashierDetailsTable.children('tbody').append('<tr><td>' + (i + 1) + '</td><td>' + arr[i].id + '</td><td>' + arr[i].name + '</td><td>' + arr[i].nic + '</td><td>' + arr[i].contactNo + '</td><td>' + arr[i].userName + '</td><td>' + arr[i].password + '</td><td>' + arr[i].address + '</td></tr>');
                }
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        }
    })
}

function setBorderToDefault() {
    signUpId.css("border", "1px solid #ced4da");
    signUpName.css("border", "1px solid #ced4da");
    signUpNic.css("border", "1px solid #ced4da");
    signUpContactNo.css("border", "1px solid #ced4da");
    signUpUserName.css("border", "1px solid #ced4da");
    signUpPassword.css("border", "1px solid #ced4da");
    signUpAddress.css("border", "1px solid #ced4da");
}

function setCashierInputBordersReset() {
    setBorderToDefault(fieldsArray);
}