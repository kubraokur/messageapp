package com.example.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class mesajAdapter extends RecyclerView.Adapter<mesajAdapter.ViewHolder>{
    Context context;
    List<mesajModel> list;
    Activity activity;// bu sayfa activity değil o yüzden bunu aldık bu activityle startActivity fonksiyonunu çağırıp diğer activitye geçeceğiz
   String userName;
   Boolean state=false;
   int view_send=1,view_receive=2;

    public mesajAdapter(Context context, List<mesajModel> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName=userName;
        state=false;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        if(viewType==view_send){
           view= LayoutInflater.from(context).inflate(R.layout.send_layout,parent,false);
            return  new ViewHolder(view);
        }
        else {
          view=  LayoutInflater.from(context).inflate(R.layout.receive_layout,parent,false);
            return  new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // atama işlemi ,liste kaç elemanlıysa o kadar dönücek ve yazdıracak
        holder.textView.setText(list.get(position).getText().toString());
        // kişiye tıklanırsa
      /*  holder.user_analayout.setOnClickListener(v -> {
         Intent intent=new Intent(activity,ChatActivity.class);
         intent.putExtra("userName",userName);
         intent.putExtra("othername",list.get(position).toString());
         activity.startActivity(intent);

        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         TextView textView;


      public ViewHolder(@NonNull View itemView) {
          super(itemView);

          if(state==true){
              textView=itemView.findViewById(R.id.send_textview);
          }
          else{
              textView=itemView.findViewById(R.id.receive_textview);
          }
      }
  }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getFrom().equals(userName)){
            state=true;
            return view_send;

        }
        else{
            state=false;
            return view_receive;
        }

    }
}
