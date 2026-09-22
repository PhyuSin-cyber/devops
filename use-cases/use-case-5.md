# USE CASE: 5 Add New Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to add a new employee's details* so that *the new employee can be paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The new employee's details are available. The HR advisor is authorised to add employee information.

### Success End Condition

The new employee's details are added to the database.

### Failed End Condition

The new employee's details are not added.

### Primary Actor

HR Advisor.

### Trigger

A new employee needs to be added to the company's employee records.

## MAIN SUCCESS SCENARIO

1. HR advisor enters the new employee's details.
2. HR system validates the employee information.
3. HR system adds the new employee's details to the database.
4. HR system confirms that the employee has been added successfully.

## EXTENSIONS

2. **Employee details are invalid or incomplete**:

    1. HR system informs the HR advisor that the details are invalid or incomplete.
    2. HR advisor corrects the details.

3. **Employee already exists**:

    1. HR system informs the HR advisor that the employee already exists.
    2. HR advisor checks the existing employee record.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
