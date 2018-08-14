package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.RequestInterface;
import com.example.mrrs.mob402_asm_ps05854.model.ServerRequest;
import com.example.mrrs.mob402_asm_ps05854.model.ServerResponse;
import com.example.mrrs.mob402_asm_ps05854.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegister;
    private Button btn_login;
    private EditText edt_email, edt_password;
    private TextView tv_register;
//    private ProgressBar progressBar;
    private SharedPreferences pref;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initControl();
        initEvent();
        initDisplay();
    }

    private void initData() {
    }

    private void initControl() {
//        progressBar = findViewById(R.id.progress);
        view = findViewById(android.R.id.content);
        tvRegister = findViewById(R.id.tv_register);
        pref = getPreferences(0);
        btn_login =  findViewById(R.id.btn_login);
        edt_email = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
//        progressBar = (ProgressBar) findViewById(R.id.progress);
        btn_login.setOnClickListener(this);
    }

    private void initEvent() {
        tvRegister.setOnClickListener(this);
    }

    private void initDisplay() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
//                    progressBar.setVisibility(View.VISIBLE);

                    loginProcess(email, password);
                } else {
                    Snackbar.make(view, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_register: {
               INTENT(Register.class);
                break;
            }
        }
    }

    private void loginProcess(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setUsername(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);

        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putString(Constants.EMAIL, resp.getUser().getEmail());
                    editor.putString(Constants.NAME, resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID, resp.getUser().getUnique_id());
                    Log.d("LOGIN","email :"+resp.getUser().getEmail() + " name: "+resp.getUser().getName());
                    editor.apply();
                    INTENT(MainActivity.class);
                //    Toast.makeText(Login.this, "Login succes", Toast.LENGTH_SHORT).show();
                }
//                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private void INTENT(Class c) {
        Intent intent = new Intent(Login.this, c);
        startActivity(intent);
    }
}
