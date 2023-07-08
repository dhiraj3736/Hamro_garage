<?php

if (isset($_POST['email'])&& isset($_POST['password'])) {
		include 'conn.php';
		include 'validate.php';
		$email=validate($_POST['email']);
		$password=validate($_POST['password']);
		$type=validate($_POST['type']);
		$sql="select * from user_signup where email='$email' and password='$password' and type='$type'";
		$result=$conn->query($sql);
		$row = $result->fetch_assoc();
		if ($result->num_rows> 0) {
				$_SESSION['admin_id']=$row['id'];

				$session_id=$_SESSION['admin_id'];
				$data["result"]="sucess";
				$data["session_id"]=$_SESSION['admin_id'];
				
				$jsonData=json_encode($data);
				header('Content-Type:application/json');
				echo $jsonData;

// echo "$session_id";
   

// Output the session ID as a response header


			//echo"success";

		}else{
			//echo "fail";
			$data["result"]="sucess";
				$jsonData=json_encode($data);
				header('Content-Type:application/json');
				echo $jsonData;

		}
		

}
?>