package com.cruise.preetika.cruiseapp.classes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cruise.preetika.cruiseapp.R;

import java.util.Random;

public class Itpackage2 extends AppCompatActivity {

    static int i1 = 0, i2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_package2);
        LinearLayout l1 = findViewById(R.id.itl1);
        LinearLayout l2 = findViewById(R.id.itl2);
    }
    public void expand(final View v, int i, Button b) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targettHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targettHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targettHeight / v.getContext().getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
        i = 1;
        b.setCompoundDrawablesWithIntrinsicBounds(null, null, this.getResources().getDrawable(R.drawable.user), null);

    }

    /**
     * Collapse Specified View
     *
     * @param
     */
    public int roomIdGenerator() {
        Random rand = new Random();
        return rand.nextInt(500);
    }

    public void collapse(final View v, int i, Button b) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight
                            - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
        i = 0;
        b.setCompoundDrawablesWithIntrinsicBounds(null, null, this.getResources().getDrawable(R.drawable.d), null);

    }


}
