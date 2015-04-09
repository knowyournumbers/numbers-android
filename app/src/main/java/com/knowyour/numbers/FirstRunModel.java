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
            new BranchPage(this, "Select Phone Number")
                .addBranch("Existing Number",
                    new SingleFixedChoicePage(this, "Existing Number")
                        .setChoices("Use number (321)-555-1212"))
                .addBranch("Manual Entry",
                    new NumberPage(this, "Type the Phone Number to teach"))
                    .setRequired(true),
            new ImagePage(this, "Pick a photo for your child to see")
                .setRequired(true),
            new MultipleFixedChoicePage(this, "What day of the weeks should we notify you?")
                .setChoices("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                .setRequired(true),
            new TextPage(this, "What time should you be notified?")
                    .setValue("9:00 AM")
                    .setRequired(true)
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
