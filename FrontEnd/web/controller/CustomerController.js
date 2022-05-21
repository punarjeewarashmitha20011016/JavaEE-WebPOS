var saveCustomer = $("#saveCustomer");
var updateCustomer = $('#cusUpdateBtn');
var deleteCustomer = $('#cusDeleteBtn');

var customerSearchBtn = $('#customerSearchBtn');
var cusId = $("#cusId");
var cusName = $("#cusName");
var cusContactNo = $("#cusContactNo");
var cusNic = $("#cusNic");
var cusAddress = $("#cusAddress");
var tblCus = $("#tblCus");
var tblCusBody = $("#tblCus tbody");
var cusTblRow = 1;

var cusIdPattern = /^(CU-)[0-9]{3}$/;
var cusNamePattern = /^[A-z ]+$/;
var cusContactPattern = /^[0-9]{10}$/;
var cusNicPattern = /^(([0-9]{9}[v]{1})|([0-9]{12}))$/;
var cusAddressPattern = /^[A-z0-9.,/ ]*$/

var cusInputsArr = [cusId, cusName, cusContactNo, cusNic, cusAddress];

var customerExistsInCustomer;

generateCustomerId();
$('#cusId,#cusName,#cusContactNo,#cusNic,#cusAddress').off('keydown');
$('#cusId,#cusName,#cusContactNo,#cusNic,#cusAddress').keydown(function (e) {
    if (e.key == 'Tab') {
        e.preventDefault();
    }
});
cusId.keyup(function (e) {
    console.log("cusId keyUp")
    let index = 0;
    var cusIdLbl = $("#cusIdLabelInCustomers span");
    if (validate(cusIdPattern, cusInputsArr, index, e, saveCustomer, updateCustomer, deleteCustomer) == true) {
        cusIdLbl.text("Id");
    } else {
        cusIdLbl.text("Please use the given format (CU-001)");
    }
})

cusName.keyup(function (e) {
    let index = 1;
    var cusNameLbl = $("#cusNameLabelInCustomers span");
    if (validate(cusNamePattern, cusInputsArr, index, e, saveCustomer, updateCustomer, deleteCustomer) == true) {
        cusNameLbl.css('font-size', 'unset');
        cusNameLbl.text("Name");
    } else {
        cusNameLbl.css('font-size', '12px');
        cusNameLbl.text("Please use the given format (Kamal Bandara)");
    }
})

cusContactNo.keyup(function (e) {
    let index = 2;
    var cusContactLbl = $("#cusContactLabelInCustomers span");
    if (validate(cusContactPattern, cusInputsArr, index, e, saveCustomer, updateCustomer, deleteCustomer) == true) {
        cusContactLbl.text("Contact No");
    } else {
        cusContactLbl.text("Please use only 10 digits");
    }
})

cusNic.keyup(function (e) {
    let index = 3;
    var cusNicLbl = $("#cusNicLabelInCustomers span");
    if (validate(cusNicPattern, cusInputsArr, index, e, saveCustomer, updateCustomer, deleteCustomer) == true) {
        cusNicLbl.text("Nic");
    } else {
        cusNicLbl.text("Please use only valid Nic numbers");
    }
})

cusAddress.keyup(function (e) {
    let index = 4;
    var cusAddressLbl = $("#cusAddressLabelInCustomers span");
    if (validate(cusAddressPattern, cusInputsArr, index, e, saveCustomer, updateCustomer, deleteCustomer) == true) {
        cusAddressLbl.text("Address");
    } else {
        cusAddressLbl.text("Please use only these special characters (.,/)");
    }
})

saveCustomer.off('click');
saveCustomer.click(function () {

    if (confirm("Do you want to save this customer") == true) {
        checkIfCustomerExists();
        if (customerExistsInCustomer == true) {
            alert('Customer details exists');
            clearFieldsInCustomer();
            generateCustomerId();
            setCustomerBordersReset();
        } else {
            let customer = {
                id: cusId.val(),
                name: cusName.val(),
                contactNo: cusContactNo.val(),
                nic: cusNic.val(),
                address: cusAddress.val()
            }
            $.ajax({
                url: "http://localhost:8080/WebPosEE/customer",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(customer),
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToCustomerTable();
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else {
                        alert(resp.data);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    }
                },
                error: function (ob, textStatus, error) {
                    alert(error);
                    clearFieldsInCustomer();
                    generateCustomerId();
                    setCustomerBordersReset();
                }
            })
        }
    } else {
        alert('Adding customer unsuccessful');
        clearFieldsInCustomer();
        generateCustomerId();
        setCustomerBordersReset();
    }
});

