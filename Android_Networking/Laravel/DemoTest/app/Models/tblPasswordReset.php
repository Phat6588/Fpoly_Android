<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class tblPasswordReset extends Model
{
    use HasFactory;
    protected $table = 'tbl_password_resets';

    protected $fillable = ['email', 'token', 'created_ats'  ];
}
