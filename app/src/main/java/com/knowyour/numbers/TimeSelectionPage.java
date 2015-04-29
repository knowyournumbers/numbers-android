package com.knowyour.numbers;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by pnied on 4/26/2015.
 */
public class TimeSelectionPage extends Page {

    protected TimeSelectionPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TimeSelectionFragment.create(getKey());
    }

    @Override
    public void getReviewItems(final ArrayList<ReviewItem> reviewItems) {
        reviewItems.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
                getKey()));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
    }

    public TimeSelectionPage setValue(final String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}
