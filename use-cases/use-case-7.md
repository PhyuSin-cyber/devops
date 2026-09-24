# USE CASE: 7 Update an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to update an employee's details* so that *I can keep the details up-to-date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The HR advisor is authorised to update employee information.

### Success End Condition

The employee's details are updated successfully in the database.

### Failed End Condition

The employee's details are not updated.

### Primary Actor

HR Advisor.

### Trigger

The HR advisor needs to update an employee's details.

## MAIN SUCCESS SCENARIO

1. HR advisor enters the employee ID.
2. HR system retrieves the employee's current details.
3. HR advisor updates the required details.
4. HR system validates the updated information.
5. HR system saves the updated details to the database.
6. HR system confirms that the employee's details have been updated successfully.

## EXTENSIONS

2. **Employee does not exist**:

    1. HR system informs the HR advisor that the employee cannot be found.

3. **Updated information is invalid**:

    1. HR system informs the HR advisor that the information is invalid.
    2. HR advisor corrects the information.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
