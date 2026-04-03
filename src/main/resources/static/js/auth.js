const BASE_URL = "http://localhost:8080";

console.log("AUTH JS LOADED");

// REGISTER
async function register() {

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let res = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({username, password})
    });

    alert(await res.text());
}

// LOGIN
async function login() {

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({username, password})
    });

    let data = await res.json();

    console.log("LOGIN RESPONSE:", data);

    if (data.userId) {

        localStorage.setItem("userId", data.userId);

        window.location.href = "./index.html";

    } else {
        alert(data.message);
    }
}