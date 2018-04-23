package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.Session;
import com.example.jason.route_application_kotlin.features.container.ContainerActivity;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity implements MvpLogin.View{

    private final String debugTag = "debugTag";

    @Inject
    MvpLogin.Presenter presenter;

    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.login_btn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }

    @Override
    public Session getSession() {
        return new Session(this);
    }

    @OnClick(R.id.login_btn)
    @Override
    public void onLoginBtnClick() {
        loginBtn.setEnabled(false);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        presenter.loginBtnClick(username, password);
    }

    @Override
    public void showContainer() {
        Intent i = new Intent (this, ContainerActivity.class);
        startActivity(i);
    }

    @Override
    public void finishNetworkOperation() {
        loginBtn.setEnabled(true);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
