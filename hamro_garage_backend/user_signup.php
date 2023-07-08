<?php
if (isset($_POST['fullname']) && isset($_POST['address']) && isset($_POST['mobile']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['repassword']) && isset($_POST['type']))  {
   include 'conn.php';
    include 'validate.php';

    $fullname = validate($_POST['fullname']);
    $address = validate($_POST['address']);
    $mobile = validate($_POST['mobile']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $repassword = validate($_POST['repassword']);
    $type=($_POST['type']);

     
$checksql= "SELECT * FROM user_signup where mobile='$mobile' OR email='$email'";

            $checkrow= mysqli_query($conn,$checksql);

            if($checkrow->num_rows!=0){
              echo "Username or Email already registered!!";
            }else{

    $sql = "INSERT INTO user_signup (fullname, address, mobile, email, password, repassword,type) VALUES ('$fullname', '$address', '$mobile', '$email', '$password', '$repassword','$type')";

    if (!$conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}}

?>
