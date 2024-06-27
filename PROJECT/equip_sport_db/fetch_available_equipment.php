<?php
// Database credentials
$servername = "localhost";
$username = "root";
$password = "";
$database = "sport_equip_db";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Fetch available equipment
$sql = "SELECT equipName FROM equipment WHERE equipStatus = 'Available'";
$result = $conn->query($sql);

$equipmentList = array();
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $equipmentList[] = $row['equipName'];
    }
}
//die($result);
// Return as comma-separated values
echo implode(",", $equipmentList);

$conn->close();
?>
