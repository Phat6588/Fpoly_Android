<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../controllers/user_controller.php';

$data = json_decode(file_get_contents("php://input"));

if ($data->username && $data->password) {
   $access_token = (new UserController())->login($data->username, $data->password);   
   if($access_token){
        http_response_code(200);
        echo json_encode(
            array(
                "access_token" => $access_token,
                "is_auth" => true
            )
        );
   }
   else {
        http_response_code(401);
        echo json_encode(
            array(
                "access_token" => null,
                "is_auth" => false
            )
        );
   }
}
else {
    http_response_code(401);
    echo json_encode(
        array(
            "access_token" => null,
            "is_auth" => false
        )
    );
}
?>