# Selenium Cucumber Java - E-Commerce Website Test Automation
A BDD (Behavior-Driven Development) test automation framework for  [Practice Software Testing ToolShop](https://practicesoftwaretesting.com/) e-commerce website testing using Selenium WebDriver, Cucumber, and Java with TestNG integration.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Scenario](#test-scenario)
- [Author](#author)

## Overview
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
This framework demonstrates test automation practices for testing ToolShop e-commerce website. Built with BDD methodology, it provides comprehensive test coverage for authentication, product management, checkout processes, and end-to-end user journeys.

## Features
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<li>BDD Framework: cucumber with Gherkin syntax for readable test scenarios</li>
<li>Page Object Model: modular and maintainable page object architecture</li>
<li>Multi-Browser Support: chrome and Firefox with configurable options</li>
<li>Headless Mode: configurable headless execution for CI/CD</li>
<li>Parallel Execution: TestNG parallel test execution support</li>
<li>Dynamic Test Data: random data generation for unique test scenarios</li>
<li>Video Recording: optional screen recording with WebM conversion</li>
<li>Comprehensive Assertions: detailed validation methods</li>

## Project Structure
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<pre>
automation-framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── driver/
│   │       │   └── DriverFactory.java           # WebDriver management
│   │       ├── pageObjects/
│   │       │   ├── Base_PO.java                 # Base page object with utilities
│   │       │   ├── Auth_PO.java                 # Authentication pages
│   │       │   ├── Product_PO.java              # Product listing & filtering
│   │       │   └── EndToEnd_PO.java             # Checkout & transaction flow
│   │       ├── properties/
│   │       │   └── config.properties            # Configuration file
│   │       └── utils/
│   │           └── Global_Vars.java             # Global constants & test data
│   └── test/
│       ├── java/
│       │   ├── runners/
│       │   │   ├── MainRunner.java              # Main Runner
│       │   │   └── testng/
│       │   │       └── testng.xml               # TestNG configuration
│       │   └── stepDefinitions/
│       │       ├── base/
│       │       │   └── Hooks.java               # Before/After hooks
│       │       ├── Auth_Steps.java              # Authentication step definitions
│       │       ├── ProductPage_Steps.java       # Product page step definitions
│       │       └── EndToEnd_Steps.java          # E2E transaction step definitions
│       └── resources/
│           └── features/
│               ├── Auth.feature                 # Authentication scenarios
│               ├── ProductPage.feature          # Product page scenarios
│               ├── Checkout.feature             # Checkout scenarios
│               └── EndToEnd.feature             # E2E transaction scenarios
├── pom.xml                                      # Maven dependencies
├── .gitignore
└── README.md
</pre>

## Prerequisites
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<li>Java JDK 21 or higher</li>
<li>Maven 3.6+</li>
<li>Chrome Browser (for Chrome tests)</li>
<li>Firefox Browser (for Firefox tests)</li>
<li>FFmpeg (optional, for video recording conversion)</li>

## Installation
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<h3>1. Clone the repository</h3>
<pre>git clone https://github.com/saisyah101/Selenium-Cucumber-Java.git
cd Selenium-Cucumber-Java/automation-framework</pre>

<h3>2. Install dependencies</h3>
<pre>mvn clean install</pre>

<h3>3. Configure properties</h3>
Create or edit src/main/java/properties/config.properties:
<pre>browser=chrome
headless=false</pre>

## Configuration 
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<h3>Browser Configuration<br></h3>
<p>Edit <code>config.properties</code> or use system properties:</p>
<pre># Run with Chrome
mvn test -DbrowserType=chrome<br>
# Run with Firefox
mvn test -DbrowserType=firefox<br>
# Run in headless mode
mvn test -Dheadless=true<br></pre>

<h3>Parallel Execution</h3>
<p>Configure in <code>testng.xml</code>:</p>
<pre><code class="language-xml">&lt;suite name="Suite1" parallel="classes" data-provider-thread-count="3"&gt;
</code></pre>

<h3>Video Recording</h3>
<p>Enable/disable in <code>Hooks.java</code>:</p>
<pre>private static final boolean ENABLE_VIDEO_RECORDING = false;</pre>

## Running Tests
<h3>Run all tests</h3>
<pre>mvn clean test</pre>

<h3>Run specific feature</h3>
<pre>mvn test -Dcucumber.filter.tags="@auth"</pre>

<h3>Run with specific tags</h3>
<pre>
# Authentication tests
mvn test -Dcucumber.filter.tags="@auth"<br>
# Product page tests
mvn test -Dcucumber.filter.tags="@productpage"<br>
# End-to-end tests
mvn test -Dcucumber.filter.tags="@E2E"<br>
# Guest user tests
mvn test -Dcucumber.filter.tags="@guest"<br>
# Authenticated user tests
mvn test -Dcucumber.filter.tags="@authenticated"<br>
# Specific scenario
mvn test -Dcucumber.filter.tags="@user-transaction"</pre>

<h3>Run in specific browser</h3>
<pre>
# Chrome
mvn test -DbrowserType=chrome<br>
# Firefox
mvn test -DbrowserType=firefox<br>
# Headless Chrome
mvn test -DbrowserType=chrome -Dheadless=true</pre>


## Test Scenarios
<div align="right"><a href="#table-of-contents">Back to Top</a></div>
<h3>Authentication Tests (Auth.feature)</h3>
<h3>Positive Scenarios</h3>
<li>Register new account and login</li>
<li>Login with existing credentials</li>
<li>Logout functionality</li>

<h3>Negative Scenarios</h3>
<li>Empty email field</li>
<li>Empty password field</li>
<li>Invalid email format</li>
<li>Invalid password length</li>
<li>Incorrect credentials</li>

<h3>Product Page Tests (ProductPage.feature)</h3>
<h3>Guest User Tests</h3>
<li>Search products by keyword</li>
<li>Search and reset filters</li>
<li>Filter by price range with slider</li>
<li>Sort by name (A-Z, Z-A)</li>
<li>Sort by price (High-Low, Low-High)</li>
<li>Filter by category (Hand Saw, Power Tools, Chisels)</li>
<li>Filter by brand</li>
<li>Filter eco-friendly products</li>
<li>Attempt to add favorites (should fail)</li>

<h3>Authenticated User Tests</h3>
<li>Search products by keyword</li>
<li>Search and reset filters</li>
<li>Filter by price range with slider</li>
<li>Sort by name (A-Z, Z-A)</li>
<li>Sort by price (High-Low, Low-High)</li>
<li>Filter by category (Hand Saw, Power Tools, Chisels)</li>
<li>Filter by brand</li>
<li>Filter eco-friendly products</li>
<li>Add product to favorites</li>
<li>Remove product from favorites</li>

<h3>End-to-End Transaction Tests (EndToEnd.feature)</h3>
<h3>Complete checkout flow with multiple payment methods:</h3>
<li>Bank Transfer</li>
<li>Cash on Delivery</li>
<li>Credit Card</li>
<li>Buy Now Pay Later</li>
<li>Gift Card</li>

<h3>Transaction Flow:</h3>
<li>Add product to cart</li>
<li>Review cart details</li>
<li>Login verification</li>
<li>Billing address confirmation</li>
<li>Payment selection and data entry</li>
<li>Order confirmation</li>
<li>Invoice generation</li>
<li>Invoice verification</li>

## Author
Siti Aisyah<br>
[LinkedIn](https://www.linkedin.com/in/saisyah)
<div align="right"><a href="#table-of-contents">Back to Top</a></div>

