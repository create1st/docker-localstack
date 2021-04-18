Feature: Test app

  Scenario: Test app
    Given App is running
    When New message
    Then Message should be persisted