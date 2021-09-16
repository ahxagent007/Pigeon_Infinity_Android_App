package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.secretdevbd.xian.pigeoninfinityapp.Functions.TimeSettings;

public class SingleAuctionActivity extends AppCompatActivity {

    String TAG = "XIAN";

    private static String AUCTION_RUNNING = "RUNNING";
    private static String AUCTION_UPCOMING = "UPCOMING";
    private static String AUCTION_PAST = "PAST";
    private static String AUCTION_LOADING = "LOADING";

    DatabaseReference databaseRef;
    AuctionObject auctionObject;

    RecyclerView RV_pigeonList;
    TextView TV_timer;

    ProgressBar PB_loadingPigeons;

    TimeSettings TS;
    String AUCTION_STATE = AUCTION_LOADING;

    ArrayList<Handler> handlers;
    ArrayList<Runnable> runnables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_auction);

        int auctionID = getIntent().getIntExtra("AUCTION_ID", 0);

        RV_pigeonList = findViewById(R.id.RV_pigeonList);
        TV_timer = findViewById(R.id.TV_timer);
        PB_loadingPigeons = findViewById(R.id.PB_loadingPigeons);

        handlers = new ArrayList<Handler>();
        runnables = new ArrayList<Runnable>();

        TS = new TimeSettings();

        if(AUCTION_STATE.equalsIgnoreCase(AUCTION_LOADING)){
            TV_timer.setText("LOADING DATA...");
        }

        getSingleAuctionDataFromFirebase(auctionID);

    }

    Date auctionEndTime;
    Date auctionStartTime;

    private void getSingleAuctionDataFromFirebase(int auc_id){

        databaseRef = FirebaseDatabase.getInstance().getReference("Auction");

        databaseRef.orderByKey().equalTo(""+auc_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                handlers.clear();
                runnables.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    AuctionObject ab = ds.getValue(AuctionObject.class);
                    auctionObject = ab;

                    auctionEndTime = TS.convertStringDateToDate(auctionObject.getAuctionEvent().getAuctionEnd());
                    auctionStartTime = TS.convertStringDateToDate(auctionObject.getAuctionEvent().getAuctionStart());
                    Date currentTime = TS.getCurrentTime();

                    if( currentTime.after(auctionStartTime) && currentTime.before(auctionEndTime)){
                        AUCTION_STATE = AUCTION_RUNNING;
                    }else if( currentTime.after(auctionEndTime)){
                        AUCTION_STATE = AUCTION_PAST;
                    }else if(currentTime.before(auctionStartTime) && currentTime.before(auctionEndTime)){
                        AUCTION_STATE = AUCTION_UPCOMING;
                    }

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                    RV_pigeonList.setLayoutManager(mLayoutManager);

                    RecyclerView.Adapter mRecycleAdapter = new MyRVAdapterPigeons(getApplicationContext(), auctionObject.getPigeons());
                    RV_pigeonList.setAdapter(mRecycleAdapter);
                }

                PB_loadingPigeons.setVisibility(View.GONE);

                if(AUCTION_STATE.equalsIgnoreCase(AUCTION_RUNNING)){
                    updateAuctionTimeEnd(auctionEndTime);
                }else if (AUCTION_STATE.equalsIgnoreCase(AUCTION_UPCOMING)){
                    updateAuctionTimeStart(auctionStartTime);
                }else if(AUCTION_STATE.equalsIgnoreCase(AUCTION_PAST)){
                    TV_timer.setVisibility(View.GONE);
                }

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

            if(AUCTION_STATE.equalsIgnoreCase(AUCTION_RUNNING)){
                updatePigeons(viewHolder.TV_auctionEnd, TS.convertStringDateToDate(pigeonList.get(i).getEndTime()));
            }if (AUCTION_STATE.equalsIgnoreCase(AUCTION_UPCOMING)){
                viewHolder.TV_auctionEnd.setText("AUCTION HASN'T STARTED");
                viewHolder.btn_bid.setVisibility(View.GONE);
                viewHolder.TV_auctionEnd.setVisibility(View.GONE);
            }if (AUCTION_STATE.equalsIgnoreCase(AUCTION_PAST)){
                viewHolder.TV_auctionEnd.setText("AUCTION ENDED");
                viewHolder.btn_bid.setVisibility(View.GONE);
                viewHolder.TV_auctionEnd.setVisibility(View.GONE);
            }

            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongClick) {

                    if (isLongClick) {
                        //Toast.makeText(context, "#" + position + " (Long click)", Toast.LENGTH_SHORT).show();
                        //showPopupMenu(view,position);

                    } else {
                        Intent at = new Intent(context, SinglePigeonActivity.class);
                        at.putExtra("AUCTION_ID", pigeonList.get(position).getAuctionID());
                        at.putExtra("PIGEON_ID", pigeonList.get(position).getPigeonID());
                        startActivity(at);
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


    private void updatePigeons(TextView textView, Date EndTime){
        int index = handlers.size();

        handlers.add(new Handler(Looper.getMainLooper()));
        runnables.add(new Runnable() {
            @Override
            public void run() {
                // Your Code
                String endsIn = TS.getTimeDifferenceEnd(EndTime);

                textView.setText(endsIn);
                handlers.get(index).postDelayed(runnables.get(index), 1000);
            }
        });
        handlers.get(index).postDelayed(runnables.get(index), 1000);
    }

    private void updateAuctionTimeEnd(Date EndTime){
        int index = handlers.size();

        handlers.add(new Handler(Looper.getMainLooper()));
        runnables.add(new Runnable() {
            @Override
            public void run() {
                // Your Code
                String endsIn = TS.getTimeDifferenceEnd(EndTime);

                TV_timer.setText(endsIn+" Remaining!!");
                handlers.get(index).postDelayed(runnables.get(index), 1000);
            }
        });
        handlers.get(index).postDelayed(runnables.get(index), 1000);
    }

    private void updateAuctionTimeStart(Date StartTime){
        int index = handlers.size();

        handlers.add(new Handler(Looper.getMainLooper()));
        runnables.add(new Runnable() {
            @Override
            public void run() {
                // Your Code
                String startsIn = TS.getTimeDifferenceStart(StartTime);

                TV_timer.setText("Starts in "+startsIn);
                handlers.get(index).postDelayed(runnables.get(index), 1000);
            }
        });
        handlers.get(index).postDelayed(runnables.get(index), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handlers.size()>0){
            for (int i=0; i<handlers.size(); i++){
                handlers.get(i).removeCallbacks(runnables.get(i));
            }

        }
    }
}

