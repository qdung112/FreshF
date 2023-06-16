package com.ecommerce_app.freshF;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Detail_Product_Activity extends AppCompatActivity {

    ImageView detailImg;
    MaterialButton addToCartBtn;
    TextView name,description,price;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product_activity);
        setTitle("Detail Product");

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        ProductModel product = (ProductModel) getIntent().getSerializableExtra("detailInfo");

        detailImg = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        addToCartBtn = findViewById(R.id.btn_add_to_cart);

        Glide.with(getApplicationContext()).load(product.getImg_url()).into(detailImg);
        name.setText(product.getName());
        description.setText(product.getDescription());
        price.setText(product.getPrice());

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(product);
            }
        });

    }

    private void addToCart(ProductModel product){
       /* String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());*/

        final HashMap<String,Object> cart = new HashMap<>();

        cart.put("name",product.getName());
        cart.put("price",product.getPrice());
        //cart.put("currentDate",saveCurrentDate);
        //cart.put("currentTime",saveCurrentTime);
        cart.put("img_url",product.getImg_url());

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Cart").add(cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Detail_Product_Activity.this,"Added To Cart",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
