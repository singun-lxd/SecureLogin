package com.singun.securelogin.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.singun.securelogin.sdk.UserLogin;
import com.singun.securelogin.user.LoginInfo;

import java.util.List;

/**
 * A login screen that offers quick secure login.
 */
public class ClientLoginActivity extends AppCompatActivity
        implements SecureLoginAdapter.OnItemClickListener {
    private UserLogin mUserLogin;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private CheckLoginTask mAuthTask = null;

    // UI references.
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mUserLogin = UserLogin.getInstance(getApplicationContext());
        attemptLoginCheck();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLoginCheck() {
        if (mAuthTask != null) {
            return;
        }
        mAuthTask = new CheckLoginTask();
        mAuthTask.execute((Void) null);
    }

    @Override
    public void onItemClick(int position, LoginInfo loginInfo) {
        UserLogin.getInstance(getApplicationContext()).quickLogin(loginInfo);
        finish();
        NavUtils.startMainActivity(this);
    }

    /**
     * Represents an asynchronous login/registration task used to quick login.
     */
    public class CheckLoginTask extends AsyncTask<Void, Void, List<LoginInfo>> {

        CheckLoginTask() {
        }

        @Override
        protected List<LoginInfo> doInBackground(Void... params) {
            return mUserLogin.getLoginDataList(ClientLoginActivity.this, true);
        }

        @Override
        protected void onPostExecute(final List<LoginInfo> loginInfoList) {
            mAuthTask = null;

            if (!loginInfoList.isEmpty()) {
                SecureLoginAdapter adapter = new SecureLoginAdapter(loginInfoList);
                adapter.setOnItemClickListener(ClientLoginActivity.this);
                mRecyclerView.setAdapter(adapter);
            } else {
                finish();
                Toast.makeText(ClientLoginActivity.this, R.string.text_error, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

