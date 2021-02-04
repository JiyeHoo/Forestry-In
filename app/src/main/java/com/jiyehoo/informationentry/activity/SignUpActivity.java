package com.jiyehoo.informationentry.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jiyehoo.informationentry.LoginActivity;
import com.jiyehoo.informationentry.MainActivity;
import com.jiyehoo.informationentry.R;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.Timer;
import java.util.TimerTask;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout mTilEmail, mTilPhone, mTilPwd;
    private Button mBtnRegister;
    private EditText mEtEmail, mEtPhone, mEtPwd;
    private TextView mTvHave;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_sign_up);

        bindView();
        setListener();
    }

    private void setListener() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validatePhone(mEtPhone.getText().toString())) {
                    mTilPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateAccount(mEtEmail.getText().toString())) {
                    mTilEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validatePassword(mEtPwd.getText().toString())) {
                    mTilPwd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mBtnRegister.setOnClickListener(v -> {
            mTilEmail.setErrorEnabled(false);
            mTilPwd.setErrorEnabled(false);

            if (validateAccount(mEtEmail.getText().toString())
                    && validatePassword(mEtPwd.getText().toString())
                    && validatePhone(mEtPhone.getText().toString())) {

                JumpingBeans.with(mTvTitle)
                        .makeTextJump(0, mTvTitle.getText().toString().length())
                        .setIsWave(true)
                        .setLoopDuration(2500)
                        .build();

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        });
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 3000);
            }

        });

        mTvHave.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void bindView() {
        mEtEmail = findViewById(R.id.et_register_email);
        mEtPhone = findViewById(R.id.et_register_phone);
        mEtPwd = findViewById(R.id.et_register_pwd);
        mTilEmail = findViewById(R.id.text_input_layout_email);
        mTilPhone = findViewById(R.id.text_input_layout_phone);
        mTilPwd  =findViewById(R.id.text_input_layout_password);
        mTvTitle = findViewById(R.id.tv_sign_up_title);
        mBtnRegister = findViewById(R.id.btn_register);
        mTvHave = findViewById(R.id.tv_goto_sign_in);
    }

    private void fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证用户名
     * @param account
     * @return
     */
    private boolean validateAccount(String account){
        if(TextUtils.isEmpty(account)){
            showError(mTilEmail,"邮箱不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(mTilPwd,"密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(mTilPwd,"密码长度为6-18位");
            return false;
        }

        return true;
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    private boolean validatePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showError(mTilPhone,"手机号不能为空");
            return false;
        }

        if (phone.length() != 11) {
            showError(mTilPhone,"请输入正确的手机号");
            return false;
        }

        return true;
    }
}