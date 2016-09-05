package me.crosswall.coverflow.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class Normal2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal2);

        PagerContainer mContainer = (PagerContainer) findViewById(R.id.pager_container);

        final ViewPager pager = mContainer.getViewPager();

        PagerAdapter adapter = new MyPagerAdapter();
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
        
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView view = new TextView(Normal2Activity.this);
            view.setText("Item "+position);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));
            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = Integer.parseInt(v.getTag().toString());
                    Toast.makeText(Normal2Activity.this, "position: " + pos, Toast.LENGTH_SHORT).show();
                    // use pos as per your requirment

                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeViewAt(position);
//            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
//            return (view == object);
        }
    }


}
