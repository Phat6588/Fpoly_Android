<?php

namespace App\Http\Controllers;
use App\Models\products;
use Illuminate\Http\Request;

class ProductController extends Controller
{
    // get all
    public function get()
    {
        $p = products::get()->toJson(JSON_PRETTY_PRINT);
        return response($p, 200);
    }

    public function insert(Request $request)
    {
        $p = new products;
        $p->name = $request->name;
        $p->price = $request->price;
        $p->image = $request->image;
        $p->save();
        return response()->json(["message" => "insert success"], 200);
    }

}
