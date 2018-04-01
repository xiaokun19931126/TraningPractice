package com.xiaokun.trainingpractice.property_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xiaokun.trainingpractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/08
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class AnimationActivity extends AppCompatActivity
{

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.img, R.id.login})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.img:
                ObjectAnimator
                        .ofFloat(img, "rotationX", 0.0F, 360F)
                        .setDuration(1000)
                        .start();
                break;
            case R.id.login:
                loginAnim();
                break;
            default:
                break;
        }
    }

    public void loginAnim()
    {
        AnimatorSet set = new AnimatorSet();
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) login.getLayoutParams();
        int currentWidth = getResources().getDisplayMetrics().widthPixels - 300;
        Log.e("AnimationActivity", "loginAnim(AnimationActivity.java:45)" + currentWidth);
        ValueAnimator animator = ValueAnimator.ofInt(currentWidth, 120);
        animator.setTarget(login);
        animator.setDuration(500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                Log.e("AnimationActivity", "onAnimationUpdate(AnimationActivity.java:53)" + value);
                params.width = value;
                login.setLayoutParams(params);
            }
        });

        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(login, "alpha", 0).setDuration(500);
        set.playTogether(animator, alpha);
        set.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                login.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                login.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progress.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        });
        set.start();
    }
}
