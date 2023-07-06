<?php
if (isset($_POST['email'])&& isset($_POST['password'])) {
        require_once"conn.php";
        require_once"validate.php";
        $email=validate($_POST['email']);
        $password=validate($_POST['password']);
        $type=validate($_POST['type']);
        $sql="select * from user_signup where email='$email' and password='$password' and type='$type'";
        $result=$conn->query($sql);

        if ($result->num_rows> 0) {
            echo"success";
        }else{
            echo "fail";
        }

}
?>