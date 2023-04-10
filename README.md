# School Management
[![codecov](
https://img.shields.io/badge/SchoolManagement-4E69C8?labelColor=4E69C8&amp;logo=Firefox&amp;)](https://school-management.loqo71la.dev)
[![docker](https://img.shields.io/docker/v/loqo71la/school-management)](https://hub.docker.com/r/loqo71la/school-management)

It is a modern and user-friendly user interface that allows educators to interact with the class and student management API. With an intuitive and responsive user interface that provides a smooth and engaging user experience for registering, viewing, and managing classes, students, grades, and more.

# For Production
## Prerequisites
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## How to use
The following steps will run a local instance of school management.
1. Clone this repository.
```
git clone https://github.com/loqo71la/school-management.git
```
2. Navigate into the project root.
```
cd school-management
```
3. Run the `docker-compose` command.
- In case of [school-management-api](https://github.com/loqo71la/school-management-api) is already running.
```
docker-compose ud -d
```
- Otherwise, you must run both services.
```
docker-compose -f docker-compose.api.yml -f docker-compose.yml up -d
```
Finally point your browser to [http://localhost:5173](http://localhost:5173)


# For Development
## Prerequisites
- [node 16+](https://nodejs.org/en/download)

## How to use
Install dependencies
```
npm install
```
Run the client
```
npm run dev
```
Finally point your browser to [http://localhost:5173](http://localhost:5173)