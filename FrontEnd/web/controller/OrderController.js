var orderTableTbody = $('#orderTable tbody');
var searchOrderId = $("#searchOrderId");
var searchIdPattern = /^((O-)[0-9]{3})|((C-)[0-9]{3})$/
var viewOrderDetailsBtn = $('#viewOrderDetailsBtn');
viewOrderDetailsBtn.prop('disabled', true);

searchOrderId.off('keyup');
searchOrderId.keyup(function () {
    if (searchIdPattern.test(searchOrderId.val())) {
        viewOrderDetailsBtn.prop('disabled', false);
        searchOrderId.css('border', '1px solid green');
        viewOrderDetailsBtn.off('click');
        viewOrderDetailsBtn.click(function () {
            searchOrders();
            // viewOrderDetailsBtn.prop('disabled', true);
        })
    } else {
        searchOrderId.css('border', '1px solid red');
        viewOrderDetailsBtn.prop('disabled', true);
    }
});

function setDataToOrderTable() {

    $.ajax({
        url: "http://localhost:8080/WebPosEE/order?option=getAll",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            let arr = resp;
            $('#orderTable tbody tr').remove();
            for (let i = 0; i < arr.length; i++) {
                if (resp[i].status == 200) {
                    let orderArray = arr[i];
                    let row = '<tr><td>' + (i + 1) + '</td><td>' + orderArray.orderId + '</td><td>' + orderArray.customerId + '</td><td>' + orderArray.orderDate + '</td><td>' + orderArray.orderTime + '</td><td>' + orderArray.discount + '</td><td>' + orderArray.total + '</td></tr>';
                    orderTableTbody.append(row);
                } else if (arr[i].status == 400) {
                    $('#orderTable tbody tr').remove();
                    return;
                } else {
                    $('#orderTable tbody tr').remove();
                    return;
                }
            }
        },
        error: function (ob, textStatus, error) {
            alert(error);
        }
    })
}

function searchOrders() {

    $.ajax({
        url: "http://localhost:8080/WebPosEE/order?option=searchOrder&orderId=" + searchOrderId.val(),
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (resp) {
            if (resp.status == 200) {
                let order = resp;
                let arr = new Array();
                let obj = {
                    orderId: order.orderDetails.orderId,
                    itemCode: order.orderDetails.itemCode,
                    itemDescription: order.orderDetails.itemDescription,
                    itemQty: order.orderDetails.itemQty,
                    itemPrice: order.orderDetails.itemPrice,
                    itemDiscount: order.orderDetails.itemDiscount,
                    total: order.orderDetails.total
                }
                arr.push(obj);
                setDataToOrderDetailsTable(arr);
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