package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.CreateNewProductTask;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.DeleteProductTask;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.GetUserProductDetailsTask;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.LoadAllUserProductsTask;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.SaveProductDetailsTask;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
CreateNewProductTask newProductTask;
    SaveProductDetailsTask saveProductDetailsTask;
    DeleteProductTask deleteProductTask;
    GetUserProductDetailsTask productDetailsTask;
    Button btnAdd, btnDelte, btnUpdate;
    Runnable myRunner;
    Handler handler;
    LoadAllUserProductsTask task;
    private ListView lvCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCart = findViewById(R.id.lv_cart);
        task = new LoadAllUserProductsTask(CartActivity.this,lvCart);
        task.execute();





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    private void UpdateProject(String productId, String productName, String productPrice, String productDescription) {
            // update table product
        saveProductDetailsTask = new SaveProductDetailsTask(this);
        saveProductDetailsTask.execute(productId,productName,productPrice,productDescription);
    }

    private void DeleteProduct(final String productId) {
        deleteProductTask = new DeleteProductTask(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deleting product...");
        builder.setMessage("Are you sure you want delete this product?");
        builder.setNegativeButton("Yes", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deleteProductTask.execute(productId);
                        dialogInterface.dismiss();

                        Toast.makeText(CartActivity.this,"Deleted",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setPositiveButton("No", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    private void AddToCart(String userId, String name, String price, String description, String urlImage) {
        newProductTask = new CreateNewProductTask(this);
        newProductTask.execute(userId,name,price, description, urlImage);
    }
}
