package com.aleksandar.famouselephants;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aleksandar.famouselephants.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
   Context context;
   ArrayList<JSONObject> list;

    public Adapter(Context context, ArrayList<JSONObject> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate((R.layout.card),parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            final JSONObject json = list.get(position);
            holder.name.setText(json.getString("name"));
            holder.note.setText(json.getString("note"));
            Picasso.get().load(json.getString("image")).into(holder.image);
            holder.mainLayout.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleElephant.class);
                    intent.putExtra("object",json.toString());
                    context.startActivity(intent);
                }
            } );
        }
        catch (Exception e){ }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, note;
        ConstraintLayout mainLayout;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameSingle);
            note = itemView.findViewById(R.id.noteSingle);
            image = itemView.findViewById(R.id.img);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