function setDataToCustomerTable() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/customer?option=getAll",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                let arr = resp;
                $("#tblCus tbody tr").remove();
                for (let i = 0; i < arr.length; i++) {
                    tblCusBody.append("<tr><td>" + (i + 1) + "</td><td>" + arr[i].id + "</td><td>" + arr[i].name + "</td><td>" + arr[i].nic + "</td><td>" + arr[i].contactNo + "</td><td>" + arr[i].address + "</td></tr>");
                }
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}

updateCustomer.off('click');
updateCustomer.click(function () {
    if (confirm("Do you want to update this customer") == true) {
        checkIfCustomerExists();
        if (customerExistsInCustomer == false) {
            alert('customer doesnt exists')
            clearFieldsInCustomer();
            generateCustomerId();
            setCustomerBordersReset();
        } else {
            let customer = {
                id: cusId.val(),
                name: cusName.val(),
                contactNo: cusContactNo.val(),
                nic: cusNic.val(),
                address: cusAddress.val()
            }

            $.ajax({
                url: "http://localhost:8080/WebPosEE/customer",
                method: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(customer),
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToCustomerTable();
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else {
                        alert(resp.data);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    }
                },
                error: function (ob, textStatus, error) {
                    alert(error);
                    clearFieldsInCustomer();
                    generateCustomerId();
                    setCustomerBordersReset();
                }
            })
        }
    } else {
        alert("Updating customer is unsuccessful");
        clearFieldsInCustomer();
        generateCustomerId();
        setCustomerBordersReset();
    }
});

deleteCustomer.off('click');
deleteCustomer.click(function () {


    if (confirm("Do you want to delete this customer") == true) {
        checkIfCustomerExists();
        if (customerExistsInCustomer == false) {
            alert("customer doesnt exists");
            clearFieldsInCustomer();
            generateCustomerId();
            setCustomerBordersReset();
        } else {
            $.ajax({
                url: "http://localhost:8080/WebPosEE/customer",
                method: 'DELETE',
                dataType: 'json',
                contentType: "application/json",
                success: function (resp) {
                    if (resp.status == 200) {
                        setDataToCustomerTable();
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    } else {
                        alert(resp.data);
                        clearFieldsInCustomer();
                        generateCustomerId();
                        setCustomerBordersReset();
                    }
                },
                error: function (ob, textStatus, error) {
                    alert(error);
                    clearFieldsInCustomer();
                    generateCustomerId();
                    setCustomerBordersReset();
                }
            })
        }
    } else {
        alert('Deleting customer is unsuccessful');
        clearFieldsInCustomer();
        generateCustomerId();
        setCustomerBordersReset();
    }
});

cusId.keydown(function (e) {
    if (e.key == 'Enter') {
        searchCustomer();
    }
})
customerSearchBtn.off('click')
customerSearchBtn.click(function () {
    searchCustomer();
});

function searchCustomer() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/customer?option=searchCustomer&customerId=" + cusId.val(),
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                cusId.val(resp.id);
                cusName.val(resp.name);
                cusNic.val(resp.nic);
                cusContactNo.val(resp.contactNo);
                cusAddress.val(resp.address);
            } else if (resp.status == 400) {
                alert(resp.message);
                clearFieldsInCustomer();
                generateCustomerId();
                setCustomerBordersReset();
            } else {
                alert(resp.data);
                clearFieldsInCustomer();
                generateCustomerId();
                setCustomerBordersReset();
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
            clearFieldsInCustomer();
            generateCustomerId();
            setCustomerBordersReset();
        }
    })
}

function clearFieldsInCustomer() {
    cusId.val("");
    cusName.val("");
    cusContactNo.val("");
    cusNic.val("");
    cusAddress.val("");
}

function generateCustomerId() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/customer?option=generateCustomerId",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                cusId.val(resp.customerId);
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

function setCustomerBordersReset() {
    setBorderToDefault(cusInputsArr);
}

function setCustomerExistsCheckVariable(bool) {
    customerExistsInCustomer = bool;
}

function checkIfCustomerExists() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/customer?option=ifCustomerExists&customerId=" + cusId.val(),
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                setCustomerExistsCheckVariable(resp.bool);
            } else if (resp.status == 400) {
                setCustomerExistsCheckVariable(resp.bool);
            } else {
                alert(resp.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}