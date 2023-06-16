package com.ecommerce_app.freshF.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.model.ProductModel;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productList;


    public OrderDetailAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(productList.get(position).getName());
        holder.price.setText(productList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_all_item_img);
            name = itemView.findViewById(R.id.view_all_item_name);
            price = itemView.findViewById(R.id.view_all_item_price);
        }
    }
}

