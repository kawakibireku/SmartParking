package iottelkom.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import iottelkom.smartparking.utils.DbUser;

/**
 * Created by kawakibireku on 11/13/17.
 */

public class CreateAccount extends AppCompatActivity {
    int i;
    int status;
    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        System.out.println("WEW");

        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("Clicked");
                DbUser db = new DbUser(getApplicationContext());
                db.open();

                EditText etUsername = (EditText) findViewById(R.id.etNewUsername);
                EditText etPassword = (EditText) findViewById(R.id.etNewPassword);

                String uname = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                ArrayList<DbUser.Akun> akun = db.getAllUser();
                i = akun.size();
                if(i == 0){
                    System.out.println("input");
                    db.insertAkun(uname, pass);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast t = Toast.makeText(getApplicationContext(), "Akun berhasil dibuat", Toast.LENGTH_LONG);
                    t.show();
                }else{
                    for (DbUser.Akun index : akun) {
                        System.out.println("for");
                        if (uname.equals(index.username.toString())) {
                            System.out.println("cek username");
                            status = 1;
                            Toasty.error(getApplicationContext(), String.format("User Already Exist"), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }


            }
        });

    }




}
