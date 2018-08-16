package com.example.mrrs.mob402_asm_ps05854.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.JSONParser;
import com.example.mrrs.mob402_asm_ps05854.activity.HomeActivity;
import com.example.mrrs.mob402_asm_ps05854.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadAllProductsTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jParser;
    ArrayList<Product> listProducts;
    JSONArray products = null;
    public LoadAllProductsTask(Context context) {
        this.context = context;
        jParser = new JSONParser();
        listProducts = new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_products, "GET", params);
        try {
            int success = jsonObject.getInt("success");
            Log.d("All Products: ","sucess: "+ success);
            if (success == 1) {
                Log.d("All Products: ", jsonObject.toString());
                products = jsonObject.getJSONArray(Constants.TAG_PRODUCTS);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);
                    // Storing each json item in variable
                    String pid = c.getString(Constants.TAG_PID);
                    String name = c.getString(Constants.TAG_NAME);
                    String price = c.getString(Constants.TAG_PRICE);
                    String image = c.getString(Constants.TAG_image);
                    // creating new Product

                    Log.d("All Products: ", "ID Product: "+pid +"| name: "+name+"| price: "+price+"| image: "+image);
                    Product product = new Product();
                    product.setIdProduct(pid);
                    product.setNameProduct(name);
                    product.setPriceProduct(price);
                    product.setImageProduct(image);
                    listProducts.add(product);
                }
            } else {
                // no products found
                // Launch Add New product Activity
                Intent intent = new Intent(context, HomeActivity.class);
                // Closing all previous activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }

    }
}

