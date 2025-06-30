/*let input = document.createElement("input");
let button = document.createElement("button");
let body = document.querySelector("body");
let h1= document.createElement("h1");
let p = document.createElement("p");
button.innerText= "click me"
input.setAttribute("placeholder","type here ")
button.setAttribute("id","btn")
h1.innerHTML="<u>DOM Manipulation</u>"
p.innerHTML="Appana College <b>Delta</b> Practice"
body.append(input);

for(let i=1; i<=5; i++){
    body.append(button)
}
body.append(h1);
body.append(p);
document.querySelector("#btn").classList.add("btnstyle");



//events:
let buttons = document.querySelectorAll("button");
for(btns of buttons){
    btns.onclick=sayhello;
    btns.onpointerenter= function(){
        console.log("Mouse has entered")
    }
} 




function sayhello(){
    console.log("button was clicked!");
}

//button.onclick= function() {
   // console.log("button was clicked!");
//}

// abb yaha pai hogi asli coding:

let btn = document.querySelector("button");

btn.addEventListener("click", function() {
let generate = GenerateRandom();
let h1 = document.querySelector("h1");
h1.innerText= generate;

let div = document.querySelector("div"); 
div.style.backgroundColor=generate;
} );

function GenerateRandom(){
let red = Math.floor(Math.random()*255);
let green = Math.floor(Math.random()*255);
let blue = Math.floor(Math.random()*255);

let color = `rgb(${red}, ${green}, ${blue})`;
return color;
}

// this is for mouseout event  

let ul = document.querySelector("ul");

ul.addEventListener("mouseout", function() {
    ul.style.color = "red";

     setTimeout(() => {
      ul.style.color = "";
    }, 500);
});

// this is for keyboard event

const log = document.getElementById("log");
const input = document.querySelector("input");

input.addEventListener("keypress", function logKey(e) {
  log.textContent += ` ${e.code}`;
  console.log(e.value);
});

button= document.createElement("button");
button.innerText= "click me";
button.style.margin= "10px";
let body= document.querySelector("body");
body.appendChild(button); 

button.addEventListener("click", function(){
   this.style.color= "green";
});


let input2= document.getElementById("myname");
let h= document.getElementById("heading");

input2.addEventListener("input", function(){
    console.log(this.value.replace(/[^a-zA-Z ]/g, ""));
    const cleaned = this.value.replace(/[^a-zA-Z ]/g, "");
      h.innerText = cleaned;
    
});


function savetodb(data){
    return new Promise((resolve, rejected) =>{
        let internet= Math.floor(Math.random() * 10) + 1;
        if(internet>4){
            resolve("Data has been saved: " + data);
        } else {
            rejected("weak connection!, Data has not been saved.")
        }
    });     
} 

savetodb("my name: ishan bawa").
then(()=>{
    console.log("Promise was resolved");
})
.catch(()=>{
    console.log("Promise was rejected");
});


///promise in javascript: 

let prom = document.getElementById("promise");

function changecolor(color, time){
    return new Promise((resolve,reject)=>{
        setTimeout(()=>{
     prom.style.color=color;
     resolve("Color changeg!"); 
},time);
    });
} 

changecolor("red",1000).
then(()=>{
    console.log("Color changed");
    return changecolor("orange",1000);
});*/


let url = "https://catfact.ninja/fact";

async function getfact(){
   try {let res= await axios.get(url);
    return res.data.fact;
}catch(e){
   return e;

} 
}
let button = document.getElementById("press");

button.addEventListener("click", async ()=>{
    fact= await getfact();
    let p = document.getElementById("result");
    p.innerText= fact;
console.log(fact);

});









