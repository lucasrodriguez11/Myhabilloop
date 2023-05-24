package com.internet.habiloop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.internet.habiloop.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password,passwordconfir, phone;
    Button mButtonRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.usernamereg);
        email = findViewById(R.id.txtInRegEmail);
        phone = findViewById(R.id.numtelreg);
        fullname = findViewById(R.id.fullnamereg);
        password = findViewById(R.id.txtInPasswordReg);
        passwordconfir = findViewById(R.id.txtInConfirPasword);

        mButtonRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        /*mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
    }

    private void register() {
        String Tusername = username.getText().toString();
        String Temail = email.getText().toString();
        String Tpassword  = password.getText().toString();
        String TconfirmPassword = passwordconfir.getText().toString();

        if (!Tusername.isEmpty() && !Temail.isEmpty() && !Tpassword.isEmpty() && !TconfirmPassword.isEmpty()) {
            if (isEmailValid(Temail)) {
                if (Tpassword.equals(TconfirmPassword)) {
                    if (Tpassword.length() >= 6) {
                        createUser(Tusername,Temail, Tpassword);
                    }
                    else {
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Las contraseña no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Insertaste todos los campos pero el correo no es valido", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(final String username, final String email, final String password) {
        ;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("username", username);
                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "El usuario se almaceno correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "No se pudo almacenar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*
     * VERIFICAR QUE SEA UN EMAIL VALIDO
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
