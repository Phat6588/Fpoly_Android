<?php
class Reset
{
    // Columns
    private $id;
    private $email;
    private $token;
    private $created;
    private $available;
    function __construct($email, $created, $available)
    {
        $this->email = $email;
        $this->created = $created;
        $this->available = $available;
    }
    function getEmail()
    {
        return $this->email;
    }
    function getCreated()
    {
        return $this->created;
    }
    function getAvailable()
    {
        return $this->available;
    }
}
?>