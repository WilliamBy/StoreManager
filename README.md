# GoodsManager
[![release](https://img.shields.io/github/v/release/WilliamBy/StoreManager)](https://github.com/WilliamBy/StoreManager/releases)
[![license](https://img.shields.io/github/license/WilliamBy/StoreManager)](./LICENSE.md)

![commit](https://img.shields.io/github/last-commit/Williamby/StoreManager)
---
## Introduction

This is a simple web application for managing goods in your own store. 

**JRE 8+** as well as **MySQL Server v8.0.30** is needed while running.

## Features

- [x] User Login & Register
- [x] Keyword Search
- [x] Goods CRUD Operations
- [ ] User Operations History Search

## Technologies

### Front-End

| Technology | Introduction        | Link                                                         |
| ---------- | ------------------- | ------------------------------------------------------------ |
| Layui      | Front-end framework | [layuiweb.com](https://www.layuiweb.com/) |

### Back-End

| Technology | Introduction       | Link    |
| ---------- | ------------------ | ------- |
| Java | Programming language | [java.com](https://www.java.com/) |
| Spring     | Back-end framework | [spring.io](https://spring.io/) |
| Spring Boot | Boot the development of your Spring project | [Spring Boot](https://spring.io/projects/spring-boot) |
| MyBatis | Persistence framework | [mybatis](https://mybatis.org/mybatis-3/), [mybatis/spring-boot-starter](https://github.com/mybatis/spring-boot-starter) |
| MySQL | Open source database | [MySQL](https://www.mysql.com/) |
| JWT | Authorization Token Lib | [JSON Web Tokens - jwt.io](https://jwt.io/) |

## Development

- Project Instructure

``` shell
# back-end code in 'GoodsManager/src/main/java'
java
  └─com
      └─example
          └─goodsmanager
              ├─auth	# authorization utility
              ├─config	# configurations
              ├─controller # web controller
              ├─domain	# domain classes
              └─mapper	# mybatis mapper
              
# front-end code in 'GoodsManager/src/main/resources/static'
static
├── index.html
├── js
│   ├── add.js
│   ├── edit.js
│   ├── login.js
│   ├── manage.js
│   ├── register.js
│   └── util.js
├── layui
└── pages
    ├── add.html
    ├── edit.html
    ├── login.html
    ├── manage.html
    └── register.html
```

- [RESTful API doc](./Others/prototype/接口.md)
- Fork & Clone this repository to your local machine

```shell
git clone <url>
```

- Get into directory `GoodsManager`

```shell
cd ./StoreManager/GoodsManager
```

- Execute maven goals to generate *.jar

```shell
mvn clean package
```

## Deployment & Usage

- Download jar file from [![release](https://img.shields.io/github/v/release/WilliamBy/StoreManager)](https://github.com/WilliamBy/StoreManager/releases)

- Make sure `JRE 8+` and `MySQL Server v8.0.30` are installed (any other version was not tested)
- Set environment variable `JAVA_HOME` properly and check it by:
```shell
java --version
```
- Start MySQL Server
- Create Database using `CREATE DATABASE IF NOT EXISTS goods_db;`
- Run commands like follow (filling your own config on using "-D" flag):

```java
java -DMYSQL_USERNAME=xxx -DMYSQL_PASSWORD=xxx -DSECRET=xxx -DMYSQL_HOST=xxx -jar GoodsManager-<version>.jar
```

- Config Parameter Table

| Parameter            | Explain                             | Default Value |
| -------------------- | ----------------------------------- | ------------- |
| `MYSQL_USERNAME`     | **MySQL Server username**           | `root`        |
| `MYSQL_PASSWORD**`** | **MySQL Server password**           | /             |
| `MYSQL_HOST`         | MySQL Service Host                  | `localhost`   |
| `MYSQL_PORT`         | MySQL Service Port                  | `3360`        |
| `WEB_PORT`           | Web Service Port                    | `80`          |
| `SECRET`             | Secret key for generating jwt token | `secret`      |

- Just visit `http://<domain>:<port>`
- If you want the application to run after leaving window, refer to tools `tmux` or `screen`
