<?php 
session_start();
$database="hamro_garage";
$username="root";
$password="";
$server="localhost";

$conn=new mysqli($server,$username,$password,$database);

if($conn->connect_error){
	die("connection failed".$conn->connect_error);
}

?>