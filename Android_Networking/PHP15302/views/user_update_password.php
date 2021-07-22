<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
$data = json_decode(file_get_contents("php://input"));

include_once '../controllers/user.php';

$email = $data->email;
$password = $data->password;
$confirm_password = $data->confirm_password;

if ($email && $password == $confirm_password) {
    // validation
    $status = (new UserController())->updatePassword($email, $password);
    if ($status) {
        http_response_code(200);
        echo json_encode(array(
            "status"=> true,
            "message"=> "Success"
        ));
    } else {
        http_response_code(404);
        echo json_encode(array(
            "status"=> false,
            "message"=> "Failed"
        ));
    }
}
else{
    http_response_code(404);
    echo json_encode(array(
        "status"=> false,
        "message"=> "Failed"
    ));
}


?>