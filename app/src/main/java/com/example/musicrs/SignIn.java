package com.example.musicrs;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    private EditText name, surname, email, password;
    private FirebaseAuth mAuth;
    private Button crearCuenta;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        name = findViewById(R.id.signIn_name);
        surname = findViewById(R.id.signIn_surname);
        email = findViewById(R.id.signIn_email);
        password = findViewById(R.id.signIn_password);
        crearCuenta = findViewById(R.id.signIn_bt_signin);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        mAuth = FirebaseAuth.getInstance();
        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(email.getText().toString(), password.getText().toString());
            }
        });

    }

    private void createAccount(String email, String password) {
        if (checkFields()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registro correcto",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                obtenerDatosUsuario(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                        // ...

                    });
        } else {
            Toast.makeText(getApplicationContext(), "Debes completar todos los campos",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerDatosUsuario(FirebaseUser user) {

        String UID = user.getUid();
        String name = user.getDisplayName();
        insertarUsuarioBBDD(UID);
    }

    private void insertarUsuarioBBDD(String UID) {

        myRef = database.getReference(UID);
        if (!name.getText().equals("") && !surname.getText().equals("")) {
            myRef.child("Nombre").setValue(name.getText().toString());
            myRef.child("Apellidos").setValue(surname.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Los campos nombre y apellido no pueden estar vacíos",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkFields() {
        boolean vacio = true;
        if (name.getText().equals("") && surname.getText().equals("") && email.getText().equals("") && password.getText().equals("")) {
            vacio = false;
        }
        return vacio;
    }
}
