<?php
 include('conn.php');
    include('validate.php');


if (isset($_POST['fullname'])&&isset($_POST['address'])&&isset($_POST['mobile'])&&isset($_POST['email']))  {
  

    $fullname = $_POST['fullname'];
     $address = $_POST['address'];
      $mobile = $_POST['mobile'];
       $email = $_POST['email'];

   if (isset($_POST['u_id'])) {
  $userid=$_POST['u_id'];

}
    $sql = "UPDATE user_signup SET fullname='$fullname',address='$address' ,mobile='$mobile', email='$email' WHERE id='$userid'";

    if (!$conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}
?>