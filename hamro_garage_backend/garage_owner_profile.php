<?php
include 'conn.php';
    include 'validate.php';

   

if (isset($_POST['u_id'])) {
	$userid=$_POST['u_id'];
	# code...

}
 // where u_id='$userid'


$result=array();
$result['data']=array();


$select= "SELECT * from user_signup where id='$userid'"; 
$responce= mysqli_query($conn,$select);

while($row=mysqli_fetch_array($responce)){

	$index['fullname']=$row['1'];
	$index['address']=$row['2'];
	$index['mobile']=$row['3'];
	$index['email']=$row['4'];
	

	array_push($result['data'], $index);
}
$result["success"]="1";
echo json_encode($result);



?>