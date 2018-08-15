package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.RequestInterface;
import com.example.mrrs.mob402_asm_ps05854.model.ServerRequest;
import com.example.mrrs.mob402_asm_ps05854.model.ServerResponse;
import com.example.mrrs.mob402_asm_ps05854.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btn_register;
    private EditText et_email, et_password, et_name, et_username;
    private TextView tv_login;
    private ProgressBar progress;

    LinearLayout view_container;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initControl();


    }

    private void initData() {

    }

    private void initControl() {
        view = findViewById(android.R.id.content);
        view_container = (LinearLayout) findViewById(R.id.view_container);
        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        progress = findViewById(R.id.progress);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        view_container.setOnClickListener(this);
    }

    private void initEvent() {

    }

    private void initDisplay() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
                this.finish();
                break;
            }
            case R.id.view_container:{
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;
            }
            case R.id.btn_register: {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name, email, username, password);

                } else {

                    Snackbar.make(view, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void registerProcess(String name, String email, String username, String password) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Log.d("HongKhanh", user.getUsername());
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(view, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }
}
