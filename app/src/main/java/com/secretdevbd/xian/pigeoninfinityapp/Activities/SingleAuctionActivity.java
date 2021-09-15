package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretdevbd.xian.pigeoninfinityapp.Fragments.AuctionListFragment;
import com.secretdevbd.xian.pigeoninfinityapp.Functions.TimeSettings;
import com.secretdevbd.xian.pigeoninfinityapp.ItemClickListener;
import com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionEvent;
import com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionObject;
import com.secretdevbd.xian.pigeoninfinityapp.Models.Pigeon;
import com.secretdevbd.xian.pigeoninfinityapp.R;

import java.util.Date;
import java.util.List;

public class SingleAuctionActivity extends AppCompatActivity {

    String TAG = "XIAN";

    DatabaseReference databaseRef;
    AuctionObject auctionObject;

    RecyclerView RV_pigeonList;
    TextView TV_timer;

    ProgressBar PB_loadingPigeons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_auction);

        int auctionID = getIntent().getIntExtra("AUCTION_ID", 0);

        RV_pigeonList = findViewById(R.id.RV_pigeonList);
        TV_timer = findViewById(R.id.TV_timer);
        PB_loadingPigeons = findViewById(R.id.PB_loadingPigeons);

        getSingleAuctionDataFromFirebase(auctionID);

    }

    private void getSingleAuctionDataFromFirebase(int auc_id){

        databaseRef = FirebaseDatabase.getInstance().getReference("Auction");

        //limitToLast 500
        databaseRef.orderByKey().equalTo(""+auc_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    AuctionObject ab = ds.getValue(AuctionObject.class);
                    auctionObject = ab;

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                    RV_pigeonList.setLayoutManager(mLayoutManager);

                    RecyclerView.Adapter mRecycleAdapter = new MyRVAdapterPigeons(getApplicationContext(), auctionObject.getPigeons());
                    RV_pigeonList.setAdapter(mRecycleAdapter);
                }

                PB_loadingPigeons.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    public class MyRVAdapterPigeons extends RecyclerView.Adapter<MyRVAdapterPigeons.ViewHolder> {

        List<Pigeon> pigeonList;
        Context context;

        public MyRVAdapterPigeons(Context context, List<Pigeon> pigeonList) {
            super();
            this.context = context;
            this.pigeonList = pigeonList;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public MyRVAdapterPigeons.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_pigeon_for_list, viewGroup, false);
            MyRVAdapterPigeons.ViewHolder viewHolder = new MyRVAdapterPigeons.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyRVAdapterPigeons.ViewHolder viewHolder, final int i) {

            viewHolder.TV_pigeonName.setText(pigeonList.get(i).getPigeonRing()+" ("+pigeonList.get(i).getPigeonName()+")");
            viewHolder.TV_bredOffer.setText("Breeder: "+pigeonList.get(i).getBreedBy()+" Offered: "+pigeonList.get(i).getOfferBy());
            viewHolder.TV_auctionEnd.setText(pigeonList.get(i).getEndTime());
            viewHolder.TV_lastBid.setText("Bidder: "+pigeonList.get(i).getLastBidderName());
            viewHolder.TV_price.setText("Bid: "+pigeonList.get(i).getPrice()+" "+auctionObject.getAuctionEvent().getCurrency());

            String url = "https://pigeoninfinity.com/static/UPLOADS/AUCTION/"+pigeonList.get(i).getAuctionID()+"/"+pigeonList.get(i).getMainPic();
            Log.i(TAG, url);

            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_downloading_24)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(viewHolder.IV_pigeonPic);



            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongClick) {

                    if (isLongClick) {
                        //Toast.makeText(context, "#" + position + " (Long click)", Toast.LENGTH_SHORT).show();
                        //showPopupMenu(view,position);

                    } else {
                        /*Intent at = new Intent(context, SinglePigeonActivity.class);
                        at.putExtra("AUCTION_ID", pigeonList.get(position).getAuctionID());
                        at.putExtra("PIGEON_ID", pigeonList.get(position).getPigeonID());
                        startActivity(at);*/
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return pigeonList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public ImageView IV_pigeonPic;
            public TextView TV_pigeonName, TV_bredOffer, TV_auctionEnd, TV_lastBid, TV_price;
            Button btn_bid;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                IV_pigeonPic = itemView.findViewById(R.id.IV_pigeonPic);
                TV_pigeonName = itemView.findViewById(R.id.TV_pigeonName);
                TV_bredOffer = itemView.findViewById(R.id.TV_bredOffer);
                TV_auctionEnd = itemView.findViewById(R.id.TV_auctionEnd);
                TV_lastBid = itemView.findViewById(R.id.TV_lastBid);
                TV_price = itemView.findViewById(R.id.TV_price);
                btn_bid = itemView.findViewById(R.id.btn_bid);


                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }
        }

    }
}