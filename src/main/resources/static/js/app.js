console.log("APP JS LOADED 🚀");

window.onload = function () {
    loadPrices();
    loadPortfolio();
};

// 🔥 LOAD PRICES
function loadPrices() {
    fetch("/api/crypto/price")
        .then(res => res.json())
        .then(data => {

            let container = document.getElementById("crypto-container");
            container.innerHTML = "";

            for (let coin in data) {
                let price = data[coin].usd;

                let div = document.createElement("div");
                div.className = "card";
                div.innerHTML = `<h3>${coin.toUpperCase()}</h3><p>$${price}</p>`;

                container.appendChild(div);
            }
        });
}

// 💰 BUY
function buy() {
    let coin = document.getElementById("coin").value;
    let amount = document.getElementById("amount").value;

    fetch(`/api/trade/buy?coin=${coin}&amount=${amount}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        loadPortfolio();
    });
}

// 💸 SELL
function sell() {
    let coin = document.getElementById("coin").value;
    let amount = document.getElementById("amount").value;

    fetch(`/api/trade/sell?coin=${coin}&amount=${amount}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        loadPortfolio();
    });
}

// 📊 PORTFOLIO
function loadPortfolio() {
    fetch("/api/trade/portfolio")
        .then(res => res.json())
        .then(data => {

            let div = document.getElementById("portfolio");
            div.innerHTML = "";

            for (let coin in data) {
                let p = document.createElement("p");
                p.innerText = coin.toUpperCase() + " : " + data[coin];
                div.appendChild(p);
            }
        });
}

// 🔄 AUTO REFRESH PRICES
setInterval(loadPrices, 30000);