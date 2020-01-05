# Employee API for Freshworks Bootcamp

Endpoints:
* [`/api/employee`](#employee-endpoint)
    * [`/create`](#creating-an-employee)
    * [`/find`](#finding-an-employee)
    * [`/update`](#updating-an-employee)
    * [`/delete`](#deleting-an-employee)
* [`/api/dept`](#department-endpoint)
    * [`/create`](#creating-a-department)
    * [`/find`](#finding-a-department)
    * [`/update`](#updating-a-department)
    * [`/delete`](#deleting-a-department)
    * [`/getEmployees`](#getting-all-employees-in-a-department)
    
## Employee Endpoint
<p id="employee-endpoint"></p>

Endpoint: `/api/employee`

All endpoints for employee related operations start with the with pattern.

### Creating an employee
Endpoint: `/api/employee/create`<br>
Method: `POST`<br>
Data:
```json
{
  "empName": "<empName>",
  "deptId": "<deptId>"
}
```
OR
```json
{
  "empName": "<empName>",
  "deptName": "<deptName>"
}
```

### Finding an Employee
Endpoint: `/api/employee/find?empId=<empId>`<br>
Method: `GET`

### Updating an Employee
Endpoint: `/api/employee/update`<br>
Method: `POST`<br>
Data:
```json
{
  "empId": "<empId>",
  "deptId": "<deptId>"
}
```
OR
```json
{
  "empId": "<empId>",
  "deptName": "<deptName>"
}
```

### Deleting an Employee
Endpoint: `/api/employee/detele/{empId}`<br>
Method: `DELETE`

## Department Endpoint
Endpoint: `/api/dept`<br>
All endpoints to perform operations on departments start with this pattern.

### Creating a Department
Endpoint: `/api/dept/create`<br>
Method: `POST`<br>
Data:
```json
{
  "deptName": "<deptName>"
}
```

### Finding a Department
Endpoint: `/api/dept/find?deptId=<deptId>`<br>
Method: `GET`

### Updating a Department
Endpoint: `/api/dept/update`<br>
Method: `POST`<br>
Data:
```json
{
  "deptId": "<deptId>",
  "deptName": "<deptName>"
}
```

### Deleting a Department
Endpoint: `/api/dept/detele/{deptId}`<br>
Method: `DELETE`

### Getting all employees in a Department
Endpoint: `/api/dept/getEmployees?deptId=<deptId>`<br>
Method: `GET`