<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once './configs/core.php';
include_once './libs/php-jwt-master/src/BeforeValidException.php';
include_once './libs/php-jwt-master/src/ExpiredException.php';
include_once './libs/php-jwt-master/src/SignatureInvalidException.php';
include_once './libs/php-jwt-master/src/JWT.php';
use \Firebase\JWT\JWT;
$data = json_decode(file_get_contents("php://input"));
$password = $data->password;
$username = $data->username;
// select username, password from user where username = ?
$hash_password = password_hash("123", PASSWORD_BCRYPT);
$is_password_valid = password_verify($password, $hash_password);
if ($username == "admin" && $is_password_valid) {
    $token = array(
        "data" => array(
            "id" => "123",
            "name" => "Châu Tinh Trì"
        )
    );
    http_response_code(200);
    $access_token = JWT::encode($token, $key);
    echo json_encode(array("access_token" => $access_token));
} else{
    http_response_code(401);
    echo json_encode(array("message" => "Login failed!!!"));
}

?>