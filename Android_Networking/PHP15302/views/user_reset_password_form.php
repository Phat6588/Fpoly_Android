<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset password</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header text-center">Reset Password</div>
            <div class="card-body">
                <?php 
                    $email = $_GET['email'];
                    $token = $_GET['token'];
                    if ($email && $token) {
                        include_once '../controllers/user.php';
                        $check_token = (new UserController())->checkToken($email, $token);
                        if ($check_token) {?>
                            <form action="user_reset_password.php" method="post">
                                <input type="hidden" name="email" value="<?php echo $email; ?>">
                                <input type="hidden" name="token" value="<?php echo $token; ?>">
                                <div class="form-group">
                                    <label for="">Password</label>
                                    <input type="password" name="password" class="form-control" id="">
                                </div>
                                <div class="form-group">
                                    <label for="">Confirm Password</label>
                                    <input type="password" name="confirm_password" class="form-control" id="">
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="form-control btn btn-primary" id="">
                                </div>
                            </form>
                        <?php } else {?>
                            <b>This link is not available</b>
                        <?php }
                    }
                ?>
            </div>
        </div>
    </div>
</body>
</html>