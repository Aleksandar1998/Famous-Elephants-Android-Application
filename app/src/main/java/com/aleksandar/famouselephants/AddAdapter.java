package com.aleksandar.famouselephants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aleksandar.famouselephants.R;

import java.util.ArrayList;

public class AddAdapter extends  RecyclerView.Adapter<AddAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Elephant> elephants;
    Activity activity;

    AddAdapter(Activity activity, Context context,ArrayList elephants){
        this.context = context;
        this.elephants = elephants;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_elephants_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Elephant elephant = elephants.get(position);
        holder.name.setText(String.valueOf(elephant.name));
        holder.image.setImageURI(Uri.parse(elephant.img));
        holder.note.setText(String.valueOf(elephant.note));
        holder.sex.setText(String.valueOf(elephant.sex));
        holder.dob.setText(String.valueOf(elephant.dob));
        holder.dod.setText(String.valueOf(elephant.dod));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateElephant.class);
                intent.putExtra("name",elephant.name);
                intent.putExtra("note",elephant.note);
                intent.putExtra("img",elephant.img);
                intent.putExtra("sex",elephant.sex);
                intent.putExtra("dob",elephant.dob);
                intent.putExtra("dod",elephant.dod);
                intent.putExtra("id",elephant.id);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return elephants.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView name, note, sex, dob, dod;
        ImageView image;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameSingle2);
            image = itemView.findViewById(R.id.img2);
            note = itemView.findViewById(R.id.noteSingle2);
            sex = itemView.findViewById(R.id.sexSingle2);
            dob = itemView.findViewById(R.id.dobSingle2);
            dod = itemView.findViewById(R.id.dodSingle2);

            mainLayout = itemView.findViewById(R.id.mainLayout2);
        }
    }
}