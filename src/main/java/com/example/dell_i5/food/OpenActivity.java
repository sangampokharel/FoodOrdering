package com.example.dell_i5.food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        recyclerView=(RecyclerView) findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRef= FirebaseDatabase.getInstance().getReference().child("orders");


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Order,OrderViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.singleorderlayout,
                OrderViewHolder.class,
                mRef

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
                    viewHolder.setUserName(model.getUsername());
                    viewHolder.setItemName(model.getItemname());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public OrderViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }


        public void setUserName(String userName){
            TextView username_content=mview.findViewById(R.id.orderUsername);
            username_content.setText(userName);
        }


        public void setItemName(String itemNames){
            TextView ItemName_content=mview.findViewById(R.id.orderItemName);
            ItemName_content.setText(itemNames);
        }
    }

}
