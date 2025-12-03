@auth @toolshop
Feature: Toolshop Website - Register and Login

  Background:
    Given User accesses the Toolshop homepage
    And User accesses the Toolshop login page

  @positive @login-new
  Scenario: User can register new account and login new account
    When User clicks on register your account
    And User enters valid first name
    And User enters valid last name
    And User enters valid date of Birth
    And User enters valid street
    And User enters valid postal code
    And User enters valid city
    And User enters valid state
    And User selects valid country
    And User enters valid phone number
    And User enters valid email address
    And User enters valid password
    And User clicks on the register button
    And User successfully registered directs to login page
    And User enters valid email login
    And User enters valid password
    And User clicks on the login button
    Then User successfully login

  @positive @logout-new
  Scenario: User can login and logout using valid credential
    When User is already registered
    And User enters valid email
    And User enters valid password
    And User clicks on the login button
    And User successfully login directs to account page
    And User clicks logout
    Then User successfully logout


  @negative
  Scenario Outline: User fails login with invalid credentials
    When User enters invalid email <email>
    And User enters invalid password <password>
    And User clicks on the login button
    Then Verify error message <expectedError>
    Examples:
      | email                     | password    | expectedError               |
      | ""                        | "pass123"   | "Email is required"         |
      | "random123@mail.com"      | ""          | "Password is required"      |
      | "lorem"                   | "test123"   | "Email format is invalid"   |
      | "automationtest@mail.com" | "1"         | "Password length is invalid"|
      | "automationtest@mail.com" | "test1234!" | "Invalid email or password" |

