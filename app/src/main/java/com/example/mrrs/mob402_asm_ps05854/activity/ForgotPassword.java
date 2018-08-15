package com.example.mrrs.mob402_asm_ps05854.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    LinearLayout View_LinearLayout;
    TextView txtLogin;
    private Button btn_reset;
    private EditText et_email, et_code, et_password;
    private TextView tv_timer, forgot_tv_email;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initData();
        initControl();
        initEvent();
        initDisplay();
    }

    private void initData() {

    }

    private void initControl() {
        view = findViewById(android.R.id.content);
        txtLogin = findViewById(R.id.forgot_btn_move_to_login);
        View_LinearLayout = findViewById(R.id.View_LinearLayout);
        btn_reset = findViewById(R.id.forgot_btn_confirm);
        et_email = findViewById(R.id.forgot_edt_email);
        et_code = findViewById(R.id.forgot_edt_code);
        et_password = findViewById(R.id.forgot_edt_password);
        tv_timer = findViewById(R.id.forgot_tv_timer);
        forgot_tv_email = findViewById(R.id.forgot_tv_email);
        progress = findViewById(R.id.progress);
    }

    private void initEvent() {
        View_LinearLayout.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }

    private void initDisplay() {
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
    }

    private void initiateResetPasswordProcess(String email) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setEmail(email);
        Log.d("ForgotPassword", "email: " + user.getEmail());
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_INITIATE);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    Snackbar.make(view, resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                    Toast.makeText(ForgotPassword.this, resp.getMessage(), Toast.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    forgot_tv_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    isResetInitiated = true;
                    startCountdownTimer();

                } else {

                    Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(view, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(ForgotPassword.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void finishResetPasswordProcess(String email, String code, String password) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setCode(code);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_FINISH);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(ForgotPassword.this, resp.getMessage(), Toast.LENGTH_SHORT).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    Snackbar.make(view, resp.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                    Toast.makeText(ForgotPassword.this, resp.getMessage(), Toast.LENGTH_SHORT).show();

                    countDownTimer.cancel();
                    isResetInitiated = false;
                    goToLogin();

                } else {
                    Snackbar.make(view, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                }
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

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(ForgotPassword.this, "Time Out ! Request again to reset password.", Toast.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }


    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_btn_confirm: {
                Log.d("ForgotPassword", "clik button okey");

                if (!isResetInitiated) {
                    Log.d("ForgotPassword", "cos email");
                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                        Log.d("ForgotPassword", "co nhan email");
                        progress.setVisibility(View.VISIBLE);
                        initiateResetPasswordProcess(email);
                    } else {

                        Snackbar.make(view, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();
                    if (!code.isEmpty() && !password.isEmpty()) {
                        finishResetPasswordProcess(email, code, password);
                    } else {
                        Snackbar.make(view, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case R.id.forgot_btn_move_to_login:
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);

                break;
            case R.id.View_LinearLayout: {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            break;
        }
    }
}
