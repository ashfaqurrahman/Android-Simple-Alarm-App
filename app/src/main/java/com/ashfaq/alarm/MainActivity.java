

package com.ashfaq.alarm;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashfaq.alarm.alarms.ui.AlarmsFragment;
import com.ashfaq.alarm.data.BaseItemCursor;
import com.ashfaq.alarm.list.RecyclerViewFragment;
import com.ashfaq.alarm.settings.SettingsActivity;
import com.ashfaq.alarm.stopwatch.ui.StopwatchFragment;
import com.ashfaq.alarm.timepickers.Utils;
import com.ashfaq.alarm.timers.ui.TimersFragment;

import butterknife.Bind;

import static com.ashfaq.alarm.list.RecyclerViewFragment.ACTION_SCROLL_TO_STABLE_ID;
import static com.ashfaq.alarm.list.RecyclerViewFragment.EXTRA_SCROLL_TO_STABLE_ID;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    public static final int    PAGE_ALARMS          = 0;
    public static final int    PAGE_TIMERS          = 1;
    public static final int    PAGE_STOPWATCH       = 2;
    public static final int    REQUEST_THEME_CHANGE = 5;
    public static final String EXTRA_SHOW_PAGE      = "com.ashfaq.alarm.extra.SHOW_PAGE";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Drawable             mAddItemDrawable;

    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (mViewPager.getCurrentItem() == mSectionsPagerAdapter.getCount() - 1) {
                    // Restore the FAB's translationX from a previous configuration.
                    mFab.setTranslationX(mViewPager.getWidth() / -2f + getFabPixelOffsetForXTranslation());
                }
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * @param position Either the current page position if the offset is increasing,
             *                 or the previous page position if it is decreasing.
             * @param positionOffset If increasing from [0, 1), scrolling right and position = currentPagePosition
             *                       If decreasing from (1, 0], scrolling left and position = (currentPagePosition - 1)
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, String.format("pos = %d, posOffset = %f, posOffsetPixels = %d",
                        position, positionOffset, positionOffsetPixels));
                int pageBeforeLast = mSectionsPagerAdapter.getCount() - 2;
                if (position <= pageBeforeLast) {
                    if (position < pageBeforeLast) {
                        // When the scrolling is due to tab selection between multiple tabs apart,
                        // this callback is called for each intermediate page, but each of those pages
                        // will briefly register a sparsely decreasing range of positionOffsets, always
                        // from (1, 0). As such, you would notice the FAB to jump back and forth between
                        // x-positions as each intermediate page is scrolled through.
                        // This is a visual optimization that ends the translation motion, immediately
                        // returning the FAB to its target position.
                        // TODO: The animation visibly skips to the end. We could interpolate
                        // intermediate x-positions if we cared to smooth it out.
                        mFab.setTranslationX(0);
                    } else {
                        // Initially, the FAB's translationX property is zero because, at its original
                        // position, it is not translated. setTranslationX() is relative to the view's
                        // left position, at its original position; this left position is taken to be
                        // the zero point of the coordinate system relative to this view. As your
                        // translationX value is increasingly negative, the view is translated left.
                        // But as translationX is decreasingly negative and down to zero, the view
                        // is translated right, back to its original position.
                        float translationX = positionOffsetPixels / -2f;
                        // NOTE: You MUST scale your own additional pixel offsets by positionOffset,
                        // or else the FAB will immediately translate by that many pixels, appearing
                        // to skip/jump.
                        translationX += positionOffset * getFabPixelOffsetForXTranslation();
                        mFab.setTranslationX(translationX);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected");
                if (position < mSectionsPagerAdapter.getCount() - 1) {
                    mFab.setImageDrawable(mAddItemDrawable);
                }
                Fragment f = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
                // NOTE: This callback is fired after a rotation, right after onStart().
                // Unfortunately, the FragmentManager handling the rotation has yet to
                // tell our adapter to re-instantiate the Fragments, so our collection
                // of fragments is empty. You MUST keep this check so we don't cause a NPE.
                if (f instanceof BaseFragment) {
                    ((BaseFragment) f).onPageSelected();
                }
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ColorStateList tabIconColor = ContextCompat.getColorStateList(this, R.color.tab_icon_color);
        setTabIcon(PAGE_ALARMS, R.drawable.ic_alarm_24dp, tabIconColor);
        setTabIcon(PAGE_TIMERS, R.drawable.ic_timer_24dp, tabIconColor);
        setTabIcon(PAGE_STOPWATCH, R.drawable.ic_stopwatch_24dp, tabIconColor);

        // TODO: @OnCLick instead.
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
                if (f instanceof RecyclerViewFragment) {
                    ((RecyclerViewFragment) f).onFabClick();
                }
            }
        });

        mAddItemDrawable = ContextCompat.getDrawable(this, R.drawable.ic_add_24dp);
        handleActionScrollToStableId(getIntent(), false);
    }

    private void setTabIcon(int index, @DrawableRes int iconRes, @NonNull final ColorStateList color) {
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        Drawable icon = Utils.getTintedDrawable(this, iconRes, color);
        DrawableCompat.setTintList(icon, color);
        tab.setIcon(icon);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleActionScrollToStableId(intent, true);
    }

    /**
     * Handles a PendingIntent, fired from e.g. clicking a notification, that tells us to
     * set the ViewPager's current item and scroll to a specific RecyclerView item
     * given by its stable ID.
     *
     * @param performScroll Whether to actually scroll to the stable id, if there is one.
     *                      Pass true if you know {@link
     *                      RecyclerViewFragment#onLoadFinished(Loader, BaseItemCursor) onLoadFinished()}
     *                      had previously been called. Otherwise, pass false so that we can
     *                      {@link RecyclerViewFragment#setScrollToStableId(long) setScrollToStableId(long)}
     *                      and let {@link
     *                      RecyclerViewFragment#onLoadFinished(Loader, BaseItemCursor) onLoadFinished()}
     *                      perform the scroll for us.
     */
    private void handleActionScrollToStableId(@NonNull final Intent intent,
                                              final boolean performScroll) {
        if (ACTION_SCROLL_TO_STABLE_ID.equals(intent.getAction())) {
            final int targetPage = intent.getIntExtra(EXTRA_SHOW_PAGE, -1);
            if (targetPage >= 0 && targetPage <= mSectionsPagerAdapter.getCount() - 1) {
                // #post() works for any state the app is in, especially robust against
                // cases when the app was not previously in memory--i.e. this got called
                // in onCreate().
                mViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(targetPage, true/*smoothScroll*/);
                        final long stableId = intent.getLongExtra(EXTRA_SCROLL_TO_STABLE_ID, -1);
                        if (stableId != -1) {
                            RecyclerViewFragment rvFrag = (RecyclerViewFragment)
                                    mSectionsPagerAdapter.getFragment(targetPage);
                            if (performScroll) {
                                rvFrag.performScrollToStableId(stableId);
                            } else {
                                rvFrag.setScrollToStableId(stableId);
                            }
                        }
                        intent.setAction(null);
                        intent.removeExtra(EXTRA_SHOW_PAGE);
                        intent.removeExtra(EXTRA_SCROLL_TO_STABLE_ID);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_THEME_CHANGE:
                if (data != null && data.getBooleanExtra(SettingsActivity.EXTRA_THEME_CHANGED, false)) {
                    recreate();
                }
                break;
        }
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int menuResId() {
        return R.menu.menu_main;
    }

    @Override
    protected boolean isDisplayHomeUpEnabled() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_THEME_CHANGE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @return the positive offset in pixels required to rebase an X-translation of the FAB
     * relative to its center position. An X-translation normally is done relative to a view's
     * left position.
     */
    private float getFabPixelOffsetForXTranslation() {
        final int margin;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Since each side's margin is the same, any side's would do.
            margin = ((ViewGroup.MarginLayoutParams) mFab.getLayoutParams()).rightMargin;
        } else {
            // Pre-Lollipop has measurement issues with FAB margins. This is
            // probably as good as we can get to centering the FAB, without
            // hardcoding some small margin value.
            margin = 0;
        }
        // By adding on half the FAB's width, we effectively rebase the translation
        // relative to the view's center position.
        return mFab.getWidth() / 2f + margin;
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final SparseArray<Fragment> mFragments = new SparseArray<>(getCount());

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_ALARMS:
                    return new AlarmsFragment();
                case PAGE_TIMERS:
                    return new TimersFragment();
                case PAGE_STOPWATCH:
                    return new StopwatchFragment();
                default:
                    throw new IllegalStateException("No fragment can be instantiated for position " + position);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public Fragment getFragment(int position) {
            return mFragments.get(position);
        }
    }
}
