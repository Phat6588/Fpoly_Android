<?php
class Product
{
    // Columns
    private $id;
    private $name;
    private $price;
    private $created;
    private $category_id;
    private $image_url;
    function __construct($id, $name, $price, $created, $category_id, $image_url)
    {
        $this->id = $id;
        $this->name = $name;
        $this->price = $price;
        $this->created = $created;
        $this->category_id = $category_id;
        $this->image_url = $image_url;
    }
    function getName()
    {
        return $this->name;
    }
    function getId()
    {
        return $this->id;
    }
    function getPrice()
    {
        return $this->price;
    }
    function getCreated()
    {
        return $this->created;
    }
    function getCategoryId()
    {
        return $this->category_id;
    }
    function getImageUrl()
    {
        return $this->image_url;
    }
    public function toString()
    {
        return $this->name;
    }
}
?>