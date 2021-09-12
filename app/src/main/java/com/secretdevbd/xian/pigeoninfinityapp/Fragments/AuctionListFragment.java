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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretdevbd.xian.pigeoninfinityapp.ItemClickListener;
import com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionObject;
import com.secretdevbd.xian.pigeoninfinityapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AuctionListFragment extends Fragment {

    RecyclerView RV_runningAuction, RV_upcomingAuction, RV_pastAuction;
    DatabaseReference databaseRef;

    ArrayList<AuctionObject> auctionObjectList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_auction_list, container, false);

        RV_runningAuction = view.findViewById(R.id.RV_runningAuction);
        RV_upcomingAuction = view.findViewById(R.id.RV_upcomingAuction);
        RV_pastAuction = view.findViewById(R.id.RV_pastAuction);

        return view;
    }

    private void getAuctionDataFromFirebase(){

        databaseRef = FirebaseDatabase.getInstance().getReference("Auction");

        //limitToLast 500
        databaseRef.limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                auctionObjectList.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    AuctionObject ab = ds.getValue(AuctionObject.class);

                    auctionObjectList.add(ab);
                }

                //Calculate Auction-Past-upcomming-now

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

    String TAG = "XIAN";

    public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {

        List<AuctionObject> auctionObjects;
        Context context;

        public MyRVAdapter(Context context, List<AuctionObject> pList) {
            super();
            this.context = context;
            this.auctionObjects = pList;
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

            /*Log.i(TAG,i+" RECYCLE VIEW "+(auctionObjects.get(i).getPigeonRingNumber()));


            viewHolder.TV_position.setText(pigeonList.get(i).getPosition()+"/"+pigeonList.get(i).getTotalPigeons()+" th");
            viewHolder.TV_ringNumber.setText(pigeonList.get(i).getPigeonRingNumber());
            viewHolder.TV_velocity.setText(pigeonList.get(i).getPigeonVelocity()+" YPM");
            viewHolder.TV_owenerName.setText(pigeonList.get(i).getOwnerName());
            viewHolder.TV_club.setText(pigeonList.get(i).getClubName());
            viewHolder.TV_race.setText(pigeonList.get(i).getRaceName() +" ( "+pigeonList.get(i).getDistance()+" KM )");*/

            //Picasso.get().load(productList.get(i).getProductPicture()).fit().centerCrop().into(viewHolder.IV_productPic);



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
            return auctionObjects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView TV_position, TV_ringNumber, TV_velocity, TV_owenerName, TV_club, TV_race, TV_date;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                /*TV_position = itemView.findViewById(R.id.TV_position);
                TV_ringNumber = itemView.findViewById(R.id.TV_ringNumber);
                TV_velocity = itemView.findViewById(R.id.TV_velocity);
                TV_owenerName = itemView.findViewById(R.id.TV_owenerName);
                TV_club = itemView.findViewById(R.id.TV_club);
                TV_race = itemView.findViewById(R.id.TV_race);
                TV_date = itemView.findViewById(R.id.TV_date);*/

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