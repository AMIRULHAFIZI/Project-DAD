<?php
// Check if the POST request contains the 'equipName' parameter
if (isset($_POST['equipName'])) {
    // Sanitize the input to prevent SQL injection
    $equipName = htmlspecialchars($_POST['equipName']);

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

    // Prepare SQL statement to fetch equipment type based on equipment name
    $stmt = $conn->prepare("SELECT equipType FROM equipment WHERE equipName = ?");
    $stmt->bind_param("s", $equipName);

    // Execute the prepared statement
    $stmt->execute();

    // Bind result variables
    $stmt->bind_result($equipType);

    // Fetch the result
    if ($stmt->fetch()) {
        // Output the equipment type (you can modify the output format as needed)
        echo $equipType;
    } else {
        // If no result found, you can return an appropriate message or handle it as needed
        echo "Equipment not found";
    }

    // Close statement and database connection
    $stmt->close();
    $conn->close();
} else {
    // If 'equipName' parameter is not set, return an error message
    echo "Error: EquipName parameter not received";
}
?>
