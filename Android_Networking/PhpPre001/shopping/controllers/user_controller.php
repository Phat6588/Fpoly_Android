<?php

    include_once '../services/user_service.php';

    include_once '../config/core.php';
    include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
    include_once '../../libs/php-jwt-master/src/ExpiredException.php';
    include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
    include_once '../../libs/php-jwt-master/src/JWT.php';

    use \Firebase\JWT\JWT;

    class UserController{   
        
        public function login($username, $password){
           $user = (new UserService())->login($username);           
            if ($user) {
                $check = password_verify($password, $user->getPassword());
                if ($check) {
                    $token = array(
                        "id" => $user->getId(),
                        "email" => $user->getEmail(),
                    );
                    $access_token = JWT::encode($token, Constant::MY_SECRET_KEY);
                    return $access_token;
                }
            }
            return null;
        }
    }   
?>