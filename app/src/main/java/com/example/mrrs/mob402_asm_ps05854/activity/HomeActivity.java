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
import com.example.mrrs.mob402_asm_ps05854.asyncTask.LoadAllProductsTask;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LoadAllProductsTask loadAllProductsTask;
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
