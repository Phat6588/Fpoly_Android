<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$data = json_decode(file_get_contents("php://input"));

$ten = $data->ten;
$luong = $data->luong;

$thuNhap = 0;
if ($luong > 20) {
    # code...
    $thuNhap = $luong - $luong * 0.1;
}
else{
    $thuNhap = $luong - $luong * 0.05;
}

$e = array(
    "ten" => $ten,
    "luong" => $luong,
    "thuNhap" => $thuNhap
);

echo json_encode($e);


?>