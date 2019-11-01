package com.xwf.handle.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xwf.handle.R;
import com.xwf.handle.tr.TJob;

public class HuoJuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huoju, container, false);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View vMouse = view.findViewById(R.id.v_mouse);
        vMouse.setOnTouchListener((v, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    int x = px2dp(motionEvent.getX());
                    int y = px2dp(motionEvent.getY());
                    if (x < 0) {
                        x = 0;
                    }
                    if (x > 160) {
                        x = 160;
                    }
                    if (y < 0) {
                        y = 0;
                    }
                    if (y > 160) {
                        y = 160;
                    }
                    sendMouse(x - 80, y - 80);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    return false;
            }
            return false;
        });
    }

    public int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private void sendMouse(int x, int y) {
        byte b = 1;
        TJob.getInstance().send(b, x, y, 0);
    }

}
