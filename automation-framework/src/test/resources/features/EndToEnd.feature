@E2E @toolshop
Feature: Toolshop Website - End to End Transaction

  Background:
    Given User is logged in
    And User accesses the homepage

  @user-transaction
  Scenario Outline: User successfully checkout a product with various payment types
    When User adds product to cart
    And User clicks cart icon
    And User navigates to Checkout page section 1: Cart
    And Detail of added product should be displayed correctly
    And User clicks proceed checkout button 1
    And User navigates to Checkout page section 2: Sign In
    And User clicks proceed checkout button 2
    And User navigates to Checkout page section 3: Billing Address
    And User clicks proceed checkout button 3
    And User navigates to Checkout page section 4: Payment
    And User selects payment <paymentType>
    And Valid input fields displayed correctly
    And User enters valid payment data
    And User clicks Confirm button
    And Verify success message displayed correctly
    And User clicks Confirm button
    And User successfully checkout the product
    And User navigates to My Invoice page
    And My Invoice page displayed correctly
    And User navigates to Details page
    Then Details page displayed correctly
    Examples:
      | paymentType         |
      | "Bank Transfer"     |
      | "Cash on Delivery"  |
      | "Credit Card"       |
      | "Buy Now Pay Later" |
      | "Gift Card"         |