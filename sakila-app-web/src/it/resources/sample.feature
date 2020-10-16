Feature: brief description of what is being tested
  more lines of description if needed.

  Background:
  # this section is optional !
  # steps here are executed before each Scenario in this file
  # variables defined here will be 'global' to all scenarios
  # and will be re-initialized before every scenario

  Scenario: brief description of this scenario
  # steps for this scenario

    # assigning a string value:
    Given def myVar = 'world'

    # using a variable
    Then print myVar

    # assigning a number (you can use '*' instead of Given / When / Then)
    * def myNum = 5
