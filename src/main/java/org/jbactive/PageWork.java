/*
 * © 2018 by Intellectual Reserve, Inc. All rights reserved.
 */

package org.jbactive;

    import com.gargoylesoftware.htmlunit.CookieManager;
    import com.gargoylesoftware.htmlunit.WebClient;
    import com.gargoylesoftware.htmlunit.html.*;
    import com.gargoylesoftware.htmlunit.util.Cookie;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.io.IOException;

/**
 *
 */
public class PageWork {

  private static final Logger LOGGER = LoggerFactory.getLogger(Work.class);


  private String pageAddress;
  private WebClient webClient;

  /**
   * Default constructor.
   */
  public PageWork(String pageAddress) {
    this(pageAddress, new WebClient());
  }

  /**
   * Constructor for testing.
   * @param webClient WebClient for simulating browser.
   */
  PageWork(String pageAddress, WebClient webClient) {
    this.pageAddress = pageAddress;
    this.webClient = webClient;
  }

  String doWork(String username, String myPwd) {
    LOGGER.info("Doing Work.");
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setAppletEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);

    CookieManager cookieManager = webClient.getCookieManager();
    cookieManager.setCookiesEnabled(true);

    webClient.getOptions().setJavaScriptEnabled(true);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    HtmlPage appPage;
    HtmlPage loginPage;
    try {
      appPage = webClient.getPage(pageAddress);
      HtmlAnchor signInLink = appPage.getAnchorByText("Sign In");
      loginPage = signInLink.click();
      HtmlForm loginForm = loginPage.getFormByName("eventForm");
      HtmlTextInput usernameField = loginForm.getInputByName("userName");
      HtmlPasswordInput passwordField = loginForm.getInputByName("password");
      HtmlButton submitButton = loginForm.getButtonByName("target");

      usernameField.setValueAttribute(username);
      passwordField.setValueAttribute(myPwd);
      submitButton.click();
    }
    catch (IOException e) {
      throw new PageWorkException("Received IO exception", e);
    }



    Cookie session = cookieManager.getCookie("mycookie");
    if (session == null) {
      throw new PageWorkException("No cookie was returned from the app.");
    }

    return session.getValue();
  }

}
