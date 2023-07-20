<?php
include 'conn.php';
    include 'validate.php';


if (isset($_POST['u_id'])&&isset($_POST['status'])) {
    $userid=$_POST['u_id'];
    $status=$_POST['status'];
    # code...
}
 // where u_id='$userid'


$result=array();
$result['data']=array();


$select= "SELECT * from garage_info where s_id='$userid'and status='$status'"; 
$responce= mysqli_query($conn,$select);

while($row=mysqli_fetch_array($responce)){
    $index['garage_name']=$row['1'];
    $index['available_time']=$row['2'];
    $index['mobile']=$row['3'];
    $index['service']=$row['4'];
    $index['location']=$row['5'];


    array_push($result['data'], $index);
}
$result["success"]="1";
echo json_encode($result);



?>