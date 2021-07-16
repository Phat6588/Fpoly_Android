<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$data = json_decode(file_get_contents("php://input"));
$id = $data->id;
$name = $data->name;

$o = array(
    "id" => $id,
    "name" => $name
);

echo json_encode($o)
// php -S 127.0.0.1:8081
?>