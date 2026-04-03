// const BASE_URL = "http://localhost:8080";

// // =========================
// // 🔐 AUTH CHECK
// // =========================
// const userId = localStorage.getItem("userId");

// console.log("UserId:", userId);

// if (!userId || userId === "null") {
//     alert("Please login first!");
//     window.location.href = "/login.html";
// }

// // =========================
// // 🔓 LOGOUT
// // =========================
// function logout() {
//     localStorage.removeItem("userId");
//     window.location.href = "/login.html";
// }

// // =========================
// // 🔄 UPDATE UI
// // =========================
// function updatePrices(data) {
//     document.getElementById("btc").innerText = data.bitcoin;
//     document.getElementById("eth").innerText = data.ethereum;
//     document.getElementById("sol").innerText = data.solana;
// }

// // =========================
// // 🔥 API FALLBACK (IMPORTANT)
// // =========================
// async function loadPrices() {
//     try {
//         let res = await fetch(`${BASE_URL}/prices`);
//         let data = await res.json();

//         console.log("📡 API Prices:", data);

//         updatePrices(data);

//     } catch (err) {
//         console.error("❌ API Error:", err);
//     }
// }

// // =========================
// // ⚡ WEBSOCKET (REAL-TIME)
// // =========================
// let socket;

// function connectWebSocket() {

//     socket = new WebSocket("ws://localhost:8080/ws/prices");

//     socket.onopen = () => {
//         console.log("✅ WebSocket Connected");
//     };

//     socket.onmessage = (event) => {
//         const data = JSON.parse(event.data);

//         console.log("⚡ WS Data:", data);

//         updatePrices(data);
//     };

//     socket.onerror = (e) => {
//         console.error("❌ WebSocket Error:", e);
//     };

//     socket.onclose = () => {
//         console.log("⚠️ WebSocket Disconnected → retrying...");
//         setTimeout(connectWebSocket, 3000);
//     };
// }

// // =========================
// // 💰 BUY COIN
// // =========================
// async function buyCoin() {

//     let coin = document.getElementById("coin").value.toLowerCase();
//     let amount = parseFloat(document.getElementById("amount").value);

//     if (!coin || !amount) {
//         alert("Enter coin and amount!");
//         return;
//     }

//     try {
//         let res = await fetch(`${BASE_URL}/trade/buy`, {
//             method: "POST",
//             headers: {"Content-Type": "application/json"},
//             body: JSON.stringify({
//                 userId: userId,
//                 coin: coin,
//                 amount: amount
//             })
//         });

//         let result = await res.text();
//         alert(result);

//         loadPortfolio();

//     } catch (error) {
//         console.error("❌ Buy Error:", error);
//     }
// }

// // =========================
// // 📉 SELL COIN
// // =========================
// async function sellCoin() {

//     let coin = document.getElementById("coin").value.toLowerCase();
//     let amount = parseFloat(document.getElementById("amount").value);

//     if (!coin || !amount) {
//         alert("Enter coin and amount!");
//         return;
//     }

//     try {
//         let res = await fetch(`${BASE_URL}/trade/sell`, {
//             method: "POST",
//             headers: {"Content-Type": "application/json"},
//             body: JSON.stringify({
//                 userId: userId,
//                 coin: coin,
//                 amount: amount
//             })
//         });

//         let result = await res.text();
//         alert(result);

//         loadPortfolio();

//     } catch (error) {
//         console.error("❌ Sell Error:", error);
//     }
// }

// // =========================
// // 📊 LOAD PORTFOLIO
// // =========================
// async function loadPortfolio() {

//     console.log("📊 Loading portfolio...");

//     try {
//         let res = await fetch(`${BASE_URL}/portfolio/${userId}`);
//         let data = await res.json();

//         console.log("Portfolio:", data);

//         let html = "";

//         if (!data || data.length === 0) {
//             html = "<p>No coins yet</p>";
//         } else {
//             data.forEach(p => {
//                 html += `
//                     <div class="card">
//                         ${p.coinName.toUpperCase()} : ${p.quantity}
//                     </div>
//                 `;
//             });
//         }

