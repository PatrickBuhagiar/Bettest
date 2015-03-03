Feature: Bettest Functionality

Scenario: Successful Registration
Given I am a user trying to register
When I register providing correct information
Then I should be told that the registration was successful

Scenario Outline: Change field names

Given I am a user trying to register
When I fill in a form with correct data and I change the "<fieldname>" field to have incorrect input
Then  I  should  be  told "<message>"  since  the  data  in  "<fieldname>"  is incorrect

Examples:

|fieldname     	|message	|
|name    		|Invalid characters|
|surname      	|Invalid characters|
|dob			|Invalid date|
|card-number    |Invalid card number|
|exp-date        |Invalid date|


Scenario: Successful bet on free account
Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
 
Scenario: Verify maximum bets for free accounts
Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told that I have reached the maximum number of bets
 
Scenario: Verify maximum cumulative bet for premium accounts
Given I am a user with a premium account
When I try to place a bet of 5000 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 1 euros
Then I should be told that I have reached the maximum cumulative betting amount
 
Scenario: Verify access restriction for guest users
Given I am a user who has not yet logged on
When I try to access the betting screen
Then I should be refused access
 
Scenario Outline: Verify that free users can only low risk
Given I am a user with a free account
When I try to place a "<risklevel>" bet of 5 euros
Then I should be "<expectedresult>" to bet
 
Examples:
 
|risklevel      |expectedresult |
|low    |msgsuccess     |
|medium |msgfail        |
|high   |msgfail        |