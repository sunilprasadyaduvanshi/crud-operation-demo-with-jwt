# crud-operation-demo with JWT

```
Step-1
First Add user in Data Base
POST : http://localhost:8080/api/employees/new
{
    "name":"sunil",
    "email":"sunilyadav@gmail.com",
    "password":"12345",
    "roles":"ROLE_ADMIN"
}

Step-2
Generate the tokens
POST : http://localhost:8080/api/employees/authenticate
{
    "username": "sunil",
    "password": "12345"
}

After that you can access the data based on roles

```