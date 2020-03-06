package com.inc.fsi.fnn.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inc.fsi.fnn.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etvName;
    EditText etvPhone;
    Button btnDone;
    TextView tvSwitchAccount;
    TextView txtUserGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etvName = findViewById(R.id.txt_nameField);
        etvPhone = findViewById(R.id.txt_phoneField);
        btnDone = findViewById(R.id.btn_Done);
        tvSwitchAccount = findViewById(R.id.tv_switchAccount);
        txtUserGreeting = findViewById(R.id.txt_userGreeting);

        btnDone.setOnClickListener(this);
        tvSwitchAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Done:
                if (verifyUserInput()) {
                    finalizeRegistration();
                }
                break;
            case R.id.tv_switchAccount:

//                Intent mainActivityIntent = new Intent(RegistrationActivity.this,
//                        Launcher.class);
//                startActivity(mainActivityIntent);
//                RegistrationActivity.this.finish();
        }
    }

    private boolean verifyUserInput(){
        boolean isVerified = true;

        String name = etvName.getText().toString();
        String id = etvPhone.getText().toString();

        if(!name.equals("timi")){
            etvName.setHint("Invalid username");
            etvName.setHintTextColor(getResources().getColor(R.color.colorError));
            etvName.setText("");
            isVerified = false;
        }

        if(!id.equals("timi32")){
            etvPhone.setHint("Invalid password");
            etvPhone.setHintTextColor(getResources().getColor(R.color.colorError));
            etvPhone.setText("");
            isVerified = false;
        }

        return isVerified;
    }



    private void finalizeRegistration(){
        Intent mainActivityIntent = new Intent(RegistrationActivity.this,
                MainActivity.class);
        startActivity(mainActivityIntent);
        RegistrationActivity.this.finish();
    }

    private void updateUI(boolean loading){
        btnDone.setVisibility(loading ? View.INVISIBLE : View.VISIBLE);
    }


}
