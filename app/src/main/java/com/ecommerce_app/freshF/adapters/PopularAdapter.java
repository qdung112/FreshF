package com.ecommerce_app.freshF.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce_app.freshF.Detail_Product_Activity;
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.model.ProductModel;

import java.util.List;

public class PopularAdapter  extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> popularModelList;


    public PopularAdapter(Context context, List<ProductModel> popularModelList) {
        this.context = context;
        this.popularModelList = popularModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularModelList.get(position).getName());
        holder.description.setText(popularModelList.get(position).getDescription());
        holder.price.setText(popularModelList.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_Product_Activity.class);
                intent.putExtra("detailInfo",popularModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView popImg;
        TextView name,description,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            price = itemView.findViewById(R.id.pop_price);
        }
    }
}
