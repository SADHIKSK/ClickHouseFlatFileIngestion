document.getElementById('sourceSelect').addEventListener('change', function () {
    const value = this.value;
    document.getElementById('clickhouseFields').classList.add('hidden');
    document.getElementById('csvFields').classList.add('hidden');
    document.getElementById('columnsSection').classList.add('hidden');
    if (value === 'clickhouse') {
        document.getElementById('clickhouseFields').classList.remove('hidden');
    } else if (value === 'csv') {
        document.getElementById('csvFields').classList.remove('hidden');
    }
});

function loadTables() {
    const status = document.getElementById("statusArea");
    status.textContent = "Connecting to ClickHouse...";
    // Simulate table load
    setTimeout(() => {
        showColumns(["name", "age", "email"]);
        status.textContent = "Tables loaded!";
    }, 1000);
}

function loadCSV() {
    const status = document.getElementById("statusArea");
    status.textContent = "Reading CSV file...";
    // Simulate CSV read
    setTimeout(() => {
        showColumns(["id", "title", "price"]);
        status.textContent = "CSV Loaded!";
    }, 1000);
}

function showColumns(columns) {
    const container = document.getElementById("columnList");
    container.innerHTML = '';
    document.getElementById("columnsSection").classList.remove('hidden');
    columns.forEach(col => {
        const label = document.createElement('label');
        label.innerHTML = `<input type="checkbox" value="${col}" checked> ${col}`;
        container.appendChild(label);
    });
}

function startIngestion() {
    const status = document.getElementById("statusArea");
    status.textContent = "Ingestion in progress...";
    setTimeout(() => {
        window.location.href = "result.html";
    }, 2000);
}
