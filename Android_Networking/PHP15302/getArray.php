<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$arr = array();
$o = array(
    "id" => "PS12345",
    "name" => "Nguyễn Anh Hùng"
);
array_push($arr, $o);

$o = array(
    "id" => "PS12346",
    "name" => "Lý Tiểu Long"
);
array_push($arr, $o);

$o = array(
    "id" => "PS12347",
    "name" => "Châu Nhuận Phát"
);
array_push($arr, $o);

$o = array(
    "id" => "PS12348",
    "name" => "Châu Tinh Trì"
);
array_push($arr, $o);



echo json_encode($arr)
// php -S 127.0.0.1:8081
?>