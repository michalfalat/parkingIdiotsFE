package com.example.asus.parkingidiots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InitialActivity extends Activity {
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        name = (EditText) findViewById(R.id.inputName);
        UserModel u = ConfigContainer.getUser();
        if(u!= null) {
            name.setText(u.getName());
        }
    }

    public void saveUser(View v) {
        ConfigContainer.saveUser(name.getText().toString());
        finish();
    }

    @Override
    public void onBackPressed() {
        if(ConfigContainer.getUser() == null) {//exit whole app when nothing set
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }

}
