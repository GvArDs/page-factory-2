#language:en
@template
Feature: Test template placeholder replacing

  Scenario: Test template placeholder replacing
    * user sends request for "test replacer" with parameters
      | day15 | ""     |
      | day1  | null   |
      | day11 | "null" |
    * system returns "works correctly"
