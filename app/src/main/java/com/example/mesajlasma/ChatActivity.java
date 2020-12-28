package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String userName,othername;
    ImageView backimage,send;
    TextView name;
    EditText chatEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chat_recycler;
    mesajAdapter mesajAdapter;
    List<mesajModel > list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMessage();
    }
    public void tanimla(){
        list=new ArrayList<>();
        userName=getIntent().getExtras().getString("userName");
        othername=getIntent().getExtras().getString("othername");
        Log.i("alınandeğerler:",userName+"--"+othername);
        name=(TextView)findViewById(R.id.name);
        send=(ImageView)findViewById(R.id.send);
        backimage=(ImageView)findViewById(R.id.back_image);
        chatEditText=(EditText)findViewById(R.id.chatEditText);
        name.setText(othername);
        backimage.setOnClickListener(v -> {
            Intent intent=new Intent(ChatActivity.this,MainActivity.class);
            intent.putExtra("kadi",userName);
            startActivity(intent);

        });
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        send.setOnClickListener(v -> {
            String mesaj=chatEditText.getText().toString();
            chatEditText.setText("");
            mesajGonder(mesaj);
    });
        chat_recycler=(RecyclerView)findViewById(R.id.chat_recycler);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(ChatActivity.this,1);// recycler layoutmanager ile çalışır
        chat_recycler.setLayoutManager(layoutManager);
        mesajAdapter=new mesajAdapter(ChatActivity.this,list,ChatActivity.this,userName);
        chat_recycler.setAdapter(mesajAdapter);
    }
    public void mesajGonder(String text){
       final  String key=reference.child("Mesajlar").child(userName).child(othername).push().getKey(); // yeni mesaj geldiğinde eskisini kaybetmemek için
      final  Map messageMap=new HashMap<>();
        messageMap.put("text",text);
        messageMap.put("from",userName);
        reference.child("Mesajlar").child(userName).child(othername).child(key).setValue(messageMap).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                reference.child("Mesajlar").child(othername).child(userName).child(key).setValue(messageMap).addOnCompleteListener(task1 -> {

                });
            }

        });

    }
    public void loadMessage(){
        reference.child("Mesajlar").child(userName).child(othername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mesajModel mesajModel=snapshot.getValue(mesajModel.class);// snapshot dataları almak için kullanılır
                list.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                chat_recycler.scrollToPosition(list.size()-1);

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