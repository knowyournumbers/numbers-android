package com.knowyour.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.knowyour.numbers.change.ChangeSchedule;

import java.util.LinkedList;
import java.util.List;

public class ChangeActivity extends FragmentActivity {
    protected ViewPager mPager;
    protected MyPagerAdapter mPagerAdapter;
    protected Button mNextButton;
    protected Button mPrevButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_activity);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.AddPage(IsNumberKnownFragment.newInstance());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                updateBottomBar();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mPager.getCurrentItem() + 1 <= mPager.getChildCount()){
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
            }
        });
        updateBottomBar();

    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mPagerAdapter.getCount()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
        } else {
            mNextButton
                    .setBackgroundResource(R.drawable.selectable_item_background);
            final TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
                    true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCount());
        }

        mPrevButton
                .setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    protected MyPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        private Fragment mPrimaryItem;
        private List<Fragment> pages = new LinkedList<>();

        public MyPagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        public void AddPage(final Fragment fragment) {
            if (!pages.contains(fragment)) {
                pages.add(fragment);
                notifyDataSetChanged();
            }
        }

        public void RemovePage(final Fragment fragment) {
            pages.remove(fragment);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Fragment getItem(int i) {
            return pages.get(i);
        }

        @Override
        public int getItemPosition(Object object) {
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }
    }

    public static class IsNumberKnownFragment extends Fragment {

        private static int number = 1;
        public static Fragment newInstance() {
            final IsNumberKnownFragment fragment = new IsNumberKnownFragment();
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater,
                                 @Nullable final ViewGroup container,
                                 @Nullable final Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.is_number_known_fragment, container, false);

            TextView textView = (TextView)view.findViewById(R.id.textView);
            textView.setText(textView.getText() + " " + number++);

            final RadioButton knownButton = (RadioButton)view.findViewById(R.id.radioButton);
            knownButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ChangeActivity activity = ((ChangeActivity) getActivity());
                    if (isChecked) {
                        activity.mPagerAdapter.AddPage(NumberKnownPreShare.newInstance());
                        activity.mPagerAdapter.AddPage(ShareKnownNumber.newInstance());
                    } else {
                        activity.mPagerAdapter.RemovePage(ShareKnownNumber.newInstance());
                        activity.mPagerAdapter.RemovePage(NumberKnownPreShare.newInstance());
                    }
                }
            });

            final RadioButton notKnownButton = (RadioButton)view.findViewById(R.id.radioButton2);
            notKnownButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    ChangeActivity activity = ((ChangeActivity) getActivity());
                    if (isChecked) {
                        activity.mPagerAdapter.AddPage(ChangeSchedule.newInstance());
                    } else {
                        activity.mPagerAdapter.RemovePage(ChangeSchedule.newInstance());
                    }
                }
            });

            return view;
        }
    }

    public static class NumberKnownPreShare extends Fragment {

        public static Fragment newInstance() {
            final NumberKnownPreShare fragment = new NumberKnownPreShare();
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater,
                                 @Nullable final ViewGroup container,
                                 @Nullable final Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.number_known_pre_share, container, false);
//            final ChangeActivity activity = ((ChangeActivity) getActivity());
//            activity.mPagerAdapter.AddPage(ShareKnownNumber.newInstance());
            return view;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    }

    public static class ShareKnownNumber extends Fragment {

        public static Fragment newInstance() {
            final ShareKnownNumber fragment = new ShareKnownNumber();
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater,
                                 @Nullable final ViewGroup container,
                                 @Nullable final Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.share_known_number, container, false);
            final Button shareWithFriends = (Button)view.findViewById(R.id.button);
            shareWithFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT, "My child learned my number with the @KnowYourNumbers app!");
                    i.setType("text/plain");
                    Intent.createChooser(i, getResources().getText(R.string.share));
                    startActivity(i);
                    // TODO Add the app icon!
                    // i.putExtra(Intent.EXTRA_SHORTCUT_ICON, null)
                }
            });

            return view;
        }
    }

}
