<?php 
include 'conn.php';
if (isset($_POST['id'])) {
	$id=$_POST['id'];
	
}
$sql="SELECT * FROM garage_info WHERE s_id=$id";
$result = $conn->query($sql);
if ($result->num_rows>0) {
header('Content-Type: application/json');
echo json_encode(true);

	# code...
}
else{
	header('Content-Type: application/json');
echo json_encode(false);
}
 ?>
