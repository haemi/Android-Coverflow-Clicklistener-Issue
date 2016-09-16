package me.crosswall.coverflow.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class Normal2Activity extends AppCompatActivity {

    GestureDetectorCompat gestureDetectorCompat;
    MyPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal2);

        PagerContainer mContainer = (PagerContainer) findViewById(R.id.pager_container);

        final ViewPager pager = mContainer.getViewPager();

        adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(adapter.getCount());

        pager.setClipChildren(false);


        boolean showRotate = getIntent().getBooleanExtra("showRotate",true);

        if(showRotate){
            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.3f)
                    .pagerMargin(0f)
                    .spaceSize(0f)
                    .rotationY(25f)
                    .build();
        }

        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                //Handle on click of view pager
                handleViewPagerClick(e,adapter.viewList);

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

        //To handle on touch of view pager with gesture detector
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return false;
            }
        });

    }

    private class MyPagerAdapter extends PagerAdapter {

        public Map<Integer,View> viewList = new HashMap<Integer,View>();

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final TextView view = new TextView(Normal2Activity.this);
            view.setText("Item " + position);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Normal2Activity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                    System.out.println("position:::::::::::::" + position);
                }
            });*/

            container.addView(view);

            //Adding views to view map
            viewList.put(position,view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, final int position, Object object) {
            ((ViewPager) container).removeViewAt(position);
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    /**
     * Handle the view pager click
     * @param event (Single Tap Event)
     * @param viewMap (List of view pager child views)
     */
    private void handleViewPagerClick(MotionEvent event, Map<Integer,View> viewMap){
        for (Map.Entry<Integer,View> entry : viewMap.entrySet()){
            int location[] = new int[2];
            entry.getValue().getLocationOnScreen(location);
            int viewX = location[0];
            int viewY = location[1];
            //point is inside view bounds
            if(( event.getRawX() > viewX && event.getRawX() < (viewX + entry.getValue().getWidth())) &&
                    ( event.getRawY() > viewY && event.getRawY() < (viewY + entry.getValue().getHeight()))){
                //Here you will get clicked position
                Log.d("Normal2Activity", "Clicked Position " + entry.getKey());
                onClick(entry.getKey());
                return ;
            }
        }
    }

    private void onClick(final int position){
        Toast.makeText(Normal2Activity.this,"Clicked Postion - "+ position,Toast.LENGTH_SHORT).show();
    }


}
