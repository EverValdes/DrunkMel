package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;

import com.drunkmel.drunkmel.helpers.CircularPenaltyArrayAdapter;
import com.drunkmel.drunkmel.loaders.JSONLoader;
import com.drunkmel.drunkmel.model.PenaltyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class PenaltyActivity extends ActivityMel {
    //Penalties that need to be recorded into singleton/database
    private ArrayList<PenaltyModel> penalties = new ArrayList<PenaltyModel>();

    //Variables
    Context context;
    private CircularPenaltyArrayAdapter circularPenaltyArrayAdapter;
    LinearLayoutManager llm;
    LinearLayout selectionButtons;
    Button nextTurn;
    Button end;

    //Layout
    RecyclerView carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONLoader jsonLoader = new JSONLoader(this);
        //Read json file "penalties"
        JSONArray jsonArray = jsonLoader.readData("penalties");
        createDataModel(jsonArray);

        context = getApplicationContext();
        loadUI();
    }

    private void loadUI() {
        setContentView(R.layout.activity_penalty);
        selectionButtons = (LinearLayout) findViewById(R.id.selectionButtons);
        nextTurn = (Button) findViewById(R.id.nextTurn);
        end = (Button) findViewById(R.id.end);
        carousel = (RecyclerView) findViewById(R.id.carousel);
        setUpCarousel();

        //Listeners
        nextTurn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PenaltyActivity.this, PenaltyActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Listeners
        end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpCarousel() {
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        circularPenaltyArrayAdapter = new CircularPenaltyArrayAdapter(this, penalties);
        carousel.setAdapter(circularPenaltyArrayAdapter);
        //Star on the middle of the penalties
        llm.scrollToPosition(circularPenaltyArrayAdapter.getItemCount()/2);
        carousel.setLayoutManager(llm);

        //Snap Helper to avoid having two penalties in the scrren
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(carousel);


        carousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {

                    //Disable the touch when the scrolling ends
                    recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                            return true;
                        }
                        @Override
                        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
                    });

                    selectionButtons.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    private void createDataModel(JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject penaltyObject = jsonArray.getJSONObject(i);
                JSONArray penaltyKeys = penaltyObject.names();
                PenaltyModel penaltyModel = new PenaltyModel();
                for (int j = 0; j < penaltyKeys.length(); j++) {
                    String key = penaltyKeys.getString(j);
                    String value = penaltyObject.getString(key);
                    switch (key) {
                        case "title": penaltyModel.setTitle(value);
                            break;
                        case "description": penaltyModel.setDescription(value);
                            break;
                    }
                }
                this.penalties.add(penaltyModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
