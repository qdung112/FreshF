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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class View_All_Product_By_Type_Activity extends AppCompatActivity {

    FirebaseFirestore fireStore;
    RecyclerView recyclerView;
    ViewAllProductAdapter viewAllAdapter;
    List<ProductModel> productList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_activity);
        String type = getIntent().getStringExtra("type");
        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(View_All_Product_By_Type_Activity.this));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        productList = new ArrayList<>();
        viewAllAdapter = new ViewAllProductAdapter(this,productList);
        recyclerView.setAdapter(viewAllAdapter);

        setTitle(type);
        fireStore.collection("AllProducts").whereEqualTo("type",type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productList.add(productModel);
                                viewAllAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}