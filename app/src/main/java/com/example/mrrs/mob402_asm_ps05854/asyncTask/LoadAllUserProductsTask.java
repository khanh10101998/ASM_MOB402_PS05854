package com.example.mrrs.mob402_asm_ps05854.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mrrs.mob402_asm_ps05854.AdapterProduct;
import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.JSONParser;
import com.example.mrrs.mob402_asm_ps05854.activity.HomeActivity;
import com.example.mrrs.mob402_asm_ps05854.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Han on 29/12/2016.
// */
public class LoadAllUserProductsTask extends AsyncTask<String, String, String> {
    Context context;
    ListView lvProducts;
    ProgressDialog pDialog;
    JSONParser jParser;
    ArrayList<Product> listProducts;
    JSONArray products = null;
    AdapterProduct adapterProduct;
    public LoadAllUserProductsTask(Context context, ListView lvProducts) {
        this.context = context;
        this.lvProducts = lvProducts;
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
                jParser.makeHttpRequest(Constants.url_user_products, "GET", params);
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
        adapterProduct = new AdapterProduct(context, listProducts);
        lvProducts.setAdapter(adapterProduct);
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "lick item: " + i, Toast.LENGTH_SHORT).show();

                // Edit product here
//                String pid = listProducts.get(i).getId();
//                Intent intent = new Intent(context, EditProductActivity.class);
//                intent.putExtra(Constants.TAG_PID, pid);
//                ((Activity) context).startActivityForResult(intent, 100);
                }
        });
    }
}