//         document.getElementById("portfolio").innerHTML = html;

//     } catch (error) {
//         console.error("❌ Portfolio Error:", error);
//     }
// }

// // =========================
// // 🚀 INIT
// // =========================
// window.onload = () => {

//     console.log("🚀 TradeHub Loaded");

//     // 🔥 Load initial data
//     loadPrices();

//     // ⚡ Start WebSocket
//     connectWebSocket();

//     // 📊 Load portfolio
//     loadPortfolio();

//     // 🔄 Backup refresh every 15 sec
//     setInterval(loadPrices, 15000);
// };


















// // =========================
// // 🔐 BASE CONFIG
// // =========================
// const BASE_URL = "http://localhost:8080";
// const userId = localStorage.getItem("userId");

// if (!userId || userId === "null") {
//     alert("Please login first!");
//     window.location.href = "/login.html";
// }

// // =========================
// // 🎨 COLORS
// // =========================
// const GREEN = "#22c55e";
// const RED = "#ef4444";
// const WHITE = "#ffffff";

// // =========================
// // 💾 STORE PRICES
// // =========================
// let lastPrices = {
//     btc: 0,
//     eth: 0,
//     sol: 0
// };

// // =========================
// // 🔥 FORMAT PRICE
// // =========================
// function formatPrice(price) {
//     return price.toLocaleString(undefined, {
//         minimumFractionDigits: 2,
//         maximumFractionDigits: 6
//     });
// }

// // =========================
// // 🎯 UPDATE PRICES
// // =========================
// function updatePrices(data) {
//     if (!data) return;

//     handlePrice("btc", data.bitcoin);
//     handlePrice("eth", data.ethereum);
//     handlePrice("sol", data.solana);
// }

// // =========================
// // ⚡ HANDLE PRICE
// // =========================
// function handlePrice(id, newPrice) {

//     const el = document.getElementById(id);
//     const changeEl = document.getElementById(id + "-change");

//     if (!el || !changeEl || newPrice == null) return;

//     let oldPrice = lastPrices[id];

//     let change = oldPrice === 0 ? 0 : ((newPrice - oldPrice) / oldPrice) * 100;

//     if (newPrice > oldPrice) {
//         el.style.color = GREEN;
//         changeEl.style.color = GREEN;
//         flash(el, true);
//     } 
//     else if (newPrice < oldPrice) {
//         el.style.color = RED;
//         changeEl.style.color = RED;
//         flash(el, false);
//     } 
//     else {
//         el.style.color = WHITE;
//     }

//     el.innerText = "$" + formatPrice(newPrice);
//     changeEl.innerText = change.toFixed(2) + "%";

//     lastPrices[id] = newPrice;
// }

// // =========================
// // ✨ FLASH EFFECT
// // =========================
// function flash(el, isUp) {
//     el.style.transition = "all 0.15s ease";
//     el.style.transform = "scale(1.08)";
//     el.style.backgroundColor = isUp 
//         ? "rgba(0,255,0,0.15)" 
//         : "rgba(255,0,0,0.15)";

//     setTimeout(() => {
//         el.style.transform = "scale(1)";
//         el.style.backgroundColor = "transparent";
//     }, 120);
// }

// // =========================
// // 🔥 API FALLBACK
// // =========================
// async function loadPrices() {
//     try {
//         let res = await fetch(`${BASE_URL}/prices`);
//         if (!res.ok) throw new Error("API failed");

//         let data = await res.json();
//         updatePrices(data);

//     } catch (err) {
//         console.error("API Error:", err);
//     }
// }

// // =========================
// // ⚡ WEBSOCKET (IMPROVED)
// // =========================
// function connectWebSocket() {

//     let socket = new WebSocket("ws://localhost:8080/ws/prices");

//     socket.onopen = () => {
//         console.log("✅ WebSocket Connected");
//     };

//     socket.onmessage = (event) => {
//         try {
//             const data = JSON.parse(event.data);
//             updatePrices(data);
//         } catch (e) {
//             console.error("WS Parse Error:", e);
//         }
//     };

