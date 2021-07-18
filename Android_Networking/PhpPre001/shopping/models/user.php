<?php
class User
{
    // Columns
    private $id;
    private $username;
    private $email;
    private $password;
    function __construct($id, $username, $email, $password)
    {
        $this->id = $id;
        $this->username = $username;
        $this->email = $email;
        $this->password = $password;
    }
    function getUsername()
    {
        return $this->username;
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