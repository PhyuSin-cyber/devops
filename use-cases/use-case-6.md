# USE CASE: 6 View an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to view an employee's details* so that *I can support a promotion request.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The employee ID is known.

### Success End Condition

The employee's current details are displayed to the HR advisor.

### Failed End Condition

The employee's details cannot be displayed.

### Primary Actor

HR Advisor.

### Trigger

The HR advisor needs to view an employee's details to support a promotion request.

## MAIN SUCCESS SCENARIO

1. HR advisor enters the employee ID.
2. HR system searches for the employee in the database.
3. HR system retrieves the employee's current details.
4. HR system displays the employee's details to the HR advisor.
5. HR advisor uses the details to support the promotion request.

## EXTENSIONS

2. **Employee does not exist**:

    1. HR system informs the HR advisor that the employee cannot be found.
    2. HR advisor checks the employee ID.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
