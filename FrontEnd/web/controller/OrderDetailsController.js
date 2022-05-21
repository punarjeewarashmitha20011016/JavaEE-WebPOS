var orderDetailsTableTbody = $('#orderDetailsTable tbody');

function setDataToOrderDetailsTable(obj) {
    $('#orderDetailsTable tbody tr').remove();
    for (let i = 0; i < obj.length; i++) {
        let row = '<tr><td>' + (i + 1) + '</td><td>' + obj[i].orderId + '</td><td>' + obj[i].itemCode + '</td><td>' + obj[i].itemDescription + '</td><td>' + obj[i].itemQty + '</td><td>' + obj[i].itemPrice + '</td><td>' + obj[i].itemDiscount + '</td><td>' + obj[i].total + '</td></tr>';
        orderDetailsTableTbody.append(row);
    }
}