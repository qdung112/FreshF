package com.ecommerce_app.freshF.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce_app.freshF.Detail_Product_Activity;
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(productList.get(position).getName());
        holder.price.setText(productList.get(position).getPrice());

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Cart").document(productList.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    productList.remove(productList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context,"Error" + task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_Product_Activity.class);
                intent.putExtra("detailInfo",productList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price;

        Button removeItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            removeItem = itemView.findViewById(R.id.delete_item_cart);
        }
    }
}