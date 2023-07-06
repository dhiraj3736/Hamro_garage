<?php
// Assuming you have already established a database connection
require_once "conn.php";
require_once "validate.php";

// Start the session
session_start();

// Check if the 'user_id' session variable exists
if (isset($_SESSION['user_id'])) {
    // Retrieve the 'user_id' from the session
    $loggedInUserId = $_SESSION['user_id'];
    
    // Fetch the profile details of the logged-in user
    $query = "SELECT fullname, address, mobile, email FROM user_signup WHERE id = '$loggedInUserId'";
    $result = mysqli_query($connection, $query);

    // Create an array to hold the fetched data
    $customerDetails = array();

    // Check if any rows are returned
    if (mysqli_num_rows($result) > 0) {
        // Fetch the row for the logged-in user
        $row = mysqli_fetch_assoc($result);
        // Add the row to the array
        $customerDetails[] = $row;
    }

    // Create a JSON response
    $response = array();
    $response['customer_details'] = $customerDetails;
    $response['loggedInUserId'] = $loggedInUserId; // Include loggedInUserId in the response

    // Return the JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    // Handle the case when 'user_id' session variable is not set
    echo "User ID not found in session.";
}


?>
