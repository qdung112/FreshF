package com.ecommerce_app.freshF.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ecommerce_app.freshF.Detail_Order_Activity;
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.model.OrderModel;
import java.util.List;


public class ViewAllOrderAdapter extends RecyclerView.Adapter<ViewAllOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> orderList;


    public ViewAllOrderAdapter(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public  ViewAllOrderAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllOrderAdapter.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_Order_Activity.class);
                intent.putExtra("orderId",orderList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}