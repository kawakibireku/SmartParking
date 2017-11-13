package iottelkom.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import iottelkom.smartparking.utils.SMPreferences;
import iottelkom.smartparking.utils.DbUser;

/**
 * Created by kawakibireku on 11/7/17.
 */

public class LoginActivity extends AppCompatActivity{
    private String TAG;

    private Button btnLogin;
    private EditText etxtUname;
    private EditText etxtPass;
    private Button btnCreate;

    private SMPreferences smPreferences;
    private CheckBox cbKeepSignIn;
    int i;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        TAG = this.getClass().getSimpleName();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        etxtUname = (EditText) findViewById(R.id.etxtUsername);
        etxtPass = (EditText) findViewById(R.id.etxtPassword);

        cbKeepSignIn = (CheckBox) findViewById(R.id.cbKeepSignIn);

        smPreferences = new SMPreferences(this);

        String lastLoginStatus = smPreferences.read(SMPreferences.KEY_LOGIN);
        if(lastLoginStatus!=null && lastLoginStatus.equals(SMPreferences.LOGGED_IN)){
            String username = smPreferences.read(SMPreferences.KEY_USERNAME);
            Toasty.success(getApplicationContext(), String.format("Welcome %s",username), Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Log.d(TAG,"logged in");
        } else {
            Log.d(TAG,"gak login");
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etxtUname.getText().toString();
                String password = etxtPass.getText().toString();

                DbUser db = new DbUser(getApplicationContext());
                db.open();
                i=0;
                ArrayList<DbUser.Akun> akun = db.getAllUser();
                for(DbUser.Akun index : akun) {
                    if (username.equals(index.username.toString()) && password.equals(index.password.toString())) {
                        if (cbKeepSignIn.isChecked()) {
                            smPreferences.store(SMPreferences.KEY_LOGIN, SMPreferences.LOGGED_IN);
                            smPreferences.store(SMPreferences.KEY_USERNAME, username);
                        }
                        status = 1;
                    }
                    if(status == 1){
                        Toasty.success(getApplicationContext(), String.format("Welcome %s", username), Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toasty.error(getApplicationContext(), String.format("Failed to authenticate"), Toast.LENGTH_SHORT, true).show();
                    }
                }
                /*
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                */
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}
