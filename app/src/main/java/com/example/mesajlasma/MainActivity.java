package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String userName;
    RecyclerView userRecycler;
    userAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        listele();
    }
    public void tanimla(){
         userName=getIntent().getExtras().getString("kadi");
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        list=new ArrayList<>(); //liste için bellekte yer ayırdık
        userRecycler=(RecyclerView)findViewById(R.id.user_recycler);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(MainActivity.this,2);
        userRecycler.setLayoutManager(layoutManager);
        userAdapter=new userAdapter(MainActivity.this,list,MainActivity.this,userName);
        userRecycler.setAdapter(userAdapter);

    }
    public void listele(){
        reference.child("Kullanıcılar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //  Kullanıcının kendi isminin listede olmaması için
                if(!snapshot.getKey().equals(userName)){
                    list.add(snapshot.getKey());
                    Log.i("Kullanıcı",snapshot.getKey());
                    userAdapter.notifyDataSetChanged(); //liste güncelledikçe adapterde güncellenir
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    
}