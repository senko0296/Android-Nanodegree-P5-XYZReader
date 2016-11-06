/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.xyzreader.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.example.xyzreader.R;

/**
 * A custom ScrollView that can accept a scroll listener.
 */
public class ObservableScrollView extends ScrollView {
    private static final String TAG = ObservableScrollView.class.getSimpleName();
    private Callbacks mCallbacks;
    private FloatingActionButton fab;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFloatingActionButton(FloatingActionButton fab) {
        this.fab = fab;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //Log.d(TAG, "onScrollChanged: " + l + " " + t + " " + oldl + " " + oldt + " ");
        Animation scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        if (fab != null) {
            if (t > oldt) {
                if (fab.getVisibility() == VISIBLE) {
                    fab.startAnimation(scaleDown);
                    fab.setVisibility(GONE);
                }

            } else {
                if (fab.getVisibility() == GONE) {
                    fab.startAnimation(scaleUp);
                    fab.setVisibility(VISIBLE);
                }

            }
        }
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int scrollY = getScrollY();
        // hack to call onScrollChanged on screen rotate
        if (scrollY > 0 && mCallbacks != null) {
            mCallbacks.onScrollChanged();
        }
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(Callbacks listener) {
        mCallbacks = listener;
    }

    public static interface Callbacks {
        public void onScrollChanged();
    }
}