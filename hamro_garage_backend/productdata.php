<?php
include 'conn.php';
$result=array();
$result['data']=array();
$select="SELECT *from servicelist";
$responce= mysqli_query($conn,$select);

while($row=mysqli_fetch_array($responce)){
	$index['id']=$row['0'];
	$index['service']=$row['1'];

	array_push($result['data'], $index);
}
$result["success"]="1";
echo json_encode($result);



?>