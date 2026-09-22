# USE CASE: 3 Produce a Report on the Salary of Employees in My Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Department Manager* I want *to produce a report on the salary of employees in my department* so that *I can support financial reporting for my department.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The department manager is identified. Database contains current employee salary data.

### Success End Condition

A report containing the current salaries of employees in the manager's department is available.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager.

### Trigger

The department manager needs salary information for their department.

## MAIN SUCCESS SCENARIO

1. Department manager requests salary information for their department.
2. Department manager accesses the HR system.
3. HR system identifies the manager's department.
4. HR system retrieves the current salary information of all employees in the department.
5. HR system provides the salary report to the department manager.

## EXTENSIONS

3. **Department cannot be identified**:

    1. HR system informs the department manager that their department cannot be identified.

4. **No employees found in the department**:

    1. HR system informs the department manager that no employees were found.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
