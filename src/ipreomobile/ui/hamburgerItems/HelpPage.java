package ipreomobile.ui.hamburgerItems;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.blocks.ConfirmationDialog;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class HelpPage extends ScreenCard {
    public HelpPage(){
        addOneTimeCheckpoint(By.className(WHAT_CAN_DO_CONTAINER_CLASSNAME));
        waitReady();
    }

    private static final String WHAT_CAN_DO_CONTAINER_CLASSNAME     = "what-can-do";
    private static final String QUESTIONS_INFO_CONTAINER_CLASSNAME  = "questions-info";
    private static final String ACCOUNT_INFO_CONTAINER_CLASSNAME    = "get-account-info";
    private static final String PROTECTION_INFO_CONTAINER_CLASSNAME = "protection-info";

    private static final String WHAT_CAN_DO_HEADER_TEXT             = "What can I do on the ipreomobile application?";
    private static final String QUESTION_HEADER_TEXT                = "Have a question?";
    private static final String ACCOUNT_HELP_HEADER_TEXT            = "Account Help";
    private static final String PROTECTION_INFO_HEADER_TEXT         = "How is my information protected?";

    private static final String CALL_ME_LINK_XPATH                  = new XPathBuilder().byClassName("email").withText("CallMeNow@Ipreo.com").build();
    private static final String GEORGE_SUPER_AM_LINK_XPATH          = new XPathBuilder().byClassName("email").withText("George SuperAM").build();

    //WhatCanDo
    public void verifyWhatCanDoHelpPageSections(){
        HelpContainer hc = new HelpContainer(WHAT_CAN_DO_CONTAINER_CLASSNAME);
        hc.setHeaderContent(WHAT_CAN_DO_HEADER_TEXT);
        hc.verify();
    }

    //QuestionsInfo
    public void verifyQuestionsInfoHelpPageSections(){
        HelpContainer hc = new HelpContainer(QUESTIONS_INFO_CONTAINER_CLASSNAME);
        hc.setHeaderContent(QUESTION_HEADER_TEXT);
        hc.setLinks(CALL_ME_LINK_XPATH);
        hc.verify();
    }

    //AccountHelp
    public void verifyAccountInfoHelpPageSections(){
        HelpContainer hc = new HelpContainer(ACCOUNT_INFO_CONTAINER_CLASSNAME);
        hc.setHeaderContent(ACCOUNT_HELP_HEADER_TEXT);
        hc.setLinks(GEORGE_SUPER_AM_LINK_XPATH);
        hc.verify();
    }

    //ProtectionInfo
    public void verifyProtectionInfoHelpPageSections(){
        HelpContainer hc = new HelpContainer(PROTECTION_INFO_CONTAINER_CLASSNAME);
        hc.setHeaderContent(PROTECTION_INFO_HEADER_TEXT);
        hc.verify();
    }
}

class HelpContainer {

    String headerClassName = "x-docked-top";
    String bodyClassName = "x-panel-inner";
   SenchaWebElement container;
    String headerContent;
    String linkXpath;

    public HelpContainer(String containerClassName) {
        this.container = Driver.findFirst(By.className(containerClassName));
    }

    public void setHeaderContent(String headerContent) {
        this.headerContent = headerContent;
    }

    public void setLinks(String linkXpath) {
        this.linkXpath = linkXpath;
    }

    public void verify() {
        verifyHeader();
        verifyBody();
        verifyLinks();
    }

    private void verifyHeader() {
        String actualHeaderText = container.findElement(By.className(headerClassName)).getText().trim();
        Assert.assertEquals(actualHeaderText, headerContent);
    }

    private void verifyBody() {
        String innerText = container.findElement(By.className(bodyClassName)).getText();
        Assert.assertTrue(innerText != null && !innerText.isEmpty());
    }

    private void verifyLinks() {
        if(linkXpath != null && !linkXpath.isEmpty()) {
           SenchaWebElement link = Driver.findOne(By.xpath(linkXpath), container);
            link.click();
            ConfirmationDialog dialog = new ConfirmationDialog();
            dialog.verify3rdPartyAppMessage();
        }
    }
}