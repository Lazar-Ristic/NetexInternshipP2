var dataSource = [];


function findByName() {
  var contactName=$("#findByNameInput").val();
  $.ajax({
    url: "http://localhost:8080/adressbook/rest/findByName?contactName="+contactName,
    type: "GET",
    crossdomain: true,
    success: (result) => {
      console.log(result);
      dataSource = result;
      $("#indexTable").html("");
      result.forEach((element,index) => {
        $('#indexTable').append(`<tr><td>`+(index+1)+`</td><td>`+element.Name+`</td><td>`+ element.Email+`</td><td><img src="data:image/png;base64,`+element.Image+`" 
        class="contactImage"></td><td class="text-center">
        <button class="btn btn-primary" onclick="onAdd(` + index + `)">edit</button>
        <button class="btn btn-primary ml-1" onclick="deleteContact(` + index + `)">delete</button></td></tr>`);
      });
    }
  });
}

function onAdd(index) {
  $('#contactForm').html(`
    <form class="row d-flex border-top">
      <div class="mr-1">
        <label>Name</label>
        <input type="text" class="form-control" id="addContactNameInput">
      </div>
      <div class="mr-1">
        <label>Email</label>
        <input type="email" class="form-control" id="addContactEmailInput">
      </div>
      <div class="mr-1">
        <label>Image</label>
        <input type="file" class="form-control-file" id="addContactImageInput">
      </div>
      <button type="button" class="btn btn-primary align-self-end mr-1" onclick="addContact(`+ index +`)">submit</button>
      <button class="btn btn-primary align-self-end" onclick="cancel()">cancel</button>
    </form>
  `);
  if (index != undefined) {
    $("#addContactNameInput").val(dataSource[index].Name);
    $("#addContactEmailInput").val(dataSource[index].Email);
  }
  return false;
}

function addContact(index) {
  var contactName=$("#addContactNameInput").val();
  var contactEmail=$("#addContactEmailInput").val();
  var file = $("#addContactImageInput")[0].files[0];
  var fr = new FileReader();
  fr.onload = () => {
    if (index != undefined) {
      var contactAdd = JSON.stringify({
        image: fr.result.split(',')[1],
        contact: {
          Name: contactName,
          Email: contactEmail,
          id: dataSource[index].id,
        },
      });
      $.ajax({
        url: "http://localhost:8080/adressbook/rest/contact",
        type: "PUT",
        data: contactAdd,
        dataType: "json",
        contentType: "application/json",
        success: () => {
          findByName();
        }
      });
    } else {
      var contactAdd = JSON.stringify({
        image: fr.result.split(',')[1],
        contact: {
          Name: contactName,
          Email: contactEmail,
        },
      });
      $.ajax({
        url: "http://localhost:8080/adressbook/rest/contact",
        type: "POST",
        data: contactAdd,
        dataType: "json",
        contentType: "application/json",
      });
    }
  };
  fr.readAsDataURL(file);
  $('#contactForm').html(``);
  return false;
}

function deleteContact(index) {
  $.ajax({
    url: "http://localhost:8080/adressbook/rest/deleteContact/" + dataSource[index].id,
    type: "DELETE",
    success: () => {
      findByName();
    }
  });
}

function exportToCSV() {
  $.ajax({
    url: "http://localhost:8080/adressbook/rest/exportToCSV",
    type: "POST",
    success: () => {
      $('#exportMessage').html(`Exported to C:\\temp\\testCSV.csv`);
    }
  });
}

function cancel() {
  $('#contactForm').html(``);
}

