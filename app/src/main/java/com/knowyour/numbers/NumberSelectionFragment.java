package com.knowyour.numbers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;
import com.tech.freak.wizardpager.ui.TextFragment;

public class NumberSelectionFragment extends TextFragment {
    private Page mPage;
    private PageFragmentCallbacks mCallbacks;

    public static NumberSelectionFragment create(final String key) {
        final Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        final NumberSelectionFragment f = new NumberSelectionFragment();
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
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);

        final String dataAsString = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
        final Integer[] phoneNumberValue = extractPhoneNumberString(dataAsString);

        if (phoneNumberValue != null && phoneNumberValue.length == 3) {
            mEditTextInput.setText(String.format("(%d) %d-%d", phoneNumberValue[0], phoneNumberValue[1], phoneNumberValue[2]));
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

        final TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getLine1Number() != null) {
            final String phoneNumber = createPhoneNumberString(telephonyManager.getLine1Number());
            mEditTextInput.setText(phoneNumber);
            mPage.getData().putString(Page.SIMPLE_DATA_KEY, phoneNumber);
            mPage.notifyDataChanged();
        }

        mEditTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s,
                                          final int start,
                                          final int count,
                                          final int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s,
                                      final int start,
                                      final int before,
                                      final int count) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                final String userEnteredPhoneNumber = editable.toString();
                mPage.getData().putString(Page.SIMPLE_DATA_KEY, userEnteredPhoneNumber);
                mPage.notifyDataChanged();
            }
        });
    }

    public static String createPhoneNumberString(final String rawPhoneNumber) {
        final String areaCode = rawPhoneNumber.substring(1, 4);
        final String firstSection = rawPhoneNumber.substring(4, 7);
        final String secondSection = rawPhoneNumber.substring(7 , 11);
        return String.format("(%s) %s-%s", areaCode, firstSection, secondSection);
    }

    public static Integer[] extractPhoneNumberString(final String dataAsString) {
        if (dataAsString == null) {
            return null;
        }
        final String[] initialParts = dataAsString.split(" ");
        if (initialParts.length != 2) {
            return null;
        }
        final String areaCodeStr = initialParts[0].substring(1,3);
        final int areaCode = Integer.parseInt(areaCodeStr);

        final String[] secondHalf = initialParts[1].trim().split("-");
        if (secondHalf.length != 2) {
            return null;
        }
        final String firstSectionStr = secondHalf[0];
        final String secondSectionStr = secondHalf[1];
        final int firstSection = Integer.parseInt(firstSectionStr);
        final int secondSection = Integer.parseInt(secondSectionStr);
        return new Integer[] {areaCode, firstSection, secondSection};
    }
}
