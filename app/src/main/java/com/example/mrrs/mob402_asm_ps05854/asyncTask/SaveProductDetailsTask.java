package com.example.mrrs.mob402_asm_ps05854.asyncTask;

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

public class SaveProductDetailsTask extends AsyncTask<String, String,String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    boolean boolSuccess = false;
    public SaveProductDetailsTask(Context context) {
        this.context = context;
        jsonParser = new JSONParser();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Saving product...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsPid = new HashMap<>();
        hsPid.put(Constants.TAG_PID, strings[0]);
        params.add(hsPid);
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put(Constants.TAG_NAME, strings[1]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put(Constants.TAG_PRICE, strings[2]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put(Constants.TAG_DESCRIPTION, strings[3]);
        params.add(hsDes);
        // sending modified data through http request
        // Notice that update product url accepts POST method
        JSONObject object =
                jsonParser.makeHttpRequest(Constants.url_update_product, "POST", params);
        try{
            int success = object.getInt(Constants.TAG_SUCCESS);
            if(success == 1){
                // successfully updated
                boolSuccess = true;

            }else {
                // failed to update product
                boolSuccess = false;
                Log.d("save_product","save err");

            }
        }catch (Exception e){
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
        if(boolSuccess){
            Log.d("save_product","save ok");
            Toast.makeText(context, "save oke", Toast.LENGTH_SHORT).show();
        }
    }
}
