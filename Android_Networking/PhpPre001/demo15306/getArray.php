<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$arr = array();
$e = array(
    "id" => "PS12345",
    "name" => "Nguyễn"
);
array_push($arr, $e);

$e = array(
    "id" => "PS54321",
    "name" => "Nguyễn Viết Châu"
);
array_push($arr, $e);

echo json_encode($arr);

?>