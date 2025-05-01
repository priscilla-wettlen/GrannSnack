const savedDates = [];

function saveDate() {
    const dateInput = document.getElementById("datepicker");
    const selectedDate = dateInput.value;

    if (selectedDate && !savedDates.includes(selectedDate)) {
        savedDates.push(selectedDate);
        updateDateList();
        sendDateToBackend(selectedDate);
    }
}

function updateDateList() {
    const ul = document.getElementById("savedDates");
    ul.innerHTML = "";
    savedDates.forEach(date => {
        const li = document.createElement("li");
        li.textContent = date;
        ul.appendChild(li);
    });
}

function sendDateToBackend(date) {
    fetch("http://localhost:8080/api/dates", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ date: date })
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to save date.");
            return response.json();
        })
        .then(data => console.log("Saved:", data))
        .catch(error => console.error("Error:", error));
}
