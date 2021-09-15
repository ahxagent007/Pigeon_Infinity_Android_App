package com.secretdevbd.xian.pigeoninfinityapp.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretdevbd.xian.pigeoninfinityapp.ItemClickListener;
import com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionEvent;
import com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionObject;
import com.secretdevbd.xian.pigeoninfinityapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.secretdevbd.xian.pigeoninfinityapp.Functions.TimeSettings;


public class AuctionListFragment extends Fragment {

    String TAG = "XIAN";

    TextView TV_past, TV_upcoming, TV_running;
    RelativeLayout RL_loadingLayer;

    RecyclerView RV_runningAuction, RV_upcomingAuction, RV_pastAuction;
    DatabaseReference databaseRef;

    ArrayList<AuctionObject> auctionObjectList;

    ArrayList<AuctionEvent> next_auc, running_auc, past_auc;

    public AuctionListFragment() {
        // Required empty public constructor
    }

    public static AuctionListFragment newInstance(String param1, String param2) {
        AuctionListFragment fragment = new AuctionListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auctionObjectList = new ArrayList<AuctionObject>();
        next_auc = new ArrayList<AuctionEvent>();
        running_auc = new ArrayList<AuctionEvent>();
        past_auc = new ArrayList<AuctionEvent>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_auction_list, container, false);

        RV_runningAuction = view.findViewById(R.id.RV_runningAuction);
        RV_upcomingAuction = view.findViewById(R.id.RV_upcomingAuction);
        RV_pastAuction = view.findViewById(R.id.RV_pastAuction);

        TV_past = view.findViewById(R.id.TV_past);
        TV_running = view.findViewById(R.id.TV_running);
        TV_upcoming = view.findViewById(R.id.TV_upcoming);

        RL_loadingLayer = view.findViewById(R.id.RL_loadingLayer);
        RL_loadingLayer.setVisibility(View.VISIBLE);

        RV_runningAuction.setVisibility(View.INVISIBLE);
        RV_upcomingAuction.setVisibility(View.INVISIBLE);
        RV_pastAuction.setVisibility(View.INVISIBLE);
        TV_past.setVisibility(View.INVISIBLE);
        TV_running.setVisibility(View.INVISIBLE);
        TV_upcoming.setVisibility(View.INVISIBLE);


        getAuctionDataFromFirebase();

        return view;
    }

    private void getAuctionDataFromFirebase(){

        Date current_date = new TimeSettings().getCurrentTime();

        databaseRef = FirebaseDatabase.getInstance().getReference("Auction");

        //limitToLast 500
        databaseRef.limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                auctionObjectList.clear();
                next_auc.clear();
                running_auc.clear();
                past_auc.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    AuctionObject ab = ds.getValue(AuctionObject.class);
                    Log.i(TAG, ab.getAuctionEvent().getAuctionStart());
                    auctionObjectList.add(ab);

                    Date auc_start_date = new TimeSettings().convertStringDateToDate(ab.getAuctionEvent().getAuctionStart());
                    Date auc_end_date = new TimeSettings().convertStringDateToDate(ab.getAuctionEvent().getAuctionEnd());

                    //Calculate Auction-Past-upcomming-now
                    if(current_date.before(auc_start_date)){
                        // Upcoming Auction
                        next_auc.add(ab.getAuctionEvent());

                    }else if(current_date.after(auc_start_date) && current_date.before(auc_end_date)){
                        // Running Auction
                        running_auc.add(ab.getAuctionEvent());
                    }else {
                        // Past Auction
                        past_auc.add(ab.getAuctionEvent());
                    }
                }

                RL_loadingLayer.setVisibility(View.INVISIBLE);

                RecyclerView.LayoutManager mLayoutManager_next_auc = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                RecyclerView.LayoutManager mLayoutManager_running_auc = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                RecyclerView.LayoutManager mLayoutManager_past_auc = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                RV_runningAuction.setLayoutManager(mLayoutManager_running_auc);
                RV_upcomingAuction.setLayoutManager(mLayoutManager_next_auc);
                RV_pastAuction.setLayoutManager(mLayoutManager_past_auc);

                if (next_auc.size()>0){
                    TV_upcoming.setVisibility(View.VISIBLE);
                    RV_upcomingAuction.setVisibility(View.VISIBLE);

                    RecyclerView.Adapter mRecycleAdapter_next_auc = new MyRVAdapter(getContext(), next_auc);
                    RV_upcomingAuction.setAdapter(mRecycleAdapter_next_auc);
                }

                if (running_auc.size()>0){
                    TV_running.setVisibility(View.VISIBLE);
                    RV_runningAuction.setVisibility(View.VISIBLE);

                    RecyclerView.Adapter mRecycleAdapter_running_auc = new MyRVAdapter(getContext(), running_auc);
                    RV_runningAuction.setAdapter(mRecycleAdapter_running_auc);
                }

                if (past_auc.size()>0){
                    TV_past.setVisibility(View.VISIBLE);
                    RV_pastAuction.setVisibility(View.VISIBLE);

                    RecyclerView.Adapter mRecycleAdapter_past_auc = new MyRVAdapter(getContext(), past_auc);
                    RV_pastAuction.setAdapter(mRecycleAdapter_past_auc);
                }


                //sort via boosted or not here
                /*ArrayList<PigeonPostDB> pigeonPostDBS = new ArrayList<PigeonPostDB>();

                Collections.reverse(boostedPigeonPostDBS);
                Collections.reverse(normalPigeonPostDBS);

                pigeonPostDBS.addAll(boostedPigeonPostDBS);
                pigeonPostDBS.addAll(normalPigeonPostDBS);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                RV_buy.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mRecycleAdapter = new RecycleViewAdapterForPigeonPost(getApplicationContext(), pigeonPostDBS);
                RV_buy.setAdapter(mRecycleAdapter);

                PB_loadingBuy.setVisibility(View.INVISIBLE);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {

        List<AuctionEvent> auctionEvents;
        Context context;

        public MyRVAdapter(Context context, List<AuctionEvent> auctionEvents) {
            super();
            this.context = context;
            this.auctionEvents = auctionEvents;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_auction_for_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

            viewHolder.TV_auctionName.setText(auctionEvents.get(i).getAuctionName());
            viewHolder.TV_auctionStart.setText(""+auctionEvents.get(i).getAuctionStart());
            viewHolder.TV_auctionEnd.setText(""+auctionEvents.get(i).getAuctionEnd());

            String url = "https://pigeoninfinity.com/static/UPLOADS/AUCTION/"+auctionEvents.get(i).getMainPicture();
            Log.i(TAG, url);

            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_downloading_24)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(viewHolder.IV_auctionPic);



            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongClick) {

                    /*viewHolder.IV_productPic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShowPicture(productList.get(position).getProductPicture());
                        }
                    });*/

                    if (isLongClick) {
                        //Toast.makeText(context, "#" + position + " (Long click)", Toast.LENGTH_SHORT).show();
                        //showPopupMenu(view,position);

                    } else {
                        //Toast.makeText(context, "#" + position + " Not Long Click", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return auctionEvents.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public ImageView IV_auctionPic;
            public TextView TV_auctionName, TV_auctionStart, TV_auctionEnd;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                IV_auctionPic = itemView.findViewById(R.id.IV_auctionPic);
                TV_auctionName = itemView.findViewById(R.id.TV_auctionName);
                TV_auctionStart = itemView.findViewById(R.id.TV_auctionStart);
                TV_auctionEnd = itemView.findViewById(R.id.TV_auctionEnd);

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