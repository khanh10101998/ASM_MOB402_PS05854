package com.example.mrrs.mob402_asm_ps05854.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.mrrs.mob402_asm_ps05854.R;

public class ItemNameActivity extends AppCompatActivity {
    ViewFlipper ViewFlipper_item_name;
    Animation in,out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_name);
        initControl();
        initDisplay();
    }

    private void initDisplay() {
        displayViewFipperItem();
    }

    private void initControl() {
        ViewFlipper_item_name = findViewById(R.id.ViewFlipper_item_name);
    }

    private void displayViewFipperItem() {
        in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        ViewFlipper_item_name.setInAnimation(in);
        ViewFlipper_item_name.setOutAnimation(out);
        ViewFlipper_item_name.setFlipInterval(3000);
        ViewFlipper_item_name.setAutoStart(true);
    }
}
