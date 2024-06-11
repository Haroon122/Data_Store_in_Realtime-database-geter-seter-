package com.example.realtimedatabase;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,age;
    Button save;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.editName);
        age=findViewById(R.id.editage);
        save=findViewById(R.id.saveBtn);
        databaseReference=FirebaseDatabase.getInstance().getReference("Data");
        progressDialog = new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedata();

            }

            private void savedata() {
                String sname=name.getText().toString().trim();
                String sage=age.getText().toString().trim();

                if (sname.isEmpty()||sage.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter both name and age!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    progressDialog.setTitle("Loading...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String id = databaseReference.push().getKey();
                    if (id != null) {
                        student student = new student(sname, sage);
                        databaseReference.child(id).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to generate id", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }
}