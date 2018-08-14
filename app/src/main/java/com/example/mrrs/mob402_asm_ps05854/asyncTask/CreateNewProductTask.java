package com.example.mrrs.mob402_asm_ps05854.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateNewProductTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    public CreateNewProductTask(Context context){
        this.context = context;
        jsonParser = new JSONParser();
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
        Log.d("create_response", "Doing back ground");
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsIUser = new HashMap<>();
        hsIUser.put("idUser",strings[0]);
        params.add(hsIUser);
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put("name",strings[1]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put("price",strings[2]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put("description",strings[3]);
        params.add(hsDes);
        HashMap<String, String> hsImage = new HashMap<>();
        hsImage.put("image",strings[4]);
        params.add(hsImage);
        // getting JSON Object
        // Note that create product url accepts POST Method
        Log.d("create_response", params.toString());
        JSONObject jsonObject =
                jsonParser.makeHttpRequest(Constants.url_create_products,"POST",params);
        Log.d("create_response",jsonObject.toString());
        try {
            int success = jsonObject.getInt(Constants.TAG_SUCCESS);
            if(success == 1){

                // Successfully created product
                Log.d("create_response","Create complete");
                Toast.makeText(context, "create complete", Toast.LENGTH_SHORT).show();

//                Intent intent = new
//                        Intent(context,AllProductsActivity.class);
//                context.startActivity(intent);
                ((Activity)context).finish();
                // closing Create product screen
            }
        }catch (Exception e){

        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
