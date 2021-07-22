<?php

    include_once '../services/user.php';

    include_once '../libs/php-jwt-master/src/BeforeValidException.php';
    include_once '../libs/php-jwt-master/src/ExpiredException.php';
    include_once '../libs/php-jwt-master/src/SignatureInvalidException.php';
    include_once '../libs/php-jwt-master/src/JWT.php';

    include_once '../libs/PHPMailer-master/src/PHPMailer.php';
    include_once '../libs/PHPMailer-master/src/SMTP.php';
    include_once '../libs/PHPMailer-master/src/Exception.php';

    include_once '../configs/core.php';

    use \Firebase\JWT\JWT;
use PHPMailer\PHPMailer\PHPMailer;

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

        public function sendEmailResetPassToUser($email)
        {
            $_email = $this->user_service->getByEmail($email);
            if ($_email) {
                $token = $this->user_service->generatePassResetToken($email);
                if ($token) {
                    // gui email cho user
                    return $this->sendEmail($email, $token);
                }
            }
            return false;
        }

        private function sendEmail ($email, $token){
            $link = "<a href='http://127.0.0.1:8081/views/user_reset_password_form.php?email="
                    . $email ."&token=" .$token. "'  > Click to reset password </a>  ";

            $mail = new PHPMailer();
            $mail->CharSet = "utf-8";
            $mail->isSMTP();
            $mail->SMTPAuth = true;
            $mail->Username = "chandev.test";
            $mail->Password = "6h-FA7W!UHa%Za@";
            $mail->SMTPSecure = "ssl";
            $mail->Host = "ssl://smtp.gmail.com";
            $mail->Port = "465";
            $mail->From = "chandev.test@gmail.com";
            $mail->FromName = "Lý Tiểu Long";
            $mail->addAddress($email, "Hello");
            $mail->Subject = "Reset password";
            $mail->isHTML(true);
            $mail->Body = "Click on this link to reset password " .$link."";


            if ($mail->Send()) {
                return true;
            }
            return false;
            // https://www.google.com/settings/security/lesssecureapps
            // http://127.0.0.1:8081/views/user_reset_password_form.php?email=channn3@fpt.edu.vn&token=cbdf5db3cc7854b0f7286e7c53d753ee2359
        }


        public function checkToken($email, $token)
        {
            return $this->user_service->checkToken($email, $token);
        }

        public function updatePasswordAndToken($token, $email, $password)
        {
            $check_token = $this->user_service->checkToken($email, $token);
            if ($check_token) {
                $hash_password = password_hash($password, PASSWORD_BCRYPT);
                $change_password = $this->user_service->changePassword($email, $hash_password);
                if ($change_password) {
                    return $this->user_service->clearToken($token);
                }
            }
            return false;
        }

        public function updatePassword($email, $password)
        {
            $hash_password = password_hash($password, PASSWORD_BCRYPT);
            return $this->user_service->changePassword($email, $hash_password);
        }
    }

?>