<?php
echo !extension_loaded('openssl')?"Not Available":"Available";
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$data = json_decode(file_get_contents("php://input"));


if ($data->email) {
    $emailId = $data->email;

    $token = md5($emailId) . rand(10, 9999);
    $expFormat = mktime(date("H"), date("i"), date("s"), date("m"), date("d") + 1, date("Y"));

    $expDate = date("Y-m-d H:i:s", $expFormat);

    $link = "<a href='http://127.0.0.1:8081/reset-password.php?key=" . $emailId . "&token=" . $token . "'>Click To Reset password</a>";

    require("../libs/PHPMailer-master/src/PHPMailer.php");
    require("../libs/PHPMailer-master/src/SMTP.php");
    require("../libs/PHPMailer-master/src/Exception.php");

    $mail = new PHPMailer\PHPMailer\PHPMailer();

    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;
    // GMAIL username
    $mail->Username = "chandev.test";
    // GMAIL password
    $mail->Password = "6h-FA7W!UHa%Za@";
    $mail->SMTPSecure = "ssl";
    // sets GMAIL as the SMTP server
    $mail->Host = "ssl://smtp.gmail.com"; 
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From = 'chandev.test@gmail.com';
    $mail->FromName = 'Chan Nguyen';
    $mail->AddAddress('channn3@fpt.edu.vn', 'Chan Nguyen');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body    = 'Click On This Link to Reset Password ' . $link . '';
    if ($mail->Send()) {
        echo "Check Your Email and Click on the link sent to your email";
    } else {
        echo "Mail Error - >" . $mail->ErrorInfo;
    }
}
