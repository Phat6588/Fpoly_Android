<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


$o = array(
    "id" => "PS12345",
    "name" => "Nguyễn Anh Hùng"
);

echo json_encode($o)
// php -S 127.0.0.1:8081
?>

