<?php
if (isset($_POST['fullname']) && isset($_POST['address']) && isset($_POST['mobile']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['repassword']) && isset($_POST['type'])) {
    require_once "conn.php";
    require_once "validate.php";

    $fullname = validate($_POST['fullname']);
    $address = validate($_POST['address']);
    $mobile = validate($_POST['mobile']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $repassword = validate($_POST['repassword']);
    $type = $_POST['type'];

    // Check if email or mobile already exists in the database
    $checkQuery = "SELECT * FROM user_signup WHERE email = '$email' OR mobile = '$mobile'";
    $checkResult = $conn->query($checkQuery);

    if ($checkResult->num_rows > 0) {
        echo "duplicate";
    } else {
        $sql = "INSERT INTO user_signup (fullname, address, mobile, email, password, repassword, type) VALUES ('$fullname', '$address', '$mobile', '$email', '$password', '$repassword', '$type')";

        if (!$conn->query($sql)) {
            echo "failure";
        } else {
            echo "success";
        }
    }
}
?>
