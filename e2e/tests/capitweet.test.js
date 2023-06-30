import { describe, test, expect, beforeEach } from "../base";
import { BASE_API_URL } from "../utils/constants";

describe("404 Not Found Page", () => {
    test("should display the feed and login UI at any page for non-logged in user", async ({
                                                                                               commonActions,
                                                                                               notFoundPage
                                                                                           }) => {
        await notFoundPage.openNonExistingPage();
        await commonActions.verifyLoggedOutUi();
    });
    test("should display \"404 page not found\" when visiting a non-existent page for logged in user", async ({
                                                                                                                  commonActions,
                                                                                                                  notFoundPage
                                                                                                              }) => {
        await commonActions.login();
        await notFoundPage.openNonExistingPage();
        await notFoundPage.verify404Label();
    });
});

describe("Home Page", () => {
    test("Add post success", async ({ commonActions, homePage, page }) => {
        await commonActions.login();
        await homePage.openHomePage();
        await page.waitForLoadState();
        await homePage.verifyAddPost();
    });

    test("Get explore route success", async ({ commonActions, homePage, sideBar, page }) => {
        await commonActions.login();
        await homePage.openHomePage();
        await page.waitForLoadState();
        await sideBar.explorePageOpen();
    });

    test("Get notification route success", async ({ commonActions, homePage, sideBar, page }) => {
        await commonActions.login();
        await homePage.openHomePage();
        await page.waitForLoadState();
        await sideBar.notificationsPageOpen();
    });

    test("Get message route success", async ({ commonActions, homePage, sideBar, page }) => {
        await commonActions.login();
        await homePage.openHomePage();
        await page.waitForLoadState();
        await sideBar.messagePageOpen();
    });

    test("Add like success", async ({ commonActions, homePage, page }) => {
        await commonActions.login();
        await homePage.openHomePage();
        await page.waitForLoadState();
        // await page.pause();
        await homePage.verifyAddPost();
        await homePage.verifyAddLikeHandle();
    });
});