package com.danielmaia.businessmanagementsystem.UnitTests.RegisterUserPageTests;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterUserFieldsAppearTest {

    //@Test
    //void testFirstNameFieldAppears() throws Exception {
    //    WebClient webClient = new WebClient();
    //    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
    //    final HtmlPage localPage = webClient.getPage(baseUrl + "/signup");
    //    Assert.assertEquals(true, localPage.getElementByName("first_name").getAttribute("name").contains("first_name"));
    //    //Assert.assertEquals("input", localPage.getHtmlElementById("first_name").getNodeName());
    //}
    //
    //@Test
    //void testLastNameFieldAppears() throws Exception {
    //    checkUrlAndField("last_name");
    //}
    //
    //@Test
    //void testUsernameFieldAppears() throws Exception {
    //    checkUrlAndField("username");
    //}
    //
    //@Test
    //void testEmailFieldAppears() throws Exception {
    //    checkUrlAndField("email");
    //}
    //
    //@Test
    //void testPageFieldAppears() throws Exception {
    //    checkUrlAndField("password");
    //}
    //
    //@Test
    //void testConfirmPasswordFieldAppears() throws Exception {
    //    checkUrlAndField("confirm_password");
    //}
    //
    //// Checks the server which is being used, either the localhost or the live domain servers
    //// Then checks whether the fields are shown.
    //void checkUrlAndField(String field) throws IOException {
    //    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
    //    final WebClient webClient = new WebClient();
    //
    //    if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
    //        final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
    //        final HtmlPage localPage = webClient.getPage(localBaseUrl + "/signup");
    //        localPage.getElementsByName(field);
    //    } else {
    //        final HtmlPage livePage = webClient.getPage(baseUrl + "/signup");
    //        livePage.getElementsByName("username");
    //    }
    //
    //}

}