//     socket.onerror = (e) => {
//         console.error("❌ WebSocket Error:", e);
//         socket.close();
//     };

//     socket.onclose = () => {
//         console.log("⚠️ Reconnecting in 2s...");
//         setTimeout(connectWebSocket, 2000);
//     };
// }

// // =========================
// // 📊 PORTFOLIO
// // =========================
// async function loadPortfolio() {

//     try {
//         let res = await fetch(`${BASE_URL}/portfolio/${userId}`);
//         if (!res.ok) throw new Error("Portfolio fetch failed");

//         let data = await res.json();

//         let html = "";

//         if (!data || data.length === 0) {
//             html = "<p>No coins yet</p>";
//         } else {
//             data.forEach(p => {
//                 html += `
//                     <div class="card">
//                         ${p.coinName.toUpperCase()} : ${p.quantity}
//                     </div>
//                 `;
//             });
//         }

//         document.getElementById("portfolio").innerHTML = html;

//     } catch (error) {
//         console.error("Portfolio Error:", error);
//     }
// }

// // =========================
// // 💰 BUY COIN
// // =========================
// async function buy() {

//     let coin = document.getElementById("coin").value.trim().toLowerCase();
//     let amount = parseFloat(document.getElementById("amount").value);

//     if (!coin || !amount || amount <= 0) {
//         alert("Enter valid coin and amount");
//         return;
//     }

//     try {
//         let res = await fetch(`${BASE_URL}/trade/buy`, {
//             method: "POST",
//             headers: { "Content-Type": "application/json" },
//             body: JSON.stringify({
//                 userId,
//                 coin,
//                 amount
//             })
//         });

//         if (!res.ok) throw new Error("Buy failed");

//         alert("✅ Buy Successful");
//         loadPortfolio();

//     } catch (err) {
//         console.error("Buy Error:", err);
//         alert("❌ Buy failed");
//     }
// }

// // =========================
// // 💸 SELL COIN
// // =========================
// async function sell() {

//     let coin = document.getElementById("coin").value.trim().toLowerCase();
//     let amount = parseFloat(document.getElementById("amount").value);

//     if (!coin || !amount || amount <= 0) {
//         alert("Enter valid coin and amount");
//         return;
//     }

//     try {
//         let res = await fetch(`${BASE_URL}/trade/sell`, {
//             method: "POST",
//             headers: { "Content-Type": "application/json" },
//             body: JSON.stringify({
//                 userId,
//                 coin,
//                 amount
//             })
//         });

//         if (!res.ok) throw new Error("Sell failed");

//         alert("✅ Sell Successful");
//         loadPortfolio();

//     } catch (err) {
//         console.error("Sell Error:", err);
//         alert("❌ Sell failed");
//     }
// }

// // =========================
// // 🔓 LOGOUT
// // =========================
// function logout() {
//     localStorage.removeItem("userId");
//     window.location.href = "/login.html";
// }

// // =========================
// // 🌍 GLOBAL
// // =========================
// window.buy = buy;
// window.sell = sell;
// window.logout = logout;

// // =========================
// // 🚀 INIT
// // =========================
// window.onload = () => {

//     console.log("🚀 TradeHub Loaded");

//     loadPrices();
//     connectWebSocket();
//     loadPortfolio();

//     // fallback (less frequent)
//     setInterval(loadPrices, 500);
// };


// =========================
// 🔐 BASE CONFIG
// =========================
const BASE_URL = "http://localhost:8080";
const userId = localStorage.getItem("userId");

if (!userId || userId === "null") {
    alert("Please login first!");
    window.location.href = "/login.html";
}

// =========================
// 🎨 COLORS
// =========================
const GREEN = "#22c55e";
const RED = "#ef4444";
const WHITE = "#ffffff";

// =========================
// 💾 LAST PRICES
// =========================
let lastPrices = {
    btc: 0,
    eth: 0,
    sol: 0
};

