function register(){

    var name = document.getElementById("name_input").value;
    var password = document.getElementById("password_input").value;
    if (name.trim()=="" || password.trim() ==""){
    alert("Name and/or password can not be empty")
    }else{
       var request = {
            "name":name,
            "password":password
        };
        fetch('/register', {
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
            });
            return false;
        }
}

function login(){
    var name = document.getElementById("name_input").value;
    var password = document.getElementById("password_input").value;
    if (name.trim()=="" || password.trim() ==""){
        alert("Name and/or password can not be empty");
    }else{
       var request = {
            "name":name,
            "password":password
       };
       fetch('/login', {
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
        if(jsonData.message==="OK"){
            loggedIn(request.name);
        }else{
        alert(jsonData.message);
        }
      });
      return false;
     }
}

function loggedIn(name){
    document.getElementById("login_form").style.display="none";
    document.getElementById("logged_in").style.display="block";
    var loggedInForm = document.getElementById("logged_in");
    var logOutButton = document.createElement("button");
    newProduct();
    listProducts();
    logOutButton.innerHTML="Log out" ;
    loggedInForm.innerHTML =`<div align="center" margin-top="300px"><p float="left">Hello ${name} !</p>
    <br> <button class="button_cont" float="left" type="button" onclick="logout()">Log out </button></div>`
}



function logout(){
     document.getElementById("login_form").style.display="block";
     document.getElementById("logged_in").style.display="none";
}
