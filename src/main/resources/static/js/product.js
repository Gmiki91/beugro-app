function newProduct() {
  var productRegister = document.getElementById("product_registration_form");
  var table = document.getElementById("product_register");
  table.setAttribute("class", "darkTable");
  table.innerHTML="";
  var row = document.createElement("tr");
  var nameTd = document.createElement("td");
  nameTd.innerHTML = `<input id="product_name_input" placeholder="Termék neve">`;
  var qtyTd = document.createElement("td");
  qtyTd.innerHTML = `<input id="product_qty_input" placeholder="Termék mennyisége">`;
  var buttonTd = document.createElement("td");
  buttonTd.innerHTML = `<input type="button" id="save_product" onclick="saveProduct()" value="Mentés">`;

  row.appendChild(nameTd);
  row.appendChild(qtyTd);
  row.appendChild(buttonTd);
  table.appendChild(row);
}

function saveProduct() {
  var productName = document.getElementById("product_name_input").value;
  var productQty = document.getElementById("product_qty_input").value;
  if (productName.trim == "" || productName.trim.length) {
    alert("Product name must not be empty or less than 3 characters long!");
    return;
  }
  if (isNaN(productQty) || productQty <= 0) {
    alert("Qty must be a number higher than 0");
    return;
  }
  var request = {
    name: productName,
    qty: productQty
  };
  fetch("/saveProduct", {
    method: "POST",
    body: JSON.stringify(request),
    headers: {
      "Content-type": "application/json"
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      alert(jsonData.message);
      fetchProducts();
    });
  return false;
}

function fetchProducts() {
  fetch("/getProducts")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      showProducts(jsonData);
    });
}
function showProducts(jsonData) {
  var table = document.getElementById("products_table");

  for (var i = 1; i < table.rows.length; ) {
    table.deleteRow(i);
  }

  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement("tr");
    tr.setAttribute("id", "product-row-" + jsonData[i].id);

    var nameTd = document.createElement("td");
    nameTd.setAttribute("id","nameTd"+jsonData[i].id);
    nameTd.innerHTML = jsonData[i].name;

    var skuTd = document.createElement("td");
    skuTd.setAttribute("id","skuTd"+jsonData[i].id);
    skuTd.innerHTML = jsonData[i].sku;

    var qtyTd = document.createElement("td");
    qtyTd.setAttribute("id","qtyTd"+jsonData[i].id);
    qtyTd.innerHTML = jsonData[i].qty;

    var statusTd = document.createElement("td");
    statusTd.innerHTML = jsonData[i].statusText;

    var timeTd = document.createElement("td");
    timeTd.innerHTML = jsonData[i].timestampFormatted;

    var modifyButton = document.createElement("button");
    modifyButton.setAttribute("type", "button");
    modifyButton.innerHTML = "Szerkesztés";
    modifyButton.onclick = handleEdit;
    modifyButton.setAttribute("id", "modify"+jsonData[i].id);
    modifyButton["raw-data"] = jsonData[i];

    var exportButton = document.createElement("button");
    exportButton.setAttribute("type", "button");
    exportButton.innerHTML = "Exportálás";
    exportButton.onclick = handleExport;
    exportButton["raw-data"] = jsonData[i].id;

    tr.appendChild(nameTd);
    tr.appendChild(skuTd);
    tr.appendChild(qtyTd);
    tr.appendChild(statusTd);
    tr.appendChild(timeTd);
    tr.appendChild(exportButton);
    tr.appendChild(modifyButton);
    table.appendChild(tr);
  }
  document.getElementById("product_list_form").appendChild(table);
}

function handleExport(){
  var id = this["raw-data"];
  fetch("/exportProduct?id="+id)
    .then(function (response){
      return response.json();
    })
    .then(function(jsonData){
      alert(jsonData.message);
    });
return false;
}

function handleEdit() {
  var row = document.getElementById(`product-row-${this["raw-data"].id}`);
  var elements = row.getElementsByTagName("td");
  if (row.getAttribute("class")=="editable") {
    row.classList.remove("editable");
    for (var i = 0; i < 3; i++) {
          elements[i].contentEditable = "false";
          elements[i].classList.remove("under-edit");
    }
    updateProduct(this['raw-data']);
  } else {
    row.setAttribute('class', 'editable');
    for (var i = 0; i < 3; i++) {
      elements[i].contentEditable = "true";
      elements[i].setAttribute("class", "under-edit");
    }
    removeEditableFromOtherRows(row);
    var editButton = document.getElementById("modify"+this["raw-data"].id);
    editButton.innerHTML = "Mentés";
  }
}

function removeEditableFromOtherRows(actualRow) {
  var table = document.getElementById("products_table");
  var rows = table.getElementsByTagName("tr");
  for (var i = 1; i < rows.length; i++) {
    if (rows[i] != actualRow) {
      var editButton = rows[i].lastElementChild;
      editButton.innerHTML = "Szerkesztés";
      var children = rows[i].getElementsByTagName("td");
      rows[i].classList.remove("editable");
      for (var k = 0; k < 3; k++) {
        children[k].classList.remove("under-edit");
        children[k].contentEditable = "false";
      }
    }
  }
}
function updateProduct(data){
  var id = data.id;
  var name = document.getElementById("nameTd" + id).innerHTML;
  var sku = document.getElementById("skuTd"+id).innerHTML;
  var qty = document.getElementById("qtyTd"+id).innerHTML;
  document.getElementById("modify"+id).innerHTML="Szerkesztés";
  var request ={
    "id":id,
    "name":name,
    "qty":qty,
    "sku":sku
  };
  fetch("/updateProduct", {
        method: "PUT",
        body: JSON.stringify(request),
        headers: {
          "Content-type": "application/json"
        }
      })
      .then(function (response) {
        return response.json();
      }).then(function (jsonData) {
        fetchProducts();
      });
}
