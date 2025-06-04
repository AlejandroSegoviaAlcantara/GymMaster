document.getElementById("logoutBtn").addEventListener("click", () => {
    fetch("/api/logout", { method: "POST" },
    {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
        .then(() => {
            window.location.href = "index.html";
        });
});
