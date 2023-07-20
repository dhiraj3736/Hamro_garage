<?php

if (isset($_POST['email'])&& isset($_POST['password'])) {
		include 'conn.php';
		include 'validate.php';
		$email=validate($_POST['email']);
		$password=validate($_POST['password']);
		$type=validate($_POST['type']);
		$sql="select * from admin_login where email='$email' and password='$password'";
		$result=$conn->query($sql);
		$row = $result->fetch_assoc();
		if ($result->num_rows> 0) {
				
// echo "$session_id";
   

// Output the session ID as a response header


			echo"success";

		}else{
			echo "fail";
		
			

		}
		

}
?>