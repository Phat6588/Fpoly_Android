<?php
    include_once '../services/user_service.php';
    include_once '../models/user.php';

    class UserController {

        private $user_service;
        public function __construct()
        {
            $this->user_service = new UserService();        
        }

        public function login($email, $password)
        {
            $user = $this->user_service->getUserByEmail($email);
            if ($user) {
                $password_valid = password_verify($password, $user->getHashPassword());
                if ($password_valid) {
                    return true;
                }
            }
            return false;
        }

        public function register($email, $password)
        {
            $user = $this->user_service->getUserByEmail($email);
            if (!$user) {
                $hash_password = password_hash($password, PASSWORD_BCRYPT);
                $new_user = new User(-1, $email, $hash_password);
                $status = $this->user_service->register($new_user);
                if ($status) {
                    return true;
                }
            }
            return false;
        }
    }
?>