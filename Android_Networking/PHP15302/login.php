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
$username = $data->username;
$password = $data->password;
// select username, password from user where username = ?
// khong co ---> 401
// neu co ---> so sanh password
    // giong nhau ---> login success
    // khong giong ---> 401
// jwt


if ($username == "admin") {
    $hash = password_hash("123", PASSWORD_BCRYPT);
    $is_password_valid = password_verify($password, $hash);
    if ($is_password_valid) {
        $token = array(
            "id" => "123",
            "name" => "John Wick",
            "username" => "johnwick",
            "role" => "admin"
        );
        $access_token = JWT::encode($token, $MY_SECRET_KEY);
        http_response_code(200);
        echo json_encode(array("access_token"=> $access_token));
    } else {
        http_response_code(401);
        echo json_encode(array("message"=>"Password not valid"));
    }
}
else {
    http_response_code(401);
    echo json_encode(array("message"=>"Username not valid"));
}
// php -S 127.0.0.1:8081
?>