package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.CreateNewProductTask;
import com.example.mrrs.mob402_asm_ps05854.asyncTask.LoadAllProductsTask;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LoadAllProductsTask loadAllProductsTask;
    CreateNewProductTask newProductTask;
    ViewFlipper ViewFlipper_home;
    ImageView img1_home,img2_home,img3_home;
    Animation in,out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initControll();
        initEvent();
        initDisplay();

    }

    private void initData() {
        loadAllProductsTask = new LoadAllProductsTask(this);
        loadAllProductsTask.execute();
    }

    private void initEvent() {
        img1_home.setOnClickListener(this);
        img2_home.setOnClickListener(this);
        img3_home.setOnClickListener(this);
    }

    private void initDisplay() {
        displayViewFipper();
        LoadImage("https://images.hgmsites.net/hug/2017-volvo-s90_100579091_h.jpg", img1_home);
        LoadImage("https://di-uploads-pod6.s3.amazonaws.com/landroverwestchester/uploads/2016/08/2017-Land-Rover-Evoque-SE.jpg", img2_home);
        LoadImage("https://lc-cdn.sixt.io/Images/Cars/BMW/USA_BMW_7er_L/Content_Box/half-660x330/A0197012.jpg", img3_home);
    }

    private void LoadImage(String url, ImageView imageView) {
        Picasso.with(this).load(url)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(imageView);
    }

    private void displayViewFipper() {
        in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        ViewFlipper_home.setInAnimation(in);
        ViewFlipper_home.setOutAnimation(out);
        ViewFlipper_home.setFlipInterval(3000);
        ViewFlipper_home.setAutoStart(true);

    }

    private void initControll() {
        ViewFlipper_home = findViewById(R.id.ViewFlipper_home);
        img1_home = findViewById(R.id.img1_home);
        img2_home = findViewById(R.id.img2_home);
        img3_home = findViewById(R.id.img3_home);
    }



    public void INTENT(Class c){
        Intent intent = new Intent(HomeActivity.this, c);
        startActivity(intent);
    }

    private void AddToCart(String userId, String name, String price, String description, String urlImage) {
        newProductTask = new CreateNewProductTask(this);
        newProductTask.execute(userId,name,price, description, urlImage);
    }

    @Override
    public void onClick(View v) {
        ViewFlipper_home.startFlipping();
        ViewFlipper_home.setFlipInterval(3000);
        switch (v.getId()){
            case R.id.img1_home:

                INTENT(CartActivity.class);
                break;
            case R.id.img2_home:
                INTENT(CartActivity.class);
                break;
            case R.id.img3_home:
                INTENT(CartActivity.class);
                break;
        }
    }
}
