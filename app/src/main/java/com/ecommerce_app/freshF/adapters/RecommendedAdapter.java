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

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> recommendedModelList;


    public RecommendedAdapter(Context context, List<ProductModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImg_url()).into(holder.recImg);
        holder.name.setText(recommendedModelList.get(position).getName());
        holder.description.setText(recommendedModelList.get(position).getDescription());
        holder.price.setText(recommendedModelList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_Product_Activity.class);
                intent.putExtra("detailInfo",recommendedModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView recImg;
        TextView name,description,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recImg = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_des);
            price = itemView.findViewById(R.id.rec_price);
        }
    }
}
