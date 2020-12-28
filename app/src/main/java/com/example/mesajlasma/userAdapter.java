package com.example.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder>{
    Context context;
    List<String> list;
    Activity activity;// bu sayfa activity değil o yüzden bunu aldık bu activityle startActivity fonksiyonunu çağırıp diğer activitye geçeceğiz
   String userName;
    public userAdapter(Context context, List<String> list, Activity activity,String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName=userName;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=  LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
      return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // atama işlemi ,liste kaç elemanlıysa o kadar dönücek ve yazdıracak
        holder.textView.setText(list.get(position).toString());
        // kişiye tıklanırsa
        holder.user_analayout.setOnClickListener(v -> {
         Intent intent=new Intent(activity,ChatActivity.class);
         intent.putExtra("userName",userName);
         intent.putExtra("othername",list.get(position).toString());
         activity.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         TextView textView;
         LinearLayout user_analayout;

      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          textView=itemView.findViewById(R.id.userName);
          user_analayout=itemView.findViewById(R.id.user_analayout);
      }
  }


}
