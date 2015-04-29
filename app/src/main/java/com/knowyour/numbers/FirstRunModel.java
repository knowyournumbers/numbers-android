package com.knowyour.numbers;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.BranchPage;
import com.tech.freak.wizardpager.model.ImagePage;
import com.tech.freak.wizardpager.model.MultipleFixedChoicePage;
import com.tech.freak.wizardpager.model.NumberPage;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.PageList;
import com.tech.freak.wizardpager.model.SingleFixedChoicePage;
import com.tech.freak.wizardpager.model.TextPage;

/**
 * Created by pnied on 4/8/2015.
 */
public class FirstRunModel extends AbstractWizardModel {
    public FirstRunModel(final Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        final PageList mine = new PageList(
            new NumberSelectionPage(this, "Select Phone Number"),
            new ImagePage(this, "Pick a photo for your child to see")
                .setRequired(false), // Busted
            new MultipleFixedChoicePage(this, "What day of the weeks should we notify you?")
                .setChoices("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
            new TimeSelectionPage(this, "What time should you be notified?")
        );


        return mine;
    }

    @Override
    public void onPageDataChanged(Page page) {
        super.onPageDataChanged(page);
    }

    @Override
    public void onPageTreeChanged() {
        super.onPageTreeChanged();
    }
}
