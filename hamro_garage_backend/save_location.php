<?php
require "conn.php";

if (isset($_POST['latitude']) && isset($_POST['longitude']) && isset($_POST['u_id'])) {
    $latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];
    $userId = $_POST['u_id'];

    // Check if the garage location already exists for the user
    $checkQuery = "SELECT * FROM garage_location WHERE s_id = '$userId'";
    $checkResult = mysqli_query($conn, $checkQuery);

    if (mysqli_num_rows($checkResult) > 0) {
        // Update the existing garage location
        $updateQuery = "UPDATE garage_location SET latitude = '$latitude', longitude = '$longitude' WHERE s_id = '$userId'";
        $updateResult = mysqli_query($conn, $updateQuery);

        if ($updateResult) {
            echo "success";
        } else {
            echo "error";
        }
    } else {
        // Insert a new garage location
        $insertQuery = "INSERT INTO garage_location (latitude, longitude, s_id) VALUES ('$latitude', '$longitude', '$userId')";
        $insertResult = mysqli_query($conn, $insertQuery);

        if ($insertResult) {
            echo "success";
        } else {
            echo "error";
        }
    }
} elseif (isset($_POST['u_id'])) {
    $userId = $_POST['u_id'];

    // Retrieve the garage location for the user
    $retrieveQuery = "SELECT latitude, longitude FROM garage_location WHERE s_id = '$userId'";
    $retrieveResult = mysqli_query($conn, $retrieveQuery);

    if (mysqli_num_rows($retrieveResult) > 0) {
        $location = mysqli_fetch_assoc($retrieveResult);
        $latitude = $location['latitude'];
        $longitude = $location['longitude'];
        echo $latitude . ',' . $longitude;
    } else {
        echo "error";
    }
} else {
    echo "missing_coordinates";
}

?>

