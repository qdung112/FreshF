package com.ecommerce_app.freshF;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce_app.freshF.adapters.ViewAllProductAdapter;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Detail_Order_Activity extends AppCompatActivity {

    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    ViewAllProductAdapter viewAllAdapter;
    List<ProductModel> productList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order_activity);

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(Detail_Order_Activity.this));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        productList = new ArrayList<>();
        viewAllAdapter = new ViewAllProductAdapter(this,productList);
        recyclerView.setAdapter(viewAllAdapter);
        setTitle("Order Detail");

        String orderId = getIntent().getStringExtra("orderId");
        fireStore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productList.add(productModel);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
     }
}
