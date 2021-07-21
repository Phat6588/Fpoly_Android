<?php

    include_once '../services/user_service.php';

    include_once '../config/core.php';
    include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
    include_once '../../libs/php-jwt-master/src/ExpiredException.php';
    include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
    include_once '../../libs/php-jwt-master/src/JWT.php';

    include_once '../../libs/PHPMailer-master/src/PHPMailer.php';
    include_once '../../libs/PHPMailer-master/src/SMTP.php';
    include_once '../../libs/PHPMailer-master/src/Exception.php';

    

    use \Firebase\JWT\JWT;

    class UserController{   
        
        public function login($email, $password){
           $user = (new UserService())->login($email);           
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

        public function getByToken($token, $email)
        {
            $service = new UserService();            
            return $service->getByToken($token, $email);
        }

        // change password
        public function updatePasswordAndToken($email, $token, $password)
        {            
            $service = new UserService();
            $reset_token = $service->getByToken($token, $email);
            if ($reset_token) {               
               // di cap nhat trong db 2 bang                           
                $clear_token = $service->clearToken($token);
                if ($clear_token) {
                    $hash_password = password_hash($password, PASSWORD_BCRYPT);     
                    return $service->changePassword($email,$hash_password);
                }                
            }
            return false;
        }






        public function sendEmailResetLink($email)
        {
            $e = (new UserService())->getByEmail($email);
            if ($e) {
                $token = (new UserService())->generateToken($email);
                if ($token) {
                    // gui email
                    return $this->sendEmail($email, $token);
                }                
            }
            return false;
        }

        private function sendEmail($email, $token)
        {
            $link = "<a href='http://127.0.0.1:8081/shopping/views/user_reset_password_form.php?key=" . $email . "&token=" . $token . "'>Click To Reset password</a>";
            $mail = new PHPMailer\PHPMailer\PHPMailer();

            $mail->CharSet =  "utf-8";
            $mail->IsSMTP();
            $mail->SMTPAuth = true;
            $mail->Username = "chandev.test";
            $mail->Password = "6h-FA7W!UHa%Za@";
            $mail->SMTPSecure = "ssl";
            $mail->Host = "ssl://smtp.gmail.com"; 
            $mail->Port = "465";
            $mail->From = 'chandev.test@gmail.com';
            $mail->FromName = 'Chan Nguyen';
            $mail->AddAddress('channn3@fpt.edu.vn', 'Chan Nguyen');
            $mail->Subject  =  'Reset Password';
            $mail->IsHTML(true);
            $mail->Body    = 'Click On This Link to Reset Password ' . $link . '';
            if ($mail->Send()) {
                return true;            
            } 
            return false;
        }


    }   
?>