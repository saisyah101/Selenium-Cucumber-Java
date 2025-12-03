@productpage @toolshop
Feature: Toolshop Website - Product Page Interactions

  Background:
    Given User accesses the Toolshop homepage

  @search-product @guest
  Scenario Outline: User can search product as a guest
    When User enters <query> on search bar
    And User clicks on the search button
    Then Search caption contains valid <query>
    And Products matching <query> are displayed
    Examples:
    | query    |
    | "Pliers" |
    | "Safety" |
    | "Saw"    |

  @search-product-reset @guest
  Scenario: User can search products then reset search filter as a guest
    When User enters valid query on search bar
    And User clicks on the search button
    And Valid products are displayed
    And User clicks on the reset button
    Then Default products should be displayed

  @filter-price @guest
  Scenario Outline: User can filter product by setting specific price range as a guest
    When User sets price range from <minPrice> to <maxPrice>
    Then Price range should be <minPrice> to <maxPrice>
    And Expected product <minPriceProduct> and <maxPriceProduct> should be displayed
    Examples:
    | minPrice   | maxPrice   | minPriceProduct       | maxPriceProduct        |
    | "1"        | "10"       | "Washers"             |"Slip Joint Pliers"     |
    | "50"       | "100"      | "Sheet Sander"        |"Drawer Tool Cabinet"   |
    | "100"      | "200"      | "Random Orbit Sander" |"Workbench with Drawers"|


  @sort-product @guest
  Scenario Outline: User can sort products by specific parameter as a guest
    When User sorts products by <sortOption>
    Then Products should be sorted by <sortType> in <sortOrder>
    Examples:
    | sortOption            | sortType | sortOrder    |
    | "Name (A - Z)"        | "name"   | "ascending"  |
    | "Name (Z - A)"        | "name"   | "descending" |
    | "Price (High - Low)"  | "price"  | "descending" |
    | "Price (Low - High)"  | "price"  | "ascending"  |


  @select-category @guest
  Scenario Outline: User can filter products by selecting category as a guest
    When User selects <category>
    And Category <category> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    Examples:
      | category      |  expectedProduct | unexpectedProduct                                                                                   |
      | "Hand Saw"    | "Wood Saw"       | "Chisels Set, Wood Carving Chisels, Swiss Woodcarving Chisels"                                      |
      | "Power Tools" | "Sheet Sander, Belt Sander, Circular Saw, Cordless Drill 24V, Cordless Drill 12V" | "Wood Saw"                         |
      | "Chisels"     | "Chisels Set, Wood Carving Chisels, Swiss Woodcarving Chisels"                    | "Combination Pliers, Bolt Cutters" |


  @select-brand @guest
  Scenario Outline: User can filter products by brand as a guest
    When User selects <brand>
    And Category <brand> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    Examples:
      | brand                    |  expectedProduct   | unexpectedProduct |
      | "ForgeFlex Tools"        | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer, Wood Saw, Adjustable Wrench, Angled Spanner" | "Claw Hammer, Bolt Cutters, Long Nose Pliers, Slip Joint Pliers, Chisels Set, Belt Sander, Cordless Drill 12V, Drawer Tool Cabinet, Screws" |
      | "MightyCraft Hardware"   | "Claw Hammer, Bolt Cutters, Long Nose Pliers, Slip Joint Pliers, Chisels Set, Belt Sander, Cordless Drill 12V, Drawer Tool Cabinet, Screws"                                | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer, Wood Saw, Adjustable Wrench, Angled Spanner" |

  @select-sustainability @guest
  Scenario Outline: User can select product by brand as a guest
    When User selects <type>
    And Category <type> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    And The eco badges are displayed
    Examples:
      | type                              |  expectedProduct   | unexpectedProduct |
      | "Show only eco-friendly products" | "Wood Saw, Safety Goggles, Safety Helmet Face Shield, Protective Gloves, Super-thin Protection Gloves, Construction Helmet, Ear Protection, Drawer Tool Cabinet, Tool Cabinet" | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer" |


  @add-favorites @guest
  Scenario: User fails to add product to favorites as a guest
    When User navigates to product detail page
    And User should see product detail page displayed correctly
    And User clicks the add to favorites button
    Then Valid error alert failed add to favorites list should be displayed


  @login-search-product @authenticated
  Scenario Outline: Logged in user can search products
    When User is logged in
    And User accesses the homepage
    And User enters <query> on search bar
    And User clicks on the search button
    Then Search caption contains valid <query>
    And Products matching <query> are displayed
    Examples:
      | query         |
      | "Screwdriver" |
      | "Tape"        |
      | "Helmet"      |

  @login-search-reset @authenticated
  Scenario: Logged in user can search products then reset search filter
    When User is logged in
    And User accesses the homepage
    And User enters valid query on search bar
    And User clicks on the search button
    And Valid products are displayed
    And User clicks on the reset button
    Then Default products should be displayed

  @login-filter-price @authenticated
  Scenario Outline: Logged in user can filter products by setting specific price range
    When User is logged in
    And User accesses the homepage
    And User sets price range from <minPrice> to <maxPrice>
    Then Price range should be <minPrice> to <maxPrice>
    And Expected product <minPriceProduct> and <maxPriceProduct> should be displayed
    Examples:
      | minPrice   | maxPrice   | minPriceProduct       | maxPriceProduct        |
      | "1"        | "10"       | "Washers"             |"Slip Joint Pliers"     |
      | "50"       | "100"      | "Sheet Sander"        |"Drawer Tool Cabinet"   |
      | "100"      | "200"      | "Random Orbit Sander" |"Workbench with Drawers"|


  @login-sort-product @authenticated
  Scenario Outline: Logged in user can sort products by specific parameter
    When User is logged in
    And User accesses the homepage
    And User sorts products by <sortOption>
    Then Products should be sorted by <sortType> in <sortOrder>
    Examples:
      | sortOption            | sortType | sortOrder    |
      | "Name (A - Z)"        | "name"   | "ascending"  |
      | "Name (Z - A)"        | "name"   | "descending" |
      | "Price (High - Low)"  | "price"  | "descending" |
      | "Price (Low - High)"  | "price"  | "ascending"  |


  @login-select-category @authenticated
  Scenario Outline: Logged in user can filter products by selecting category
    When User is logged in
    And User accesses the homepage
    And User selects <category>
    And Category <category> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    Examples:
      | category      |  expectedProduct | unexpectedProduct                                                                                   |
      | "Hand Saw"    | "Wood Saw"       | "Chisels Set, Wood Carving Chisels, Swiss Woodcarving Chisels"                                      |
      | "Power Tools" | "Sheet Sander, Belt Sander, Circular Saw, Cordless Drill 24V, Cordless Drill 12V" | "Wood Saw"                         |
      | "Chisels"     | "Chisels Set, Wood Carving Chisels, Swiss Woodcarving Chisels"                    | "Combination Pliers, Bolt Cutters" |


  @login-select-brand @authenticated
  Scenario Outline: Logged in user can filter products by brand
    When User is logged in
    And User accesses the homepage
    And User selects <brand>
    And Category <brand> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    Examples:
      | brand                    |  expectedProduct   | unexpectedProduct |
      | "ForgeFlex Tools"        | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer, Wood Saw, Adjustable Wrench, Angled Spanner" | "Claw Hammer, Bolt Cutters, Long Nose Pliers, Slip Joint Pliers, Chisels Set, Belt Sander, Cordless Drill 12V, Drawer Tool Cabinet, Screws" |
      | "MightyCraft Hardware"   | "Claw Hammer, Bolt Cutters, Long Nose Pliers, Slip Joint Pliers, Chisels Set, Belt Sander, Cordless Drill 12V, Drawer Tool Cabinet, Screws"                                | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer, Wood Saw, Adjustable Wrench, Angled Spanner" |

  @login-select-sustainability @authenticated
  Scenario Outline: Logged in user can select products by brand
    When User is logged in
    And User accesses the homepage
    And User selects <type>
    And Category <type> is selected
    Then Expected product should include <expectedProduct>
    And Unexpected product <unexpectedProduct> should not be displayed
    And The eco badges are displayed
    Examples:
      | type                              |  expectedProduct   | unexpectedProduct |
      | "Show only eco-friendly products" | "Wood Saw, Safety Goggles, Safety Helmet Face Shield, Protective Gloves, Super-thin Protection Gloves, Construction Helmet, Ear Protection, Drawer Tool Cabinet, Tool Cabinet" | "Claw Hammer with Shock Reduction Grip, Hammer,  Thor Hammer, Sledgehammer, Claw Hammer with Fiberglass Handle, Court Hammer" |


  @login-add-favorites @authenticated
  Scenario: Logged in user successfully add products to favorites
    When User is logged in
    And User accesses the homepage
    And User navigates to product detail page
    And User should see product detail page displayed correctly
    And User clicks the add to favorites button
    And User successfully added product to favorites
    And User navigates to My Favorites page
    Then My favorites page displayed correctly
    And The added favorites product should be displayed


  @login-add-favorite-remove-favorite @authenticated
  Scenario: Logged in user successfully removes product from favorites
    When User is logged in
    And User accesses the homepage
    And User adds product to favorites
    And User navigates to My Favorites page
    And User removes product from favorites
    Then Product should be removed successfully
