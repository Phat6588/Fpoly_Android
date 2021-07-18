<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../controllers/product_controller.php';

include_once '../config/core.php';
include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';
include_once '../helpers/token.php';

use \Firebase\JWT\JWT;

$data = json_decode(file_get_contents("php://input"));

$access_token = getBearerToken();

if ($access_token) {
    try {
        JWT::decode($access_token, Constant::MY_SECRET_KEY, array('HS256'));
        $products = (new ProductController())->get();
        http_response_code(200);
        echo json_encode($products);
    }
    catch (Exception $e) {
        http_response_code(401);
        echo json_encode(array(
            "message" => "Access denied.",
            "status" => false
        ));
    }
}
else {
    http_response_code(401);
    echo json_encode(array(
        "message" => "Access denied.",
        "error" => false
    ));
}
?>


















