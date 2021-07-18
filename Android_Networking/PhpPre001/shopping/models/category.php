<?php
class Category
{
    // Columns
    private $id;
    private $name;
    function __construct($id, $name)
    {
        $this->id = $id;
        $this->name = $name;
    }
    function getName()
    {
        return $this->name;
    }
    function getId()
    {
        return $this->id;
    }
}
?>