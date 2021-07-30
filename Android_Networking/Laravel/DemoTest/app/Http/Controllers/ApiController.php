<?php

namespace App\Http\Controllers;

use App\Models\tblProducts;
use Illuminate\Http\Request;

class ApiController extends Controller
{
    public function get() {
        // logic to get all students goes here
        $p = tblProducts::get()->toJson(JSON_PRETTY_PRINT);
        return response($p, 200);
      }
  
      public function create(Request $request) {
        // logic to create a student record goes here
        $student = new tblProducts;
        $student->name = $request->name;
        $student->price = $request->price;
        $student->image_url = $request->image_url;
        $student->category_id = $request->category_id;
        $student->save();

        return response()->json([
            "message" => "product record created"
        ], 201);
      }
  
      public function getById($id) {
        // logic to get a student record goes here
        if (tblProducts::where('id', $id)->exists()) {
            $p = tblProducts::where('id', $id)->get()->toJson(JSON_PRETTY_PRINT);
            return response($p, 200);
          } else {
            return response()->json([
              "message" => "Product not found"
            ], 404);
          }
      }
  
      public function update(Request $request, $id) {
        // logic to update a student record goes here
        if (tblProducts::where('id', $id)->exists()) {
            $p = tblProducts::find($id);
            $p->name = is_null($request->name) ? $p->name : $request->name;
            $p->price = is_null($request->price) ? $p->price : $request->price;
            $p->image_url = is_null($request->image_url) ? $p->image_url : $request->image_url;
            $p->category_id = is_null($request->category_id) ? $p->category_id : $request->category_id;

            $p->save();
    
            return response()->json([
                "message" => "records updated successfully"
            ], 200);
            } else {
            return response()->json([
                "message" => "records not found"
            ], 404);
            
        }
      }
  
      public function delete ($id) {
        // logic to delete a student record goes here
        if(tblProducts::where('id', $id)->exists()) {
            $student = tblProducts::find($id);
            $student->delete();
    
            return response()->json([
              "message" => "records deleted"
            ], 202);
          } else {
            return response()->json([
              "message" => "product not found"
            ], 404);
          }
      }
}
