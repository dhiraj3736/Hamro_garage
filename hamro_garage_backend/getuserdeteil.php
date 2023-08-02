<?php
include 'conn.php';
    include 'validate.php';
   


 // where u_id='$userid'


$result=array();
$result['data']=array();


$select= "SELECT * from user_signup"; 
$responce= mysqli_query($conn,$select);

while($row=mysqli_fetch_array($responce)){
	$index['id']=$row['0'];
	$index['name']=$row['1'];
	$index['address']=$row['2'];
	$index['mobile']=$row['3'];
	$index['email']=$row['4'];
	$index['type']=$row['7'];
	

	array_push($result['data'], $index);
}
$result["success"]="1";
echo json_encode($result);



?>