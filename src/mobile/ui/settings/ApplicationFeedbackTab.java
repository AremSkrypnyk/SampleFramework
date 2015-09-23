package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ApplicationFeedbackTab extends ScreenCard {
    private static final By RATING_STARS_LOCATOR  = By.className("rating-holder");
    private static final By RATE_QUESTION_LOCATOR = By.className("rate");
    private static final By QUESTIONS_OR_COMMENTS_INPUT_BOX_LOCATOR = By.className("x-input-text");
    private static final By SUBMIT_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildText("Submit").build());
    private static final String STAR_XPATH = new XPathBuilder().byClassName("star").withAttribute("index", "%s").build();

    public ApplicationFeedbackTab() {
        addCheckpointElement(RATING_STARS_LOCATOR);
        waitReady();
    }

    public ApplicationFeedbackTab verifyControls() {
        Assert.assertNotNull(Driver.findIfExists(RATING_STARS_LOCATOR), "Stars rating wasn't found.");
        Assert.assertNotNull(Driver.findIfExists(QUESTIONS_OR_COMMENTS_INPUT_BOX_LOCATOR), "Input box for questions or comments wasn't found.");
        return new ApplicationFeedbackTab();
    }

    public ApplicationFeedbackTab verifyTextPresent() {
        String[] applicationFeedbackTabTexts = {
                "How would you rate your overall experience?",
                "Detailed Feedback",
                "Mention features you'd like to see in the mobile product or describe your overall experience.",
                "Questions or Comments",
        };

        for (int i = 0; i < applicationFeedbackTabTexts.length; i++) {
            Driver.verifyExactTextPresentAndVisible(applicationFeedbackTabTexts[i]);
        }
        return new ApplicationFeedbackTab();
    }

    public ApplicationFeedbackTab verifySubmitButtonDisabled() {
        Assert.assertTrue(isSubmitButtonDisabled(), "Submit button is not disabled.");
        return new ApplicationFeedbackTab();
    }

    public ApplicationFeedbackTab verifySubmitButtonEnabled() {
        Assert.assertFalse(isSubmitButtonDisabled(), "Sumbit button is not enabled.");
        return new ApplicationFeedbackTab();
    }

    public ApplicationFeedbackTab setQuestionsOrCommentsFieldValue(String text) {
       SenchaWebElement inputBox = Driver.findVisible(QUESTIONS_OR_COMMENTS_INPUT_BOX_LOCATOR);

        inputBox.clear();
        inputBox.sendKeys(text);
        return new ApplicationFeedbackTab();
    }

    public ApplicationFeedbackTab setStarRating(int rate) {
        Driver.findIfExists(By.xpath(String.format(STAR_XPATH, "" + rate))).click();
        return new ApplicationFeedbackTab();
    }

    private boolean isSubmitButtonDisabled() {
        return Driver.findIfExists(SUBMIT_BUTTON_LOCATOR).hasClass("x-item-disabled");
    }
}
