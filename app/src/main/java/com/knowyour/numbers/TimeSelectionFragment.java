package com.knowyour.numbers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;

/**
 * Created by pnied on 4/28/2015.
 */
public class TimeSelectionFragment extends Fragment {
    protected static final String ARG_KEY = "key";
    private String mKey;
    private Page mPage;
    private PageFragmentCallbacks mCallbacks;
    private TimePicker mTimePicker;


    public static TimeSelectionFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TimeSelectionFragment f = new TimeSelectionFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_selection,
                container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
                .getTitle());

        mTimePicker = (TimePicker) rootView.findViewById(R.id.timePicker);

        String[] time = new String[0];
        final String dataAsString = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
        if (dataAsString != null ) {
            time = dataAsString.split(":");
        }

        if (time.length == 2) {
            try {
                int hour = Integer.parseInt(time[0]);
                mTimePicker.setCurrentHour(hour);
                int minute = Integer.parseInt(time[1]);
                mTimePicker.setCurrentMinute(minute);
            } catch (final NumberFormatException nfe) {
                // Do nothing
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException(
                    "Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(final TimePicker view, final int hourOfDay, final int minute) {
                mPage.getData().putString(Page.SIMPLE_DATA_KEY, hourOfDay + ":" + minute);
                mPage.notifyDataChanged();
            }
        });
     }
}