// =========================
// 🎯 REAL-TIME (BINANCE)
// =========================
function connectRealTimePrices() {

    function setup(symbol, id) {

        let socket = new WebSocket(`wss://fstream.binance.com/ws/${symbol}@trade`);
        let oldPrice = 0;

        socket.onopen = () => {
            console.log(`✅ ${symbol} connected`);
        };

        socket.onmessage = (event) => {
            let data = JSON.parse(event.data);
            let newPrice = parseFloat(data.p);

            const el = document.getElementById(id);
            const changeEl = document.getElementById(id + "-change");

            if (!el || !changeEl) return;

            let change = oldPrice === 0 ? 0 : ((newPrice - oldPrice) / oldPrice) * 100;

            // 🎨 COLOR LOGIC
            if (newPrice > oldPrice) {
                el.style.color = GREEN;
                changeEl.style.color = GREEN;
                flash(el, true);
            } 
            else if (newPrice < oldPrice) {
                el.style.color = RED;
                changeEl.style.color = RED;
                flash(el, false);
            } 
            else {
                el.style.color = WHITE;
            }

            // 💹 UPDATE UI
            el.innerText = "$" + newPrice.toFixed(2);
            changeEl.innerText = change.toFixed(2) + "%";

            oldPrice = newPrice;
            lastPrices[id] = newPrice;
        };

        socket.onerror = () => {
            console.log(`❌ ${symbol} error`);
            socket.close();
        };

        socket.onclose = () => {
            console.log(`⚠️ Reconnecting ${symbol}...`);
            setTimeout(() => setup(symbol, id), 2000);
        };
    }

    // 🔥 REAL STREAMS
    setup("btcusdt", "btc");
    setup("ethusdt", "eth");
    setup("solusdt", "sol");
}

// =========================
// ✨ FLASH EFFECT
// =========================
function flash(el, isUp) {
    el.style.transition = "all 0.15s ease";
    el.style.transform = "scale(1.08)";
    el.style.backgroundColor = isUp 
        ? "rgba(0,255,0,0.15)" 
        : "rgba(255,0,0,0.15)";

    setTimeout(() => {
        el.style.transform = "scale(1)";
        el.style.backgroundColor = "transparent";
    }, 120);
}

// =========================
// 📊 PORTFOLIO
// =========================
async function loadPortfolio() {

    try {
        let res = await fetch(`${BASE_URL}/portfolio/${userId}`);
        let data = await res.json();

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
        console.error("Portfolio Error:", error);
    }
}

// =========================
// 💰 BUY
// =========================
async function buy() {

    let coin = document.getElementById("coin").value.trim().toLowerCase();
    let amount = parseFloat(document.getElementById("amount").value);

    if (!coin || !amount || amount <= 0) {
        alert("Enter valid coin and amount");
        return;
    }

    try {
        let res = await fetch(`${BASE_URL}/trade/buy`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, coin, amount })
        });

        if (!res.ok) throw new Error();

        alert("✅ Buy Successful");
        loadPortfolio();

    } catch {
        alert("❌ Buy Failed");
    }
}

// =========================
// 💸 SELL
// =========================
async function sell() {

    let coin = document.getElementById("coin").value.trim().toLowerCase();
    let amount = parseFloat(document.getElementById("amount").value);

    if (!coin || !amount || amount <= 0) {
        alert("Enter valid coin and amount");
        return;
    }

    try {
        let res = await fetch(`${BASE_URL}/trade/sell`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, coin, amount })
        });

        if (!res.ok) throw new Error();

        alert("✅ Sell Successful");
        loadPortfolio();

    } catch {
        alert("❌ Sell Failed");
    }
}

// =========================
// 🔓 LOGOUT
// =========================
function logout() {
    localStorage.removeItem("userId");
    window.location.href = "/login.html";
}

// =========================
// 🌍 GLOBAL
// =========================
window.buy = buy;
window.sell = sell;
window.logout = logout;

// =========================
// 🚀 INIT
// =========================
window.onload = () => {

    console.log("🚀 TradeHub Loaded");

    // 🔥 REAL-TIME (NO BACKEND DELAY)
    connectRealTimePrices();

    // Portfolio
    loadPortfolio();
};