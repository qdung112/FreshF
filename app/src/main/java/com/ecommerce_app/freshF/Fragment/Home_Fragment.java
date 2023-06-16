package com.ecommerce_app.freshF.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce_app.freshF.R;
import com.ecommerce_app.freshF.View_All_Category_Activity;
import com.ecommerce_app.freshF.View_All_Popular_Activity;
import com.ecommerce_app.freshF.adapters.HomeCategoryAdapter;
import com.ecommerce_app.freshF.adapters.PopularAdapter;
import com.ecommerce_app.freshF.adapters.RecommendedAdapter;
import com.ecommerce_app.freshF.model.HomeCategoryModel;
import com.ecommerce_app.freshF.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home_Fragment extends Fragment {


    ProgressBar progressBar;
    ScrollView scrollView;
    RecyclerView popRec,homeCateRec,recRec;
    List<ProductModel> popularList;
    PopularAdapter popularAdapter;

    Button seeAllPopProBtn;

    List<HomeCategoryModel> homeCategoryList;
    HomeCategoryAdapter homeCategoryAdapter;
    Button seeAllCateProBtn;
    List<ProductModel> recommendedList;
    RecommendedAdapter recommendedAdapter;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        popRec = root.findViewById(R.id.pop_rec);
        homeCateRec = root.findViewById(R.id.exp_rec);
        recRec = root.findViewById(R.id.rec_rec);

        progressBar = root.findViewById(R.id.progressBar);
        scrollView = root.findViewById(R.id.homeScrollView);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        seeAllCateProBtn = root.findViewById(R.id.view_all_explore_cate);
        // popular items
        seeAllPopProBtn = root.findViewById(R.id.view_all_popular_product);
        popRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(),popularList);
        popRec.setAdapter(popularAdapter);
        db = FirebaseFirestore.getInstance();
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel productModel = document.toObject(ProductModel.class);
                                popularList.add(productModel);
                                popularAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"error" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seeAllPopProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), View_All_Popular_Activity.class);
                startActivity(intent);
            }
        });

        // category items
        homeCateRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeCategoryList = new ArrayList<>();
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(),homeCategoryList);
        homeCateRec.setAdapter(homeCategoryAdapter);
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategoryModel homeCategoryModel = document.toObject(HomeCategoryModel.class);
                                homeCategoryList.add(homeCategoryModel);
                                homeCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"error" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        seeAllCateProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), View_All_Category_Activity.class);
                startActivity(intent);
            }
        });

        // recommended items
        recRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedList);
        recRec.setAdapter(recommendedAdapter);
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel recommendedModel = document.toObject(ProductModel.class);
                                recommendedList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"error" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
     return root;
    }
}
