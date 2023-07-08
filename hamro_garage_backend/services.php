<?php
 include('conn.php');
    include('validate.php');
if (isset($_POST['service'])&& isset($_POST['u_id']))  {
  

    $service = $_POST['service'];

    $u_id=$_POST['u_id'];


    $sql = "INSERT INTO servicelist (service,u_id) VALUES ('$service','$u_id')";

    if (!$conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}
?>