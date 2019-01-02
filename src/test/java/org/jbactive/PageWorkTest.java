package org.jbactive;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.testng.Assert.assertEquals;

/**
 *
 */
@SuppressWarnings("squid:S2068") //Password for Test only
public class PageWorkTest {

  private static final String USERNAME = "fake-username";
  private static final String MY_PASSWORD = "fake-password";

  private PageWork testModel;

  @Mock
  private WebClient mockWebClient;

  @Mock
  private CookieManager mockCookieManager;

  @Mock
  private HtmlPage mockLoginPage;

  @Mock
  private HtmlForm mockLoginForm;

  @Mock
  private HtmlTextInput mockUsernameField;

  @Mock
  private HtmlPasswordInput mockPasswordField;

  @Mock
  private HtmlSubmitInput mockLoginButton;

  @Mock
  private HtmlPage mockPortalPage;

  @Mock
  private HtmlAnchor mockPortalSignInAnchor;

  @BeforeMethod
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);

    when(mockWebClient.getCookieManager()).thenReturn(mockCookieManager);

    Cookie ldsCookie = new Cookie("domain", "JSESSIONID", "cookie");
    when(mockCookieManager.getCookie("JSESSIONID")).thenReturn(ldsCookie);

    Cookie portalCookie = new Cookie("domain", "sessionid", "session-cookie");
    when(mockCookieManager.getCookie("sessionid")).thenReturn(portalCookie);

    when(mockWebClient.getPage("https://ident.org/sso/UI/Login?service=credentials")).thenReturn(mockLoginPage);
    when(mockLoginPage.getFormByName("Login")).thenReturn(mockLoginForm);

    // TODO: For some reason these three lines are throwing elementnotfoundexceptions or something like that. It's odd.
    when(mockLoginForm.getInputByName("IDToken1")).thenReturn(mockUsernameField);
    when(mockLoginForm.getInputByName("IDToken2")).thenReturn(mockPasswordField);
    when(mockLoginForm.getInputByName("Login.Submit")).thenReturn(mockLoginButton);

    when(mockWebClient.getPage("https://app")).thenReturn(mockPortalPage);
    when(mockPortalPage.getAnchorByText("Sign In")).thenReturn(mockPortalSignInAnchor);

    testModel = new PageWork("mock-page", mockWebClient);
  }

  @Test(enabled = false)
  public void shouldEnableCookies() {
    testModel.doWork(USERNAME, MY_PASSWORD);

    verify(mockCookieManager).setCookiesEnabled(true);
  }

  @Test(enabled = false)
  public void shouldSetLoginValuesBeforeClickingButton() throws IOException {
    InOrder inOrder = inOrder(mockUsernameField, mockPasswordField, mockLoginButton);

    testModel.doWork(USERNAME, MY_PASSWORD);

    inOrder.verify(mockUsernameField).setValueAttribute(USERNAME);
    inOrder.verify(mockPasswordField).setValueAttribute(MY_PASSWORD);
    inOrder.verify(mockLoginButton).click();
  }

  @Test(enabled = false)
  public void shouldReturnTheValueOfTheFsSessionIdCookie() {
    String cookieValue = testModel.doWork(USERNAME, MY_PASSWORD);

    assertEquals(cookieValue, "session-cookie");
  }

}
