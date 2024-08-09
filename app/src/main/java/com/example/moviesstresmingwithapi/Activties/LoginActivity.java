package com.example.moviesstresmingwithapi.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moviesstresmingwithapi.R;

public class LoginActivity extends AppCompatActivity {
 private EditText userEdit,passEdit;
 private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        userEdit = findViewById(R.id.editTextUser);
        passEdit = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(view ->{
                if(userEdit.getText().toString().isEmpty() || passEdit.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill your usr and password", Toast.LENGTH_SHORT).show();
                }else if (userEdit.getText().toString().equals("dalal")  && passEdit.getText().toString().equals("Ss234234")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }else{
                    Toast.makeText(LoginActivity.this, "Your user and password is not correct", Toast.LENGTH_SHORT).show();
                }
        });

    }
}