function newProduct(){
var productRegister = document.getElementById("product_registration_form");
var table = document.createElement("table");
table.setAttribute("class","darkTable");

var row = document.createElement("tr");
var nameTd = document.createElement("td");
nameTd.innerHTML = `<input id="product_name_input" placeholder="Termék neve">`
var qtyTd = document.createElement("td");
qtyTd.innerHTML =  `<input id="product_qty_input" placeholder="Termék mennyisége">`
var buttonTd = document.createElement("td");
buttonTd.innerHTML =  `<input type="button" id="save_product" onclick="saveProduct()" value="Mentés">`

row.appendChild(nameTd);
row.appendChild(qtyTd);
row.appendChild(buttonTd);
table.appendChild(row);
productRegister.appendChild(table)

}

function saveProduct(){
var productName = document.getElementById("product_name_input").value;
var productQty= document.getElementById("product_qty_input").value;
if(productName.trim=="" || productName.trim.length){
alert("Product name must not be empty or less than 3 characters long!");
return;
}
if(isNaN(productQty) || productQty<=0){
alert("Qty must be a number higher than 0");
return;
}
 var request = {
            "name":productName,
            "qty":productQty
        };
        fetch('/saveProduct', {
                method: "POST",
                body: JSON.stringify(request),
                headers:{
                "Content-type": "application/json"
                }
            })
            .then(function (response){
                return response.json();
            })
            .then(function (jsonData){
                alert(jsonData.message);
                fetchProducts();
            });
            return false;
        }

function fetchProducts(){
fetch("/getProducts")
.then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showProducts(jsonData);
    });
}
function showProducts(jsonData){
var table = document.getElementById("products_table");

for(var i = 1;i<table.rows.length;){
            table.deleteRow(i);
        }

for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement("tr");

    var nameTd = document.createElement("td");
    nameTd.innerHTML= jsonData[i].name;

    var skuTd = document.createElement("td");
    skuTd.innerHTML = jsonData[i].sku;

    var qtyTd = document.createElement("td");
    qtyTd.innerHTML = jsonData[i].qty;

     var statusTd = document.createElement("td");
     statusTd.innerHTML = jsonData[i].statusText;

      var timeTd = document.createElement("td");
      timeTd.innerHTML = jsonData[i].timestampFormatted;

      tr.appendChild(nameTd);
      tr.appendChild(skuTd);
      tr.appendChild(qtyTd);
      tr.appendChild(statusTd);
      tr.appendChild(timeTd);

      table.appendChild(tr);
}
document.getElementById("product_list_form").appendChild(table);
}