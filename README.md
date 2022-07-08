# School Management
[![codecov](
https://img.shields.io/badge/SchoolManagement-4E69C8?labelColor=4E69C8&amp;logo=Firefox&amp;)](https://school-management.loqo71la.dev)
![Unit Test](https://github.com/loqo71la/school-management/actions/workflows/coverage-analysis.yml/badge.svg)
[![codecov](https://codecov.io/gh/loqo71la/school-management/branch/main/graph/badge.svg?token=VTP819T047)](https://codecov.io/gh/loqo71la/school-management)

Application to manage a system that assigns students to classes using SpringBoot and ReactJs.

To run the set of tests
```
./gradlew clean test
```

To run the API
```
./gradlew bootRun
```
or
```
./gradlew clean build
java -jar build/libs/school-management-0.0.1-SNAPSHOT.jar
```

# REST API Structure

## Students

### GET `/api/students` status: `200 OK`
_Returns the list of all students_
```
[
    {
        "studentId": 27,
        "firstName": "Pam",
        "lastName": "Bam"        
    },
    {
        "studentId": 32,
        "firstName": "Steve",
        "lastName": "Collin"        
    },
    ..
    ..
]
```

### GET `/api/students/{id}` status: `200 OK`
_Returns single student_
```
{
    "studentId": 27,
    "firstName": "Pam",
    "lastName": "Bam"       
}
```

### GET `/api/students/{id}` status: `404 Not Found`
_Returns an error when the student is not found_
```
{
    "status": "error",
    "message": "Student not found"
}
```

### POST `/api/students` status: `201 Created`
_Returns the success info when the student was created_
```
{
    "status": "success",
}
```

### POST `/api/students` status: `401 Unauthorized`
_Returns an error when the student already exists_
```
{
    "status": "error",
    "message": "Student already exists"
}
```

### PUT `/api/students/{id}` status: `200 OK`
_Returns the success info when the student was updated_
```
{
    "status": "success",
}
```

### PUT `/api/students` status: `404 Not Found`
_Returns an error when the student is not found_
```
{
    "status": "error",
    "message": "Student not found"
}
```

### DELETE `/api/students/{id}` status: `200 OK`
_Returns the success info when the student was removed_
```
{
    "status": "success",
}
```

## Classes

### GET `/api/classes` status: `200 OK`
_Returns the list of all classes_
```
[
    {
        "code": "1A-192",
        "title": "Geology",
        "description": "Sedimentary Petrology"        
    },
    {
        "code": "3C-014",
        "title": "Music",
        "description": "Art of Listening"        
    },
    ..
    ..
]
```

### GET `/api/classes/{code}` status: `200 OK`
_Returns single student_
```
{
    "code": "1A-192",
    "title": "Geology",
    "description": "Sedimentary Petrology"
}
```

### GET `/api/classes/{code}` status: `404 Not Found`
_Returns an error when the class is not found_
```
{
    "status": "error",
    "message": "Class not found"
}
```

### POST `/api/classes` status: `201 Created`
_Returns the success info when the class was created_
```
{
    "status": "success",
}
```

### POST `/api/classes` status: `401 Unauthorized`
_Returns an error when the class already exists_
```
{
    "status": "error",
    "message": "Class already exists"
}
```

### PUT `/api/classes/{code}` status: `200 OK`
_Returns the success info when the class was updated_
```
{
    "status": "success",
}
```

### PUT `/api/classes` status: `404 Not Found`

_Returns an error when the class is not found_
```
{
    "status": "error",
    "message": "Class not found"
}
```

### DELETE `/api/classes/{code}` status: `200 OK`
_Returns the success info when the class was removed_
```
{
    "status": "success",
}
```

## Students by Class

### GET `/api/classes/{code}/students` status: `200 OK`
_Returns the list of all students for specific class_
```
[
    {
        "studentId": 27,
        "firstName": "Pam",
        "lastName": "Bam"        
    },
    {
        "studentId": 32,
        "firstName": "Steve",
        "lastName": "Collin"        
    },
    ..
    ..
]

```
### GET `/api/classes/{code}/students` status: `404 Not Found`
_Returns an error when the class is not found_
```
{
    "status": "error",
    "message": "Class not found"
}
```

### PUT `/api/classes/{code}/students/{id}` status: `200 OK`
_Returns the success info when the student was added to the class_
```
{
    "status": "success",
}
```

### PUT `/api/classes/{code}/students/{id}` status: `404 Not Found`

_Returns an error when the class is not found_
```
{
    "status": "error",
    "message": "Class not found"
}
```

### PUT `/api/classes/{code}/students/{id}` status: `404 Not Found`

_Returns an error when the student is not found_
```
{
    "status": "error",
    "message": "Student not found"
}
```

## Classes by Student

### GET `/api/students/{id}/classes` status: `200 OK`
_Returns the list of all classes for specific student_
```
[
    {
        "code": "1A-192",
        "title": "Geology",
        "description": "Sedimentary Petrology"        
    },
    {
        "code": "3C-014",
        "title": "Music",
        "description": "Art of Listening"        
    },
    ..
    ..
]

```
### GET `/api/students/{id}/classes` status: `404 Not Found`
_Returns an error when the student is not found_
```
{
    "status": "error",
    "message": "Student not found"
}
```

### PUT `/api/students/{id}/classes/{code}` status: `200 OK`
_Returns the success info when the student was added to the class_
```
{
    "status": "success",
}
```

### PUT `/api/students/{id}/classes/{code}` status: `404 Not Found`

_Returns an error when the student is not found_
```
{
    "status": "error",
    "message": "Student not found"
}
```

### PUT `/api/students/{id}/classes/{code}` status: `404 Not Found`

_Returns an error when the class is not found_
```
{
    "status": "error",
    "message": "Class not found"
}
```
