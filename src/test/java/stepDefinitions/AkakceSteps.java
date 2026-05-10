package stepDefinitions;

import io.cucumber.java.en.*;
import pages.*;
import org.testng.Assert;

public class AkakceSteps {
    HomePage homePage;
    ProductListPage productListPage;
    FilterPage filterPage;
    ProductDetailPage productDetailPage;

    @Given("User opens Akakce app")
    public void user_opens_akakce_app() {
        homePage = new HomePage();
    }

    @When("User searches for {string}")
    public void user_searches_for(String product) {
        productListPage = homePage.searchForProduct(product);
    }

    @And("User clicks on Filter button")
    public void user_clicks_on_filter_button() {
        filterPage = productListPage.openFilter();
    }

    @And("User selects {string} filter and clicks show products")
    public void user_selects_filter(String filterText) {
        productListPage = filterPage.applyFilter(filterText);
    }

    @And("User sorts products by {string}")
    public void user_sorts_products_by(String sortType) {
        productListPage.sortBy(sortType);
    }

    @And("User clicks on the {int}. product in the results")
    public void user_clicks_on_the_product_in_the_results(int index) {
        productDetailPage = productListPage.selectProductByIndex(index);
    }

    @And("User clicks on Go to Product button")
    public void user_clicks_on_go_to_product() {
        productDetailPage.clickGoToProduct();
    }

    @Then("User verifies that Go to Seller button is displayed")
    public void user_verifies_seller_button() {
        Assert.assertTrue(productDetailPage.isSellerButtonDisplayed(), "Satıcıya git butonu görünmüyor!");
    }
}