<?php
class User
{
    // Columns
    private $id;
    private $email;
    private $password;
    function __construct($id, $email, $password)
    {
        $this->id = $id;
        $this->email = $email;
        $this->password = $password;
    }
    function getId()
    {
        return $this->id;
    }
    function getPassword()
    {
        return $this->password;
    }
    function getEmail()
    {
        return $this->email;
    }
}
?>