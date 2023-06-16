package com.ecommerce_app.freshF;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class Placed_Oder_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    Button backToHome;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placed_order_activity);

        backToHome = findViewById(R.id.btn_back_to_home);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        List<ProductModel> productList = (List<ProductModel>) getIntent().getSerializableExtra("productList");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if(productList != null && productList.size() > 0){
            final HashMap<String,Object> order = new HashMap<>();
            for (ProductModel product : productList){
                order.put("name",product.getName());
                order.put("price",product.getPrice());
                //cart.put("currentDate",saveCurrentDate);
                //cart.put("currentTime",saveCurrentTime);
                order.put("img_url",product.getImg_url());
                order.put("date",dtf.format(now));

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Cart").document(product.getId()).delete();

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Order").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Placed_Oder_Activity.this,"Order Successful",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Placed_Oder_Activity.this, "error" + task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Placed_Oder_Activity.this,Main_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
