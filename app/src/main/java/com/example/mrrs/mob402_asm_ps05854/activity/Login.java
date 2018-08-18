package com.example.mrrs.mob402_asm_ps05854.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrrs.mob402_asm_ps05854.Constants;
import com.example.mrrs.mob402_asm_ps05854.R;
import com.example.mrrs.mob402_asm_ps05854.RequestInterface;
import com.example.mrrs.mob402_asm_ps05854.model.ServerRequest;
import com.example.mrrs.mob402_asm_ps05854.model.ServerResponse;
import com.example.mrrs.mob402_asm_ps05854.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    TextView tvRegister, txt_forgot_password;
    private Button btn_login;
    private EditText edt_email, edt_password;
    private TextView tv_register, txt_forgotpassword;
    LoginButton loginface;
    ImageView ivLoginFacebook, ivLoginGoogle;
    SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;
    private ProgressDialog mProgressDialog;
    //    private ProgressBar progressBar;
    private SharedPreferences pref;
    private static final String TAG = "Login";
    LinearLayout view_container;
    private static final int RC_SIGN_IN = 9001;

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
        initFacebookService();
        initGoogleService();
    }

    private void initControl() {
//        progressBar = findViewById(R.id.progress);
        view = findViewById(android.R.id.content);

        tvRegister = findViewById(R.id.tv_register);
        view_container = (LinearLayout) findViewById(R.id.view_container);
        pref = getPreferences(0);
        btn_login = findViewById(R.id.btn_login);
        edt_email = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        txt_forgotpassword =  findViewById(R.id.txt_forgotpassword);
//        progressBar = (ProgressBar) findViewById(R.id.progress);
        btn_login.setOnClickListener(this);
        ivLoginFacebook = findViewById(R.id.iv_login_facebook);
        ivLoginGoogle = findViewById(R.id.iv_login_google);
        ivLoginGoogle.setOnClickListener(this);
        ivLoginFacebook.setOnClickListener(this);
        view_container.setOnClickListener(this);
        txt_forgotpassword.setOnClickListener(this);
    }

    private void initEvent() {
        tvRegister.setOnClickListener(this);
    }

    private void initDisplay() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
//                    progressBar.setVisibility(View.VISIBLE);

                    loginProcess(email, password);
                } else {
                    Snackbar.make(view, "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.tv_register: {
//                    Toast.makeText(this,"CLICKED REGISTER", Toast.LENGTH_LONG).show();
                Intent my_i = new Intent(this, Register.class);
                startActivity(my_i);
                break;

            }
//            case R.id.txt_forgotpassword: {
////                    Toast.makeText(this,"CLICKED REGISTER", Toast.LENGTH_LONG).show();
//                Intent my_i = new Intent(this, ForgotPassword.class);
//                startActivity(my_i);
//                break;
//
//            }
            case R.id.view_container: {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;
            }
            case R.id.iv_login_facebook: {
                loginface.performClick();
                break;
            }
            case R.id.iv_login_google: {
                signInButton.performClick();
                break;
            }
            case R.id.txt_forgotpassword: {
                INTENT(ForgotPassword.class);
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
                    Log.d("LOGIN", "Id user: "+ resp.getUser().getUnique_id() +"email :" + resp.getUser().getEmail() + " name: " + resp.getUser().getName());
                    editor.apply();
                    INTENT(MainActivity.class);
                    Toast.makeText(Login.this, "Login succes", Toast.LENGTH_SHORT).show();
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

    private void initFacebookService() {
        loginface = (LoginButton) findViewById(R.id.loginface);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mrrs.mob402_asm_ps05854",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        callbackManager = CallbackManager.Factory.create();
        loginface.setReadPermissions("email");

        loginface.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //dang nhap thanh cong
                Toast.makeText(Login.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(Login.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initGoogleService() {
        // Button listeners
        findViewById(R.id.btnSignIn).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btnSignIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }

    public void checkServiceGoogle() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("Login", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(true);
            Toast.makeText(this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
            Toast.makeText(this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
        }
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void INTENT(Class c) {
        Intent intent = new Intent(Login.this, c);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Warning")
                    .setMessage("Are you sure you want to exit app?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with exit app
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.btnSignIn).setVisibility(View.GONE);
        } else {
            findViewById(R.id.btnSignIn).setVisibility(View.VISIBLE);
        }
    }
}
