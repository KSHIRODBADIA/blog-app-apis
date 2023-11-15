# Blog-app-apis (made with Spring Boot, MySQL, Spring Security, JWT, JPA, Rest API)

Build Restful CRUD API for a blog using Spring Boot, Mysql, JPA and Hibernate.

## About the project
Application made using:\
 <i><b>Java 17,\
  Spring Boot v3.1.3,\
   spring-boot-starter-web,\
    spring-boot-starter-data-jpa,\
     spring-boot-starter-validation,\
      spring-boot-devtools,\
       mysql-connector-j,\
        Spring Security,\
         modelmapper,\
          Lombok,\
           springdoc-openapi-starter-webmvc-ui\
            Maven</b></i>.
 
Unregistered/anonymous blog users can view all posts and comments;\
Registered and logged in users (Authenticated users) can add new posts, view only their own posts, edit or delete them (CRUD functionality);\
Users can write comments to particular posts by own or other users;\
Validation for creating new posts, body must not be empty, title must have length of 7 by default and other;\
Spring Security authentication and authorization rules ensures that users only able to edit or delete their own posts;\
All APIs are documentd using Swagger UI.

## How to set up the application

**1. Open terminal and use git clone command to download the remote GitHub repository to your computer:
```
git clone https://github.com/KSHIRODBADIA/blog-app-apis.git
```
It will create a new folder with same name as GitHub repository "blog-app-apis". All the project files and git data will be cloned into it. After cloning complete change directories into that new folder:
```
cd blog-app-apis
```
**2. Create Mysql database**
```
create database blog_app_apis
```
**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Swagger API Docs with UI ( http://localhost:8080/swagger-ui.html)

The app defines following CRUD APIs.
### Auth

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /auth/register | Sign up | [JSON](#register) |
| POST   | /auth/login | Log in | [JSON](#login) |

### Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/users/{userId} | Get logged in user profile | |
| POST   | /api/users | create new user | [JSON](#usercreate) |
| PUT    | /api/users/{userId} | Update user details | [JSON](#userupdate) |
| DELETE | /api/users/{userId} | Delete user (For logged in user or admin) | |
| GET    | /api/users/ | Return all user details | |

### Posts

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/posts | Get all posts | |
| GET    | /api/posts/{id} | Get post by id | |
| POST   | /api/posts | Create new post (By logged in user) | [JSON](#postcreate) |
| PUT    | /api/posts/{id} | Update post (If post belongs to logged in user or logged in user is admin) | [JSON](#postupdate) |
| DELETE | /api/posts/{id} | Delete post (If post belongs to logged in user or logged in user is admin) | |

### Comments

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/posts/{postId}/comments | Get all comments which belongs to post with id = postId | |
| GET    | /api/posts/{postId}/comments/{id} | Get comment by id if it belongs to post with id = postId | |
| POST   | /api/posts/{postId}/comments | Create new comment for post with id = postId (By logged in user) | [JSON](#commentcreate) |
| PUT    | /api/posts/{postId}/comments/{id} | Update comment by id if it belongs to post with id = postId (If comment belongs to logged in user or logged in user is admin) | [JSON](#commentupdate) |
| DELETE | /api/posts/{postId}/comments/{id} | Delete comment by id if it belongs to post with id = postId (If comment belongs to logged in user or logged in user is admin) | |

### Category

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/categories | Get all categories | |
| GET    | /api/categories/{id} | Get category by id | |
| POST   | /api/categories | Create new categories (By logged in user) | [JSON](#albumcreate) |
| PUT    | /api/categories/{id} | Update album (If album belongs to logged in user or logged in user is admin) | [JSON](#albumupdate) |
| DELETE | /api/categories/{id} | Delete album (If album belongs to logged in user or logged in user is admin) | |

