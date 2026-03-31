const BASE_URL = "http://localhost:8080";

// =========================
// 🔐 CHECK LOGIN
// =========================
const userId = localStorage.getItem("userId");

console.log("UserId:", userId);

if (!userId || userId === "null") {
    alert("Please login first!");
    window.location.href = "/login.html";
}


// =========================
// 🔹 LOGOUT
// =========================
function logout() {
    localStorage.removeItem("userId");
    window.location.href = "/login.html";
}


// =========================
// 🔥 LOAD LIVE PRICES (CoinGecko)
// =========================
async function loadPrices() {

    console.log("📡 Fetching live prices...");

    try {
        let res = await fetch(`${BASE_URL}/prices`);
        let data = await res.json();

        console.log("Live Prices:", data);

        // ✅ Update UI with LIVE prices
        document.getElementById("btc").innerText = data.bitcoin?.usd || 0;
        document.getElementById("eth").innerText = data.ethereum?.usd || 0;
        document.getElementById("sol").innerText = data.solana?.usd || 0;

    } catch (error) {
        console.error("❌ Price Fetch Error:", error);
    }
}


// =========================
// 🔹 BUY COIN
// =========================
async function buyCoin() {

    let coin = document.getElementById("coin").value.toLowerCase();
    let amount = parseFloat(document.getElementById("amount").value);

    if (!coin || !amount) {
        alert("Enter coin and amount!");
        return;
    }

    try {
        let res = await fetch(`${BASE_URL}/trade/buy`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                userId: userId,
                coin: coin,
                amount: amount
            })
        });

        let result = await res.text();
        alert(result);

        loadPortfolio();

    } catch (error) {
        console.error("❌ Buy Error:", error);
    }
}


// =========================
// 🔹 SELL COIN
// =========================
async function sellCoin() {

    let coin = document.getElementById("coin").value.toLowerCase();
    let amount = parseFloat(document.getElementById("amount").value);

    if (!coin || !amount) {
        alert("Enter coin and amount!");
        return;
    }

    try {
        let res = await fetch(`${BASE_URL}/trade/sell`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                userId: userId,
                coin: coin,
                amount: amount
            })
        });

        let result = await res.text();
        alert(result);

        loadPortfolio();

    } catch (error) {
        console.error("❌ Sell Error:", error);
    }
}


// =========================
// 🔹 LOAD PORTFOLIO
// =========================
async function loadPortfolio() {

    console.log("📊 Loading portfolio...");

    try {
        let res = await fetch(`${BASE_URL}/portfolio/${userId}`);
        let data = await res.json();

        console.log("Portfolio:", data);

        let html = "";

        if (!data || data.length === 0) {
            html = "<p>No coins yet</p>";
        } else {
            data.forEach(p => {
                html += `
                    <div class="card">
                        ${p.coinName.toUpperCase()} : ${p.quantity}
                    </div>
                `;
            });
        }

        document.getElementById("portfolio").innerHTML = html;

    } catch (error) {
        console.error("❌ Portfolio Error:", error);
    }
}


// =========================
// 🔄 INITIAL LOAD
// =========================
window.onload = () => {

    console.log("🚀 TradeHub Loaded");

    loadPrices();
    loadPortfolio();

    // 🔄 Refresh prices every 5 sec
    setInterval(loadPrices, 5000);
};