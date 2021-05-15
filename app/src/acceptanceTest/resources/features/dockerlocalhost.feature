Feature: Test app

  Scenario Outline: Process order
    Given Order with status "<Order Status>"
    When Order status is Accepted
    Then Order is sent to execution
    Examples:
      | Order Status |
      | New          |
      | Postponed    |