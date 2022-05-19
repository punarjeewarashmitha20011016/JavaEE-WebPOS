var tBodyInItems = $(".ManageItems .container-fluid div:nth-child(3) div table tbody");
var rowNo = 1;

var itemCodeInItems = $("#itemCodeInItems");
var itemDescriptionInItems = $("#itemDescriptionInItems");
var itemQtyInItems = $("#itemQtyInItems");
var itemBuyingPriceInItems = $("#itemBuyingPriceInItems");
var itemUnitPriceInItems = $("#itemUnitPriceInItems");
var itemDiscountInItems = $("#itemDiscountInItems");
var saveItemBtn = $("#saveItems");
var updateItemBtn = $("#updateItemBtn");
var deleteItemBtn = $("#deleteItemBtn");
var searchItemBtn = $("#searchItemBtn");

var itemCodePattern = /^(I-)[0-9]{3}$/;
var itemDescriptionPattern = /^[0-9A-z ]+[.]*[/]*[(]*[)]*[']*[,]*[&]*[-]*$/;
var itemQtyPattern = /^[0-9]+$/;
var itemBuyingPattern = /^[0-9.]{1,}$/;
var itemUnitPattern = /^[0-9.]{1,}$/;
var itemDiscountPattern = /^[0-9.]{1,}$/;

var itemsArray = [itemCodeInItems, itemDescriptionInItems, itemQtyInItems, itemBuyingPriceInItems, itemUnitPriceInItems, itemDiscountInItems];

itemCodeInItems.val(generateItemCode());

var checkIfItemExistsInItems;

$('#itemCodeInItems,#itemDescriptionInItems,#itemQtyInItems,#itemBuyingPriceInItems,#itemUnitPriceInItems,#itemDiscountInItems').off('keydown');
$('#itemCodeInItems,#itemDescriptionInItems,#itemQtyInItems,#itemBuyingPriceInItems,#itemUnitPriceInItems,#itemDiscountInItems').keydown(function (e) {
    if (e.key == 'Tab') {
        e.preventDefault();
    }
});

itemCodeInItems.keyup(function (e) {
    let index = 0;
    var itemCodeLbl = $("#itemCodeLblInItems span");
    if (validate(itemCodePattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemCodeLbl.text("Code");
    } else {
        itemCodeLbl.text("Please use the given format (I-001)");
    }
});

itemDescriptionInItems.keyup(function (e) {
    let index = 1;
    let itemDescriptionLbl = $("#itemDescriptionLblInItems span");
    if (validate(itemDescriptionPattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemDescriptionLbl.css('font-size', 'unset');
        itemDescriptionLbl.text("Description");
    } else {
        itemDescriptionLbl.css('font-size', '12px');
        itemDescriptionLbl.text("Please only use these special characters only (./()',&-)");
    }
})

itemQtyInItems.keyup(function (e) {
    let index = 2;
    let itemQtyLbl = $("#itemQtyLblInItems span")
    if (validate(itemQtyPattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemQtyLbl.text("Qty");
    } else {
        itemQtyLbl.text("Please use a whole number");
    }

})

itemBuyingPriceInItems.keyup(function (e) {
    let index = 3;
    let itemBuyingPriceLbl = $("#itemBuyingPriceLblInItems span")
    if (validate(itemBuyingPattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemBuyingPriceLbl.text("Buying Price");
    } else {
        itemBuyingPriceLbl.text("Please use the format (100.0 or 100)");
    }
})

itemUnitPriceInItems.keyup(function (e) {
    let index = 4;
    let itemUnitPriceLbl = $("#itemUnitPriceLblInItems span")
    if (validate(itemUnitPattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemUnitPriceLbl.text("Unit Price");
    } else {
        itemUnitPriceLbl.text("Please use the format (100.0 or 100)");
    }
})

itemDiscountInItems.keyup(function (e) {
    let index = 5;
    let itemDiscountLbl = $("#itemDiscountLblInItems span")
    if (validate(itemDiscountPattern, itemsArray, index, e, saveItemBtn, updateItemBtn, deleteItemBtn) == true) {
        itemDiscountLbl.text("Discount");
    } else {
        itemDiscountLbl.text("Please use the format (5.0 or 5)");
    }
})

saveItemBtn.off('click');
saveItemBtn.click(function () {
    let discountInItems = 0;
    if (itemDiscountInItems.length == 0) {
        discountInItems = 0;
    } else {
        discountInItems = itemDiscountInItems.val();
    }
    let item = {
        itemCode: itemCodeInItems.val(),
        itemDescription: itemDescriptionInItems.val(),
        itemQty: itemQtyInItems.val(),
        itemBuyingPrice: itemBuyingPriceInItems.val(),
        itemUnitPrice: itemUnitPriceInItems.val(),
        itemDiscount: discountInItems
    }

    if (confirm("Do you want to save this item" == true)) {
        checkIfItemExists();
        if (checkIfItemExistsInItems == false) {
            console.log(checkIfItemExistsInItems + " - check if item exists bool");
            $.ajax({
                url: "http://localhost:8080/WebPosEE/item",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(item),
                success: function (resp) {
                    if (resp.status == 200) {
                        setDatToTheItemTable();
                        setDataToItemComboBox();
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else {
                        alert(resp.data);
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    }
                },
                error: function (db, textStatus, error) {
                    alert(error)
                    clearFieldsInItems();
                    generateItemCode();
                    setBorderToResetInItem();
                }
            })
        } else {
            clearFieldsInItems();
            generateItemCode();
            setBorderToResetInItem();
        }
    }
});

function setDatToTheItemTable() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/item?option=getAll",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                let arr = resp;
                $(".ManageItems .container-fluid div:nth-child(3) div table tbody tr").remove();
                itemDetailsTable.children('tbody').children('tr').remove();
                for (let i = 0; i < arr.length; i++) {
                    tBodyInItems.append("<tr><td>" + (i + 1) + "</td><td>" + arr[i].itemCode() + "</td><td>" + arr[i].itemDescription() + "</td><td>" + arr[i].itemQty() + "</td><td>" + arr[i].itemBuyingPrice() + "</td><td>" + arr[i].itemUnitPrice() + "</td><td>" + arr[i].itemDiscount() + "</td></tr>");
                    itemDetailsTable.children('tbody').append("<tr><td>" + (i + 1) + "</td><td>" + arr[i].itemCode() + "</td><td>" + arr[i].itemDescription() + "</td><td>" + arr[i].itemQty() + "</td><td>" + arr[i].itemBuyingPrice() + "</td><td>" + arr[i].itemUnitPrice() + "</td><td>" + arr[i].itemDiscount() + "</td></tr>");
                }
            }
        },
        error: function (db, textStatus, error) {
            alert(error)
        }
    })
}

itemCodeInItems.keydown(function (e) {
    if (e.key == 'Enter') {
        searchItemDetails();
    }
})
searchItemBtn.off('click')
searchItemBtn.click(function () {
    searchItemDetails();
})

function searchItemDetails() {

    $.ajax({
        url: "http://localhost:8080/WebPosEE/item?option=searchItem&itemCode=" + itemCodeInItems.val(),
        dataType: "json",
        contentType: "application/json",
        method: "GET",
        success: function (resp) {
            if (resp.status == 200) {
                itemCodeInItems.val(resp.itemCode);
                itemDescriptionInItems.val(resp.itemDescription);
                itemQtyInItems.val(resp.itemQty);
                itemBuyingPriceInItems.val(resp.itemBuyingPrice);
                itemUnitPriceInItems.val(resp.itemUnitPrice);
                itemDiscountInItems.val(resp.itemDiscount);
            } else if (resp.status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (db, textStatus, error) {
            alert(error)
        }
    })
}

updateItemBtn.off('click');
updateItemBtn.click(function () {

    let item = {
        itemCode: itemCodeInItems.val(),
        itemDescription: itemDescriptionInItems.val(),
        itemQty: itemQtyInItems.val(),
        itemBuyingPrice: itemBuyingPriceInItems.val(),
        itemUnitPrice: itemUnitPriceInItems.val(),
        itemDiscount: itemDiscountInItems.val()
    }
    if (confirm("Do you want to update this item") == true) {
        checkIfItemExists()
        if (checkIfItemExistsInItems == false) {
            alert('This item code doesnt exists');
            clearFieldsInItems();
            generateItemCode();
            setBorderToResetInItem();
        } else {
            $.ajax({
                url: "http://localhost:8080/WebPosEE/item",
                method: "PUT",
                dataType: "json",
                data: JSON.stringify(item),
                contentType: "application/json",
                success: function (resp) {
                    if (resp.status == 200) {
                        setDatToTheItemTable();
                        setDataToItemComboBox();
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else {
                        alert(resp.data);
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    }
                },
                error: function (db, textStatus, error) {
                    alert(error)
                    clearFieldsInItems();
                    generateItemCode();
                    setBorderToResetInItem();
                }
            })
        }
    }
});

deleteItemBtn.off('click');
deleteItemBtn.click(function () {
    if (confirm("Do you want to delete this item") == true) {
        checkIfItemExists();
        if (checkIfItemExistsInItems == false) {
            alert("This item is not exists");
            clearFieldsInItems();
            generateItemCode();
            setBorderToResetInItem();
        } else {
            $.ajax({
                url: "http://localhost:8080/WebPosEE/item?itemCode=" + itemCodeInItems.val(),
                method: "DELETE",
                dataType: "json",
                contentType: 'application/json',
                success: function (resp) {
                    if (resp.status == 200) {
                        setDatToTheItemTable();
                        setDataToItemComboBox();
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else if (resp.status == 400) {
                        alert(resp.message);
                        setDatToTheItemTable();
                        setDataToItemComboBox();
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    } else {
                        alert(resp.data);
                        setDatToTheItemTable();
                        setDataToItemComboBox();
                        clearFieldsInItems();
                        generateItemCode();
                        setBorderToResetInItem();
                    }
                },
                error: function (ob, textStatus, error) {
                    alert(error);
                    setDatToTheItemTable();
                    clearFieldsInItems();
                    generateItemCode();
                    setBorderToResetInItem();
                }
            })
        }
    } else {
        alert("Deleting this item is unsuccessfull");
        clearFieldsInItems();
        generateItemCode();
        setBorderToResetInItem();
    }
})

function clearFieldsInItems() {
    itemCodeInItems.val("");
    itemDescriptionInItems.val("");
    itemQtyInItems.val("");
    itemBuyingPriceInItems.val("");
    itemUnitPriceInItems.val("");
    itemDiscountInItems.val("");
}

function generateItemCode() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/item?option=generateItemCode",
        method: "GET",
        success: function (resp) {
            if (resp.status == 200) {
                itemCodeInItems.val(resp.itemCode);
            } else if (status == 400) {
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (db, textStatus, error) {
            alert(error);
        }
    })
}

function setBorderToResetInItem() {
    setBorderToDefault(itemsArray);
}

function setBoolToVariableCheck(bool) {
    checkIfItemExistsInItems = bool;
}

function checkIfItemExists() {
    $.ajax({
        url: "http://localhost:8080/WebPosEE/item?option=ifItemExists&itemCode=" + itemCodeInItems.val(),
        method: "GET",
        success: function (resp) {
            if (resp.status == 200) {
                setBoolToVariableCheck(resp.bool);
            } else if (status == 400) {
                setBoolToVariableCheck(resp.bool);
                alert(resp.message);
            } else {
                alert(resp.data);
            }
        },
        error: function (db, textStatus, error) {
            alert(error);
        }
    })
}