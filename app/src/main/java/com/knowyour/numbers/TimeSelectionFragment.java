package com.knowyour.numbers;

import android.annotation.TargetApi;
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

public class TimeSelectionFragment extends Fragment {
    protected static final String ARG_KEY = "key";
    private Page mPage;
    private PageFragmentCallbacks mCallbacks;
    private TimePicker mTimePicker;


    public static TimeSelectionFragment create(String key) {
        final Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        final TimeSelectionFragment f = new TimeSelectionFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mPage = mCallbacks.onGetPage(args.getString(ARG_KEY));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.time_selection,
                container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
                .getTitle());

        mTimePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
        final String dataAsString = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
        final Integer[] timeValue = extractTimeString(dataAsString);

        mTimePicker.setCurrentHour(timeValue[0]);
        mTimePicker.setCurrentMinute(timeValue[1]);

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

        final String currentTime = createTimeString(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
        mPage.getData().putString(Page.SIMPLE_DATA_KEY, currentTime);
        mPage.notifyDataChanged();

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(final TimePicker view, final int hourOfDay, final int minute) {
                final String formattedTime = createTimeString(hourOfDay, minute);
                mPage.getData().putString(Page.SIMPLE_DATA_KEY, formattedTime);
                mPage.notifyDataChanged();
            }
        });
     }

    public static String createTimeString(int hourOfDay, int minute) {
        return String.format("%d:%02d %s", hourOfDay % 12, minute, hourOfDay >= 12 ? "PM" : "AM");
    }

    public static Integer[] extractTimeString(String dataAsString) {
        String[] time = new String[0];
        if (dataAsString != null ) {
            time = dataAsString.split(": ");
        }

        final Integer[] timeValue = new Integer[2];

        if (time.length == 3) {
            try {
                boolean pm = time[2].equalsIgnoreCase("pm");
                timeValue[0] = Integer.parseInt(time[0]) + (pm ? 12 : 0);
                timeValue[1] = Integer.parseInt(time[1]);
            } catch (final NumberFormatException nfe) {
                // Do nothing
            }
        }
        return timeValue;
    }
}
