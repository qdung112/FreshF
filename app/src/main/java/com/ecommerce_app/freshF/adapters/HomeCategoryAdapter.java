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
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.View_All_Product_By_Type_Activity;
import com.ecommerce_app.freshF.model.HomeCategoryModel;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private Context context;
    private List<HomeCategoryModel> homeCategoryModelList;

    public HomeCategoryAdapter(Context context, List<HomeCategoryModel> homeCategoryModelList) {
        this.context = context;
        this.homeCategoryModelList = homeCategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cate_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String type = homeCategoryModelList.get(position).getName();
        Glide.with(context).load(homeCategoryModelList.get(position).getImg_url()).into(holder.cateImg);
        holder.name.setText(type);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, View_All_Product_By_Type_Activity.class);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView cateImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_cate_name);
            cateImg = itemView.findViewById(R.id.home_cate_img);
        }
    }
}
