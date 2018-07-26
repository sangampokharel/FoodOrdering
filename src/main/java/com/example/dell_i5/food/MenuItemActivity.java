package com.example.dell_i5.food;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        recyclerView=(RecyclerView) findViewById(R.id.foodList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Item");
        mAuth=FirebaseAuth.getInstance();


        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    Intent intent=new Intent(MenuItemActivity.this,ClientLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.menu,FoodViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.setName(model.getFname());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String food_key=getRef(position).getKey().toString();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleFoodActivity=new Intent(MenuItemActivity.this,SingleFoodActivity.class);
                        singleFoodActivity.putExtra("foodId",food_key);
                        startActivity(singleFoodActivity);
                    }
                });
            }
        };

        recyclerView.setAdapter(FBRA);
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder{
      View mview;

        public FoodViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setName(String name){
            TextView food_name=(TextView) mview.findViewById(R.id.foodName);
            food_name.setText(name);
        }


        public void setDesc(String desc){
            TextView food_desc=(TextView) mview.findViewById(R.id.fooddesc);
            food_desc.setText(desc);
        }

        public void setPrice(String price){
            TextView food_price=(TextView) mview.findViewById(R.id.foodprice);
            food_price.setText(price);
        }

        public void setImage(Context ctx, String image){
            ImageView food_image=mview.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);


        }


    }

}
