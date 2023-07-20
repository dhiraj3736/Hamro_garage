<?php
if (isset($_POST['garage_name']) && isset($_POST['available_time']) && isset($_POST['mobile']) && isset($_POST['service']) && isset($_POST['location']) && isset($_POST['status']) && isset($_POST['u_id'])) {
    include 'conn.php';
    include 'validate.php';

    $garage_name = validate($_POST['garage_name']);
    $available_time = validate($_POST['available_time']);
    $mobile = validate($_POST['mobile']);
    $service = validate($_POST['service']);
    $location = validate($_POST['location']);
    $status = validate($_POST['status']);
    $u_id = $_POST['u_id'];

    // Check if a garage with the given s_id already exists
    $check_query = "SELECT COUNT(*) as count FROM garage_info WHERE s_id = '$u_id'";
    $result = $conn->query($check_query);
    $row = $result->fetch_assoc();
    $garage_count = $row['count'];

    if ($garage_count > 0) {
        // Garage with the given s_id already exists
        echo "Already registered";
    } else {
        // Insert the garage information into the database
        $sql = "INSERT INTO garage_info (garage_name, available_time, mobile, service, location, status, s_id) VALUES ('$garage_name', '$available_time', '$mobile', '$service', '$location', '$status', '$u_id')";

        if ($conn->query($sql)) {
            echo "success";
        } else {
            echo "failure";
        }
    }
}
?>
