package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {
   EditText kullaniciadi;
   Button kayit;
   FirebaseDatabase firebaseDatabase;
   DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        kullaniciadi=findViewById(R.id.kullaniciadi);
        kayit=findViewById(R.id.kayit);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        kayit.setOnClickListener(v -> {
            String username=kullaniciadi.getText().toString();
            kullaniciadi.setText("");
            ekle(username);
        });
    }
    public  void ekle( final String kadi){
        reference.child("Kullanıcılar").child(kadi).child("kullaniciadi").setValue(kadi).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"giriş başarılı",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(GirisActivity.this,MainActivity.class);
                intent.putExtra("kadi",kadi);
                startActivity(intent);
            }
        });

    }
    public void kayitol(){

    }
}