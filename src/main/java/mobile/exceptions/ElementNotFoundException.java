package mobile.exceptions;

import mobile.base.Locator;

public class ElementNotFoundException extends RuntimeException {

    private String page;
    private Locator locator;

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(Locator locator, String page) {
        this.locator = locator;
        this.page = page;
    }

    public ElementNotFoundException(Locator locator, String page, String message) {
        super(message);
        this.page = page;
        this.locator = locator;
    }

    public ElementNotFoundException(Locator locator, String page, String message, Throwable cause) {
        super(message, cause);
        this.page = page;
        this.locator = locator;
    }

    @Override
    public String toString() {
        super.toString();
        return page + " does not contain the element \"" + locator.key + "\" identified by locator: " + locator.value;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " for : " + locator;
    }

    public String getPage() {
        return page;
    }

}