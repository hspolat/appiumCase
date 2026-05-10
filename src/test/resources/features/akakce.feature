Feature: Akakce Mobile Automation

  Scenario: Search laptop with 4K filter and verify seller button
    Given User opens Akakce app
    When User searches for "Laptop"
    And User clicks on Filter button
    And User selects "4K" filter and clicks show products
    And User sorts products by "En Yüksek Fiyat"
    And User clicks on the 10. product in the results
    And User clicks on Go to Product button
    Then User verifies that Go to Seller button is displayed