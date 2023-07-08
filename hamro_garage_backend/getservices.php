<?php
include 'conn.php';
    include 'validate.php';
   

if (isset($_POST['u_id'])) {
	$userid=$_POST['u_id'];
	# code...
}



$result=array();
$result['data']=array();


$select= "SELECT * from servicelist where u_id='$userid'"; 
$responce= mysqli_query($conn,$select);

while($row=mysqli_fetch_array($responce)){
	$index['id']=$row['0'];
	$index['service']=$row['1'];

	array_push($result['data'], $index);
}
$result["success"]="1";
echo json_encode($result);



?>