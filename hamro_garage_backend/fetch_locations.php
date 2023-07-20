<?php
include 'conn.php';
include 'validate.php';

// $userid = $_POST['u_id']; // Assuming the user ID is sent via POST request

$sql = "SELECT latitude, longitude FROM garage_location";
$result = $conn->query($sql);

$locations = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $latitude = $row["latitude"];
        $longitude = $row["longitude"];
        $location = array("latitude" => $latitude, "longitude" => $longitude);
        $locations[] = $location;
    }
}

$response = array("locations" => $locations);

header('Content-Type: application/json');
echo json_encode($response);


?>

