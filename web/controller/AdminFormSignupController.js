var signUpAdminId = $('#signUpAdminId');
var signUpAdminName = $('#signUpAdminName');
var signUpAdminNic = $('#signUpAdminNic');
var signUpAdminContactNo = $('#signUpAdminContactNo');
var signUpAdminUserName = $('#signUpAdminUserName');
var signUpAdminPassword = $('#signUpAdminPassword');
var signUpAdminAddress = $('#signUpAdminAddress');

var signupAdminIdPattern = /^(A-)[0-9]{3}$/;
var signupAdminNamePattern = /^[A-z0-9]{3,}[ ]*[A-z]*$/;
var signupAdminNicPattern = /^([0-9]{9}[v])|([0-9]{12})$/;
var signupAdminContactNoPattern = /^[0-9]{10}$/;
var signupAdminUserNamePattern = /^([A-z]{3,}[.]*[A-z]*[0-9]*[@]?((gmail.com)|(yahoo.com)?))$/;
var signupAdminPasswordPattern = /^([A-z0-9]{3,}[.]*[A-z0-9]*[@]?)$/;
var signupAdminAddressPattern = /^[A-z0-9,/ ]*[.]?$/;

var addAdminSignUpDetailsBtn = $('#addAdminSignupDetailsBtn');
var updateAdminSignUpDetailsBtn = $('#updateAdminSignupDetailsBtn');
var deleteAdminSignUpDetailsBtn = $('#deleteAdminSignupDetailsBtn');

var signUpAdminIdLbl = $('#signUpAdminIdLbl span');
var signUpAdminNameLbl = $('#signUpAdminNameLbl span');
var signUpAdminNicLbl = $('#signUpAdminNicLbl span');
var signUpAdminContactNoLbl = $('#signUpAdminContactNoLbl span');
var signUpAdminUserNameLbl = $('#signUpAdminUserNameLbl span');
var signUpAdminPasswordLbl = $('#signUpAdminPasswordLbl span');
var signUpAdminAddressLbl = $('#signUpAdminAddressLbl span');


var fieldsArrayInAdmin = [signUpAdminId, signUpAdminName, signUpAdminNic, signUpAdminContactNo, signUpAdminUserName, signUpAdminPassword, signUpAdminAddress];

var checkIfTrueOrFalseReturnedFromSearchAdminDetails;
var checkIfAdminExists;


$('#signUpAdminId,#signUpAdminName,#signUpAdminNic,#signUpAdminContactNo,#signUpAdminUserName,#signUpAdminPassword,#signUpAdminAddress').off('keydown');
$('#signUpAdminId,#signUpAdminName,#signUpAdminNic,#signUpAdminContactNo,#signUpAdminUserName,#signUpAdminPassword,#signUpAdminAddress').keydown(function (e) {
    if (e.key == 'Tab') {
        e.preventDefault();
    }
});

generateAdminId();

