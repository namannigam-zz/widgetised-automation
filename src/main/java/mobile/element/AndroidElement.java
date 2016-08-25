package mobile.element;

import mobile.base.StartUp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AndroidElement extends StartUp {

    /**
     * Background Elements
     */
    public static class BackGround {
        public static final WebElement STATUS_BAR = androidDriver.findElement(By.id("statusBarBackground"));
        public static final WebElement NAVIGATION_BAR = androidDriver.findElement(By.id("navigationBarBackground"));
    }

    /**
     * Login Elements
     */
    public static class Login {
        public static final WebElement APP_BAR = androidDriver.findElement(By.id("app_bar"));


        public static class Toolbar {
            public static final WebElement TOOLBAR_LAYOUT = androidDriver.findElement(By.id("toolbar_layout"));
            public static final WebElement TOOLBAR = androidDriver.findElement(By.id("toolbar"));
            public static final WebElement SKIP_BUTTON = androidDriver.findElement(By.id("btn_skip"));
            public static final WebElement FLIPKART_LOGO = androidDriver.findElement(By.id("flipkart_logo"));
            public static final WebElement BANNER = androidDriver.findElement(By.id("banner_text"));
        }

        public static class DataHolder {
            public static final WebElement DATA_HOLDER = androidDriver.findElement(By.id("login_data_holder"));
            public static final WebElement BANNER = androidDriver.findElement(By.id("banner_text"));

            public static class EmailMobile {
                public static final WebElement MOBILE_EMAIL_EDITTEXT = androidDriver.findElement(By.id("et_mobile"));
                public static final WebElement MOBILE_EMAIL_INPUT = androidDriver.findElement(By.id("mobileNo"));
                public static final WebElement CLEAR_BUTTON = androidDriver.findElement(By.id("clbt"));
            }

            public static class PasswordView {
                public static final WebElement PASSWORD_VIEW = androidDriver.findElement(By.id("passwordview"));
                public static final WebElement PASSWORD_INPUT = androidDriver.findElement(By.id("et_password"));
                public static final WebElement FORGOT_BUTTON = androidDriver.findElement(By.id("forgot"));
                public static final WebElement SHOW_BUTTON = androidDriver.findElement(By.id("show"));
            }

            public static final WebElement HINT_FOCUS_TEXT = androidDriver.findElement(By.id("hintFocusText"));
            public static final WebElement LOGIN_BUTTON = androidDriver.findElement(By.id("btn_mlogin"));
            public static final WebElement SIGNUP_BUTTON = androidDriver.findElement(By.id("btn_msignup"));
        }

        public static class SocialHolder {
            public static final WebElement SOCIAL_HOLDER = androidDriver.findElement(By.id("social_holder"));
            public static final WebElement SOCIAL_TEXT = androidDriver.findElement(By.id("social_text"));
            public static final WebElement FACEBOOK_LOGIN = androidDriver.findElement(By.id("btn_login_facebook"));
            public static final WebElement GOOGLE_LOGIN = androidDriver.findElement(By.id("btn_login_google"));
        }
    }

    /**
     * SignUp Elements
     */
    public static class SignUp {
        public static final WebElement APP_BAR = androidDriver.findElement(By.id("app_bar"));

        public static class Toolbar {
            public static final WebElement TOOLBAR_LAYOUT = androidDriver.findElement(By.id("toolbar_layout"));
            public static final WebElement TOOLBAR = androidDriver.findElement(By.id("toolbar"));
            public static final WebElement SKIP_BUTTON = androidDriver.findElement(By.id("btn_skip"));
            public static final WebElement FLIPKART_LOGO = androidDriver.findElement(By.id("flipkart_logo"));
            public static final WebElement BANNER = androidDriver.findElement(By.id("banner_text"));
        }

        public static class DataHolder {
            public static final WebElement DATA_HOLDER = androidDriver.findElement(By.id("sign_data_holder"));
            public static final WebElement BANNER = androidDriver.findElement(By.id("banner_text"));

            public static class EmailMobile {
                public static final WebElement MOBILE_EDITTEXT = androidDriver.findElement(By.id("et_mobile"));
                public static final WebElement MOBILE_INPUT = androidDriver.findElement(By.id("mobileNo"));
                public static final WebElement CLEAR_BUTTON = androidDriver.findElement(By.id("clbt"));
            }

            public static class CountryCode {
                public static final WebElement COUNTRY_CODE = androidDriver.findElement(By.id("country_code_info"));
                public static final WebElement COUNTRY_CODE_DIVIDER =
                        androidDriver.findElement(By.id("countryDivider"));
            }

            public static final WebElement SIGNUP_BUTTON = androidDriver.findElement(By.id("btn_msignup"));
            public static final WebElement LOGIN_BUTTON = androidDriver.findElement(By.id("btn_mlogin"));

        }
    }

    /**
     * FlyOut Elements
     */
    public static class FlyOut {
        public static final WebElement FLYOUT_VIEW = androidDriver.findElement(By.id("flyout_navigation_view"));
        public static final WebElement FLYOUT_DESIGN = androidDriver.findElement(By.id("design_navigation_view"));

        public static class Header {
            public static final WebElement FLYOUT_HEADER = androidDriver.findElement(By.id("flyout_header"));
            public static final WebElement FLYOUT_LOGO = androidDriver.findElement(By.id(""));
            public static final WebElement FLYOUT_TEXT = androidDriver.findElement(By.id(""));
            public static final WebElement FLYOUT_ICON = androidDriver.findElement(By.id(""));
        }

        public static class Widget {
            public static final WebElement WIDGET_CONTAINER_VIEW =
                    androidDriver.findElement(By.id("widget_container_view"));
            public static final WebElement PARENT_CONTAINER =
                    androidDriver.findElement(By.id("flyout_parent_container"));
            public static final WebElement PARENT_TITLE = androidDriver.findElement(By.id("flyout_parent_title"));
            public static final WebElement PARETN_ICON = androidDriver.findElement(By.id("flyout_parent_icon"));
        }
    }

    /**
     * Search Elements
     */
    public static class Search {

    }

    /**
     * Product Elements
     */
    public static class Product {

    }
}