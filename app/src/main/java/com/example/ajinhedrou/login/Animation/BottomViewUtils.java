package com.example.ajinhedrou.login.Animation;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class BottomViewUtils {
    public static void setupViewPager(ViewPager viewPager) {
        final View bottomSheetParent = findBottomSheetParent(viewPager);
        if (bottomSheetParent != null) {
            viewPager.addOnPageChangeListener(new BottomSheetViewPagerListener(viewPager, bottomSheetParent));
        }
    }

    private static class BottomSheetViewPagerListener extends ViewPager.SimpleOnPageChangeListener {
        private final ViewPager viewPager;
        private final BottomViewBehaviour<View> behavior;

        private BottomSheetViewPagerListener(ViewPager viewPager, View bottomSheetParent) {
            this.viewPager = viewPager;
            this.behavior = BottomViewBehaviour.from(bottomSheetParent);
        }

        @Override
        public void onPageSelected(int position) {
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    behavior.invalidateScrollingChild();
                }
            });
        }
    }

    private static View findBottomSheetParent(final View view) {
        View current = view;
        while (current != null) {
            final ViewGroup.LayoutParams params = current.getLayoutParams();
            if (params instanceof CoordinatorLayout.LayoutParams && ((CoordinatorLayout.LayoutParams) params).getBehavior() instanceof BottomViewBehaviour) {
                return current;
            }
            final ViewParent parent = current.getParent();
            current = parent == null || !(parent instanceof View) ? null : (View) parent;
        }
        return null;
    }
}
