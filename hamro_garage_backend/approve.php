<?php
 include('conn.php');
    include('validate.php');

if (isset($_POST['status']))  {
  
	$id=$_POST['id'];
    $status = $_POST['status'];
     

 


    $sql = "UPDATE garage_info SET status='$status' where id='$id'";

    if (!$conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}
?>