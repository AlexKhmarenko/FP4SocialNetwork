import { expect } from "../base";
import { BASE_URL } from "../utils/constants";

export class SideBar {

    static SELECTOR = {
        SIDEBAR_FOR_HOME_PAGE: "sidebar_for_home_page",
        EXPLORE_POSTS_WRAPPER: "explore_posts_wrapper",
        HEADER_TEXT_ROUTES: "header_text_routes",
        FAB_OF_EXPLORE_TEXT: "fab_of_explore_text",
    };

    constructor(page) {
        this.page = page;
    }

    async explorePageOpen() {
        await this.page.getByText("Explore").click();
        await expect(this.page.getByTestId(SideBar.SELECTOR.EXPLORE_POSTS_WRAPPER)).toBeInViewport();
        await this.page.getByTestId(SideBar.SELECTOR.HEADER_TEXT_ROUTES).textContent("Explore");
    }
}