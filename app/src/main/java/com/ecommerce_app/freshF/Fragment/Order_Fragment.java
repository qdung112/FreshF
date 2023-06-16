package com.ecommerce_app.freshF.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.View_All_Category_Activity;
import com.ecommerce_app.freshF.adapters.ViewAllCateAdapter;
import com.ecommerce_app.freshF.adapters.ViewAllOrderAdapter;
import com.ecommerce_app.freshF.model.OrderModel;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Order_Fragment extends Fragment {

    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    ViewAllOrderAdapter viewAllAdapter;
    List<OrderModel> orderList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order,container,false);

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.order_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        orderList = new ArrayList<>();
        viewAllAdapter = new ViewAllOrderAdapter(getActivity(),orderList);
        recyclerView.setAdapter(viewAllAdapter);

        fireStore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OrderModel orderModel = new OrderModel(document.getId());
                                orderList.add(orderModel);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        return root;
    }
}
