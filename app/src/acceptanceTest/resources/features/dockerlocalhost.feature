Feature: Test app

  Scenario: Process order
    Given Order
    When I request orders list from API
    Then Returned list contains order

  Scenario Outline: Process order
    Given Order with status "<Order Status>"
    When Order status is Accepted
    Then Order is sent to execution
    Examples:
      | Order Status |
      | New          |
      | Postponed    |
