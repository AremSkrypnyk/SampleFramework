package ipreomobile.templates.ui;

public class ScrollableElement implements Scrollable {
    private static String JS_FIND_ELEMENT_BASE = "var element = document.%s;";
    private static String JS_SCROLL_ELEMENT_BASE = "element.style.webkitTransform = 'translate3d(%spx, %spx, 0px)';";
    private static String DEFAULT_JS_DESCRIPTION = "getElementsByClassName('x-scroll-scroller')[0]";

    private String jsElementDescription;
    private String jsFindElementCommand;
    private String jsScrollElementCommand;
    private String xValue;
    private String yValue;
    /*

    "var element = document.getElementsByClassName('x-scroll-scroller')[0];" +
                "element.style.webkitTransform = 'translate3d(0px, -' + (element.offsetHeight - document.body.clientHeight) + 'px, 0px)';"
     */

    public ScrollableElement() {
        init(DEFAULT_JS_DESCRIPTION);
    }
    public ScrollableElement(String jsElementDescription){
        init(jsElementDescription);
    }
    private void init(String jsElementDescription) {
        this.jsElementDescription = jsElementDescription;
        jsFindElementCommand = String.format(JS_FIND_ELEMENT_BASE, jsElementDescription);
    }

    @Override
    public void swipeBackward(boolean toTheEnd) {

    }

    @Override
    public void swipeForward(boolean toTheEnd) {

    }

    @Override
    public void swipeDown(boolean toTheEnd) {

    }

    @Override
    public void swipeUp(boolean toTheEnd) {
        xValue = "0";
        yValue = "' + (element.offsetHeight - document.body.clientHeight) + '";
    }
}
