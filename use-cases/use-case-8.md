# USE CASE: 8 Delete an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete an employee's details* so that *I can comply with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee exists in the database. The HR advisor is authorised to delete employee information.

### Success End Condition

The employee's details are deleted from the database in accordance with data retention requirements.

### Failed End Condition

The employee's details are not deleted.

### Primary Actor

HR Advisor.

### Trigger

The employee's data has reached the required retention period and must be deleted.

## MAIN SUCCESS SCENARIO

1. HR advisor enters the employee ID.
2. HR system retrieves the employee's details.
3. HR advisor confirms that the employee's details should be deleted.
4. HR system deletes the employee's details from the database.
5. HR system confirms that the employee's details have been deleted successfully.

## EXTENSIONS

2. **Employee does not exist**:

    1. HR system informs the HR advisor that the employee cannot be found.

3. **Deletion is not authorised**:

    1. HR system informs the HR advisor that the employee's details cannot be deleted.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
