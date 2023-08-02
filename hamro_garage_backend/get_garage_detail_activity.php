<?php
include 'conn.php';
include 'validate.php';

// Check if latitude and longitude are provided in the POST request
if (isset($_POST['latitude']) && isset($_POST['longitude'])) {
    $latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];


    $result=array();
    $result['data']=array();


    $select = "SELECT * FROM garage_info WHERE latitude = '$latitude' AND longitude = '$longitude'";
    $responce= mysqli_query($conn,$select);

    while($row=mysqli_fetch_array($responce)){
        $index['garage_name']=$row['1'];
        $index['mobile']=$row['3'];
        $index['service']=$row['4'];
        $index['location']=$row['5'];


        array_push($result['data'], $index);
    }
    $result["success"]="1";
    echo json_encode($result);

}

?>