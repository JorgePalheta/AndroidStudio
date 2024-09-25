package com.example.teste1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Dataclass> datalist;

    public MyAdapter(List<Dataclass> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(view); // Corrigido nome da classe
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String imageUrl = datalist.get(position).getDataImage();
        Log.d("ImageURL", "URL da imagem: " + imageUrl);  // Log para verificar a URL
        Glide.with(context)
                .load(imageUrl)
                .into(holder.recImage);

        holder.recTitle.setText(datalist.get(position).getDataTitle());
        holder.recDesc.setText(datalist.get(position).getDataDesc());
        holder.recLang.setText(datalist.get(position).getDataLang());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", datalist.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Descrição", datalist.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", datalist.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key", datalist.get(holder.getAdapterPosition()).getKey());


                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size(); // Adicionado ponto e vírgula
    }
    public void searchDataList(ArrayList<Dataclass> searchList) {
        datalist = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recLang = itemView.findViewById(R.id.recLang);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}