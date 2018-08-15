package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.LoadAllProductsTask;

public class HomeActivity extends AppCompatActivity {
    private ListView lvProducts;
    LoadAllProductsTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lvProducts = (ListView)findViewById(R.id.listProducts);
        task = new LoadAllProductsTask(HomeActivity.this,lvProducts);
        task.execute();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
