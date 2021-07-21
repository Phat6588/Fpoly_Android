<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
$data = json_decode(file_get_contents("php://input"));



include_once '../controllers/user.php';

if ($data->email && $data->password) {
    $access_token = (new UserController())->login($data->email, $data->password);
    if ($access_token) {
        http_response_code(200);
        echo json_encode(array(
            "is_auth"=> true,
            "access_token"=> $access_token
        ));
    } else {
        http_response_code(401);
        echo json_encode(array(
            "is_auth"=> false,
            "access_token"=> null
        ));
    }
}
else {
    http_response_code(401);
    echo json_encode(array(
        "is_auth"=> false,
        "access_token"=> "nullllll"
    ));
}

?>