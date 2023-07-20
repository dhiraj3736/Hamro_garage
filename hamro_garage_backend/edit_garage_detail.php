<?php
 include('conn.php');
    include('validate.php');

if (isset($_POST['garage_name'])&&isset($_POST['available_time'])&&isset($_POST['mobile'])&&isset($_POST['service'])&&isset($_POST['location']))  {
  

    $garage_name = $_POST['garage_name'];
     $available_time = $_POST['available_time'];
      $mobile = $_POST['mobile'];
       $service = $_POST['service'];
         $location = $_POST['location'];

   if (isset($_POST['u_id'])) {
  $userid=$_POST['u_id'];

}
    $sql = "UPDATE garage_info SET garage_name='$garage_name',available_time='$available_time' ,mobile='$mobile', service='$service',location='$location' WHERE s_id='$userid'";

    if (!$conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}
?>