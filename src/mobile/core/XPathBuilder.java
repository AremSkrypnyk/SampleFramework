package ipreomobile.core;

import org.testng.Assert;

public class XPathBuilder {
    private String xPathHead = "";
    private String xPathTail = "";
    private static final String CONTAINS = "[contains(%s, '%s')]";

    public XPathBuilder byXpathPart(String xpath) {
        this.xPathHead = this.xPathHead + xpath;
        return this;
    }

    public XPathBuilder byTag(String tagName) {
        this.xPathHead = this.xPathHead + this.xPathTail + "//" + tagName;
        this.xPathTail = "";
        return this;
    }

    public XPathBuilder byIndex(int index) {
        this.xPathHead = this.xPathHead + "[" + index + "]";
        return this;
    }

    public XPathBuilder byCurrentItem(){
        this.xPathHead = "." + this.xPathHead;
        return this;
    }

    public XPathBuilder byClassName(String... classNames) {
        return this.byTag("*").withClassName(classNames);
    }

    public XPathBuilder byClassNameEquals(String className) {
        return this.byTag("*").withClassNameEquals(className);
    }

    public XPathBuilder byName(String name) {
        return this.byTag("*").withName(name);
    }

    public XPathBuilder byText(String text) {
        return this.byTag("*").withText(text);
    }

    public XPathBuilder byTextContains(String text) {
        return this.byTag("*").withTextContains(text);
    }

    public XPathBuilder withXpathPart(String xpath) {
        this.xPathHead = this.xPathHead + "[" + xpath;
        this.xPathTail = "]" + this.xPathTail;
        return this;
    }

    public XPathBuilder byId(String fullId) { return this.byTag("*").withId(fullId); }

    public XPathBuilder byIdContains(String idPart) {
        return this.byTag("*").withIdContains(idPart);
    }

    public XPathBuilder byTranslate3dByY(int yValue) { return this.byTag("*").withTranslate3dByY(yValue); }

    public XPathBuilder byAttribute(String attributeName, String attributeValue) { return this.byTag("*").withAttribute(attributeName, attributeValue); }

    public XPathBuilder byPlaceholder(String placeholderValue) {return this.byAttribute("placeholder", placeholderValue);}

    public XPathBuilder or() {
        build();
        this.xPathHead = this.xPathHead + " | ";
        this.xPathTail = "";
        return this;
    }

    public XPathBuilder withAttribute(String attributeName, String attributeValue) {
        this.xPathHead = this.xPathHead + "[@"+attributeName+"='"+attributeValue+"']";
        return this;
    }

    public XPathBuilder withClassNameEquals(String className) {
        return withAttribute("class", className);
    }

    public XPathBuilder withName(String name) {
        return withAttribute("name", name);
    }

    public XPathBuilder withId(String id) {
        return withAttribute("id", id);
    }

    public XPathBuilder withClassName(String... classNames) {
        for (int i = 0; i< classNames.length; i++) {
            classNames[i] = "contains(@class, '"+classNames[i]+"')";
        }
        this.xPathHead = this.xPathHead + "[" + String.join(" and ", classNames) + "]";
        return this;
    }

    public XPathBuilder withNoClassName(String... classNames) {
        for (int i = 0; i< classNames.length; i++) {
            classNames[i] = "contains(@class, '"+classNames[i]+"')";
        }
        this.xPathHead = this.xPathHead + "[not(" + String.join(" and ", classNames) + ")]";
        return this;
    }

    public XPathBuilder withIdContains(String idPart) {
        this.xPathHead = this.xPathHead + String.format(CONTAINS, "@id", idPart);
        return this;
    }

    public XPathBuilder withText(String text) {
        this.xPathHead = this.xPathHead + "[" + getTrimmedText() + "=" + escapeQuotesInText(text) + "]";
        return this;
    }

    public XPathBuilder withTextIgnoreCase(String text) {
        this.xPathHead = this.xPathHead + "[" + getTrimmedTextIgnoreCase() + "=" + escapeQuotesInText(text) + "]";
        return this;
    }

    public XPathBuilder withChildText(String text) {
        return withChildTag("*").withText(text);
    }

    public XPathBuilder withChildTextIgnoreCase(String text) {
        return withChildTag("*").withTextIgnoreCase(text);
    }

    public XPathBuilder withTextContains(String text) {
        this.xPathHead = this.xPathHead + "[contains(" + getTrimmedText() + ", " + escapeQuotesInText(text) + ")]";
        return this;
    }

    public XPathBuilder withTextContainsIgnoreCase(String text) {
        this.xPathHead = this.xPathHead + "[contains(" + getTrimmedTextIgnoreCase() + ", " + escapeQuotesInText(text) + ")]";
        return this;
    }

    public XPathBuilder withChildTextContains(String text) {
        return withChildTag("*").withTextContains(text);
    }

    public XPathBuilder withChildTextContainsIgnoreCase(String text) {
        return withChildTag("*").withTextContainsIgnoreCase(text);
    }

    public XPathBuilder withChildTag(String childTag) {
        this.xPathHead = this.xPathHead + "[.//" + childTag;
        this.xPathTail = "]" + this.xPathTail;
        return this;
    }

    public XPathBuilder withNoTranslate3dNegativeByY(){
        this.xPathHead = this.xPathHead + "[not(contains(@style, 'translate3d(0px, -10000px, 0px)'))]";

        //translate3d(0px, -10000px, 0px); height: 100%;
        //translate3d(0px, -10000px, 0px)
        return this;
    }

    public XPathBuilder withTranslate3dNegativeByY(){
        this.xPathHead = this.xPathHead + "[contains(@style, 'translate3d(0px, -10000px, 0px)')]";
        return this;
    }

    public XPathBuilder withTranslate3dByY(int yValue) {
        this.xPathHead = this.xPathHead + "//*[contains(@style, '-webkit-transform: translate3d(0px, "+yValue+"px, 0px)')]";
        return this;
    }

    private String getTrimmedText(){
        return "translate(normalize-space(text()), '\u00A0', ' ')";
    }

    private String getTrimmedTextIgnoreCase(){
        return "translate(normalize-space(text()), '\u00A0ABCDEFGHIJKLMNOPQRSTUVWXYZ', ' abcdefghijklmnopqrstuvwxyz')";
    }

    private String escapeQuotesInText(String text) {
        Assert.assertNotNull(text, "Text for building XPath cannot be null.");
        String result;
        if (text.contains("'")) {
            result = "concat(\"";
            result += text.replaceAll("'", "\", \"'\", \"");
            result += "\")";
        } else {
            result = "'" + text + "'";
        }
        return result;
    }

    @Override
    public String toString(){
        return this.build();
    }

    public String build() {
        return xPathHead + xPathTail;
    }

}
