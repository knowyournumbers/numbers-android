package com.knowyour.numbers;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

public class NumberSelectionPage  extends Page {
    protected NumberSelectionPage(final ModelCallbacks callbacks, final String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return NumberSelectionFragment.create(getKey());
    }

    @Override
    public void getReviewItems(final ArrayList<ReviewItem> reviewItems) {
        reviewItems.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
                getKey()));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY)) && mData.getString(SIMPLE_DATA_KEY).length() == 10;
    }

    public NumberSelectionPage setValue(final String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}
