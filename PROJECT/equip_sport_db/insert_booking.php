<?php
// Check if all required POST parameters are received
if (isset($_POST['userName'], $_POST['equipName'], $_POST['borrowDate'], $_POST['returnDate'])) {
    // Sanitize inputs to prevent SQL injection
    $userName = htmlspecialchars($_POST['userName']);
    $equipName = htmlspecialchars($_POST['equipName']);
    $borrowDate = htmlspecialchars($_POST['borrowDate']);
    $returnDate = htmlspecialchars($_POST['returnDate']);

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

    // Prepare SQL statement to insert data into booking table
    $stmt = $conn->prepare("INSERT INTO booking (userName, equipId, borrowDate, returnDate) VALUES (?, ?, ?, ?)");

    // Fetch equipId from equipment table based on equipName
    $equipId = getEquipmentId($conn, $equipName);

    // Bind parameters to the prepared statement
    $stmt->bind_param("siss", $userName, $equipId, $borrowDate, $returnDate);

    // Execute the prepared statement
    if ($stmt->execute()) {
        echo "Booking submitted successfully";
    } else {
        echo "Error: " . $stmt->error;
    }

    // Close statement and database connection
    $stmt->close();
    $conn->close();
} else {
    // If required parameters are not received, return an error message
    echo "Error: Missing parameters";
}
    
// Function to fetch equipment ID based on equipment name
function getEquipmentId($conn, $equipName) {
    $equipId = null; // Initialize $equipId to null
    // Prepare SQL statement to fetch equipId from equipment table based on equipName
    $stmt = $conn->prepare("SELECT equipId FROM equipment WHERE equipName = ?");
    $stmt->bind_param("s", $equipName);
    $stmt->execute();
    $stmt->bind_result($equipId);
    $stmt->fetch();
    $stmt->close();

    return $equipId;
}

?>
