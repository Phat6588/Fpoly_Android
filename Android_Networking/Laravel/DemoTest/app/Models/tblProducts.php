<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class tblProducts extends Model
{
    use HasFactory;
    protected $table = 'tbl_products';

    protected $fillable = ['name', 'price', 'image_url','category_id'  ];
}
