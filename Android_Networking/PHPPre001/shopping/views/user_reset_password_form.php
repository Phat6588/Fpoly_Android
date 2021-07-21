<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Reset Password In PHP MySQL</title>
        <!-- CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-header text-center">
                    Reset Password In PHP MySQL
                </div>
                <div class="card-body">
                <?php
                if($_GET['key'] && $_GET['token'])
                {
                    include_once '../controllers/user_controller.php';

                    $email = $_GET['key'];
                    $token = $_GET['token'];

                    $reset_token = (new UserController())->getByToken($token, $email);
                    
                    if ($reset_token) { ?>
                        <form action="user_reset_password.php" method="post">
                            <input type="hidden" name="email" value="<?php echo $email;?>">
                            <input type="hidden" name="token" value="<?php echo $token;?>">
                           
                            <div class="form-group">
                                <label for="exampleInputEmail1">Password</label>
                                <input type="password" name='password' class="form-control">
                            </div>                
                            <div class="form-group">
                                <label for="exampleInputEmail1">Confirm Password</label>
                                <input type="password" name='confirm_password' class="form-control">
                            </div>
                            <input type="submit" name="new-password" class="btn btn-primary">
                        </form>
                    <?php } else { ?>
                        <p>This forget password link has been expired</p>
                    <?php } } ?>
                </div>
            </div>
        </div>
    </body>
</html>