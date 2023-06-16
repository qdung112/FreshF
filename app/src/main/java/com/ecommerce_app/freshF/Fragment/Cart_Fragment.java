package com.ecommerce_app.freshF.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce_app.freshF.Placed_Oder_Activity;
import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.adapters.CartAdapter;;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart_Fragment extends Fragment {

    RecyclerView cartRec;
    List<ProductModel> productList;
    CartAdapter cartAdapter;
    ProgressBar progressBar;
    Button buyNow;

    TextView totalPrice;

    FirebaseAuth auth;
    FirebaseFirestore db;

    double calTotalPrice = 0;

    String displayTotalPrice = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart,container,false);
        cartRec = root.findViewById(R.id.rec);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        buyNow = root.findViewById(R.id.btn_payment);
        totalPrice = root.findViewById(R.id.total_price);

        cartRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        productList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(),productList);
        cartRec.setAdapter(cartAdapter);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                String id = document.getId();
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setId(id);
                                calTotalPrice += Double.parseDouble(productModel.getPrice().replace("đ", ""))*1000;
                                productList.add(productModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);
                            displayTotalPrice = "Total Price: " + currencyFormatter.format(calTotalPrice);
                            totalPrice.setText(displayTotalPrice);
                        } else {
                            Toast.makeText(getActivity(),"error" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Placed_Oder_Activity.class);
                intent.putExtra("productList",(Serializable) productList);
                startActivity(intent);
            }
        });

        return root;
    }
}
