<?php

    include_once '../services/user.php';

    include_once '../libs/php-jwt-master/src/BeforeValidException.php';
    include_once '../libs/php-jwt-master/src/ExpiredException.php';
    include_once '../libs/php-jwt-master/src/SignatureInvalidException.php';
    include_once '../libs/php-jwt-master/src/JWT.php';

    include_once '../configs/core.php';

    use \Firebase\JWT\JWT;

    class UserController {

        private $user_service;

        public function __construct()
        {
            $this->user_service = new UserService();
        }

        public function register($email, $password, $confirm_password){
            if ($password != $confirm_password) {
                return false;
            }
            $hash_password = password_hash($password, PASSWORD_BCRYPT);
            $status = $this->user_service->register($email, $hash_password);
            return $status;
        }

        public function login($email, $password){
            $user = $this->user_service->getByEmail($email);
            if ($user) {
                $password_valid = password_verify($password, $user->getHashPassword());
                if ($password_valid) {
                    $token = array(
                        "id" => $user->getId(),
                        "email" => $user->getEmail()
                    );
                    $access_token = JWT::encode($token, Constant::MY_SECRET_KEY);
                    return $access_token;
                }
            }
            return null;
        }

    }

?>