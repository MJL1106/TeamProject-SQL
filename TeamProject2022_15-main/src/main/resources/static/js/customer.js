// The price when the menu is loaded
var price = 0;
var order = "";

function increment(x) {
  readTextFile("/menu.json", function(text){
    menu = JSON.parse(text); //parse JSON
    document.getElementById("input"+x).stepUp();
    price += menu[x].Data.price;
    order+= x +",";
    update();
  });
}

function decrement(x) {
  readTextFile("/menu.json", function(text){
    menu = JSON.parse(text); //parse JSON
    if (document.getElementById("input"+x).value != 0) {
      price -= menu[x].Data.price;
      update();
      order = order.replace(x +',', '');
    }
    document.getElementById("input"+x).stepDown();
  });
}

function readTextFile(file, callback) {
  var rawFile = new XMLHttpRequest();
  rawFile.overrideMimeType("application/json");
  rawFile.open("GET", file, true);
  rawFile.onreadystatechange = function() {
      if (rawFile.readyState === 4 && rawFile.status == "200") {
          callback(rawFile.responseText);
      }
  }
  rawFile.send(null);
}

function update() {
  document.getElementById("price").textContent = "price\n £" + price.toFixed(2);
}

function makeOrder() {
  var parameter = window.location.search.replace( "?", "" ); 
  var values = parameter.split("=");
  table = values[1];
  location.href = '/payment?order=' +order+ '&table='+table;
}

function resetValues(){
  document.getElementById("input").value=0;
}

//Code to say out of stock things are out of stock
function out(){
  const collection = document.getElementsByClassName("outOfStock")
  for (let i=0; i<collection.length; i++){
      collection[i].innerHTML += '<span class = "noItem"> OUT OF STOCK </span>';
  }
}

var count = 1;

// Code to ask for help
function help(){
  var parameter = window.location.search.replace( "?", "" ); 
  var values = parameter.split("=");
  table = values[1];
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
          if (count%2 != 0) {
              document.getElementById("confirmation").innerHTML = "asking for help</br>";
          } else {
              document.getElementById("confirmation").innerHTML = "help cancelled</br>";
          }
          count += 1;
      }
  };
  xhttp.open("GET", "customer?table="+table+"&help=1", true);
  xhttp.send();
  }