signUpAdminId.off('keyup');
signUpAdminId.keyup(function (e) {
    let index = 0;
    if (validate(signupAdminIdPattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminIdLbl.text('Id')
        if (e.key == 'Enter') {
            searchAdminSignupDetails();
        }
    } else {
        signUpAdminIdLbl.text('Please enter a valid Id. Hint (A-001)');
        signUpAdminIdLbl.css('font-size', '80%');
    }
})

signUpAdminName.off('keyup');
signUpAdminName.keyup(function (e) {
    let index = 1;
    if (validate(signupAdminNamePattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminNameLbl.text('Name')
    } else {
        signUpAdminNameLbl.text('Please enter a valid Name.');
        signUpAdminNameLbl.css('font-size', '80%');
    }
})

signUpAdminNic.off('keyup');
signUpAdminNic.keyup(function (e) {
    let index = 2;
    if (validate(signupAdminNicPattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminNicLbl.text('Nic')
    } else {
        signUpAdminNicLbl.text('Please enter a valid Nic no');
        signUpAdminNicLbl.css('font-size', '80%');
    }
})

signUpAdminContactNo.off('keyup');
signUpAdminContactNo.keyup(function (e) {
    let index = 3;
    if (validate(signupAdminContactNoPattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminContactNoLbl.text('Contact No')
    } else {
        signUpAdminContactNoLbl.text('Please enter a valid contact number.');
        signUpAdminContactNoLbl.css('font-size', '80%');
    }
})

signUpAdminUserName.off('keyup');
signUpAdminUserName.keyup(function (e) {
    let index = 4;
    if (validate(signupAdminUserNamePattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminUserNameLbl.text('User Name')
    } else {
        signUpAdminUserNameLbl.text('Please enter a valid User Name.');
        signUpAdminUserNameLbl.css('font-size', '80%');
    }
})

signUpAdminPassword.off('keyup');
signUpAdminPassword.keyup(function (e) {
    let index = 5;
    if (validate(signupAdminPasswordPattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminPasswordLbl.text('Password')
    } else {
        signUpAdminPasswordLbl.text('Please enter a valid Password.');
        signUpAdminPasswordLbl.css('font-size', '80%');
    }
})

signUpAdminAddress.off('keyup');
signUpAdminAddress.keyup(function (e) {
    let index = 6;
    if (validate(signupAdminAddressPattern, fieldsArrayInAdmin, index, e, addAdminSignUpDetailsBtn, updateAdminSignUpDetailsBtn, deleteAdminSignUpDetailsBtn) == true) {
        signUpAdminAddressLbl.text('Address')
    } else {
        signUpAdminAddressLbl.text('Please enter a valid Address.');
        signUpAdminAddressLbl.css('font-size', '80%');
    }
})

addAdminSignUpDetailsBtn.off('click');
addAdminSignUpDetailsBtn.click(function () {
    var signUpDetails = {
        id: signUpAdminId.val(),
        name: signUpAdminName.val(),
        nic: signUpAdminNic.val(),
        contactNo: signUpAdminContactNo.val(),
        userName: signUpAdminUserName.val(),
        password: signUpAdminPassword.val(),
        address: signUpAdminAddress.val()
    }
    if (confirm('Do you want to save this admin details') == true) {
        if (checkIfTrueOrFalseReturnedFromSearchAdminDetails == true) {
            alert('This ' + signUpAdminId.val() + ' already exists');
            clearFieldsInAdminSignup();
            generateAdminId();
            setAdminInputBordersReset();
            return
        } else {
            $.ajax({
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(signUpDetails),
                url: "http://localhost:8080/WebPosEE/admin",
                success: function (resp) {
                    if (resp.status == 200) {
                        alert(resp.message);
                        setDataToTheAdminTable();
                        clearFieldsInAdminSignup();
                        setAdminInputBordersReset();
                        generateAdminId();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                    } else {
                        alert(resp.data);
                    }
                },
                error: function (ob, textstatus, error) {
                    alert(error);
                }
            })
        }
    } else {
        alert('Saving admin details of ' + signUpAdminId.val() + ' is unsuccessful');
        clearFieldsInAdminSignup();
        let id = generateAdminId();
        signUpAdminId.val(id);
        setAdminInputBordersReset();
        return;
    }
})
updateAdminSignUpDetailsBtn.off('click');
updateAdminSignUpDetailsBtn.click(function () {
    if (confirm('Do you want to update the admin details relevant to admin id - ' + signUpAdminId.val() + '') == true) {
        ifAdminExists();
        console.log(checkIfAdminExists + " - checkIf Admin exists")
        if (checkIfAdminExists == false) {
            alert('Entered admin details not exists')
        } else {
            console.log('else eke ine')
            let admin = {
                id: signUpAdminId.val(),
                name: signUpAdminName.val(),
                nic: signUpAdminNic.val(),
                contactNo: signUpAdminContactNo.val(),
                userName: signUpAdminUserName.val(),
                password: signUpAdminPassword.val(),
                address: signUpAdminAddress.val()
            }
            $.ajax({
                url: "http://localhost:8080/WebPosEE/admin",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(admin),
                method: "PUT",
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToTheAdminTable();
                        clearFieldsInAdminSignup();
                        setAdminInputBordersReset();
                        generateAdminId();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                    } else {
                        alert(resp.data);
                    }
                },
                error: function (ob, textstatus, error) {
                    alert(error)
                }
            })
        }
    } else {
        alert('Updating admin ' + signUpAdminId.val() + ' is unsuccessful');
        clearFieldsInAdminSignup();
        generateAdminId();
        /*signUpAdminId.val(id);*/
        setAdminInputBordersReset();
        return;
    }
})

deleteAdminSignUpDetailsBtn.off('click');
deleteAdminSignUpDetailsBtn.click(function () {
    if (confirm('Do you want to delete this admin details') == true) {
        $.ajax({
            url: "http://localhost:8080/WebPosEE/admin?adminID=" + signUpAdminId.val(),
            method: "DELETE",
            success: function (resp) {
                if (resp.status == 200) {
                    setDataToTheAdminTable();
                    clearFieldsInAdminSignup();
                    setAdminInputBordersReset();
                    generateAdminId();
                } else if (resp.status == 400) {
                    alert(resp.message);
                } else {
                    alert(resp.data);
                }
            },
            error: function (ob, textstatus, error) {
                alert(error)
            }
        })
    } else {
        clearFieldsInAdminSignup();
        setAdminInputBordersReset();
        generateAdminId();
    }
})

function setDataToCheckAdminExists(resp) {
    checkIfAdminExists = resp;
}

function ifAdminExists() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/admin?option=checkIfAdminExists&adminID=" + signUpAdminId.val(),
        dataType: "json",
        contentType: "application/json",
        method: "GET",
        success: function (resp) {
            if (resp.status == 200) {
                console.log(resp.bool);
                setDataToCheckAdminExists(resp.bool)
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        }
    })
}

function checkIfTrueOrFalseReturnFromAjaxSearch(resp) {
    checkIfTrueOrFalseReturnedFromSearchAdminDetails = resp;
}

function searchAdminSignupDetails() {
    $.ajax({
        dataType: "json",
        contentType: "application/json",
        method: "GET",
        url: "http://localhost:8080/WebPosEE/admin?option=searchAdmin&adminId=" + signUpAdminId.val(),
        success: function (resp) {
            if (resp.status == 200) {
                let adminSearchOb = resp;
                console.log(adminSearchOb.adminNic)
                signUpAdminName.val(adminSearchOb.adminName);
                signUpAdminNic.val(adminSearchOb.adminNic);
                signUpAdminContactNo.val(adminSearchOb.adminContactNo);
                signUpAdminUserName.val(adminSearchOb.adminUserName);
                signUpAdminPassword.val(adminSearchOb.adminPassword);
                signUpAdminAddress.val(adminSearchOb.adminAddress);
                checkIfTrueOrFalseReturnFromAjaxSearch(true);
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textstatus, error) {
            console.log("error")
            alert(error);
            checkIfTrueOrFalseReturnFromAjaxSearch(false);
        }
    })
}

function clearFieldsInAdminSignup() {
    signUpAdminId.val("");
    signUpAdminName.val("");
    signUpAdminNic.val("");
    signUpAdminContactNo.val("");
    signUpAdminUserName.val("");
    signUpAdminPassword.val("");
    signUpAdminAddress.val("");
}

function generateAdminId() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/admin?option=generateAdminId",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                console.log("Log - " + resp.adminID);
                signUpAdminId.val(resp.adminID);
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textstatus, error) {
            alert(error);
        }
    });
}

function setDataToTheAdminTable() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/admin?option=getAll",
        dataType: "json",
        contentType: "application/json",
        method: "GET",
        success: function (resp) {
            adminDetailsTable.children('tbody').children('tr').remove();
            for (let i = 0; i < resp.length; i++) {
                console.log(resp[i].adminName)
                adminDetailsTable.children('tbody').append('<tr><td>' + (i + 1) + '</td><td>' + resp[i].adminId + '</td><td>' + resp[i].adminName + '</td><td>' + resp[i].adminNic + '</td><td>' + resp[i].adminContactNo + '</td><td>' + resp[i].adminUserName + '</td><td>' + resp[i].adminPassword + '</td><td>' + resp[i].adminAddress + '</td></tr>');
            }
        },
        error: function (ob, textstatus, error) {
            alert(error);
        }
    })
}

function setAdminInputBordersReset() {
    setBorderToDefault(fieldsArrayInAdmin);
}