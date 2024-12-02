# **Quiz-API**

A RESTful API for creating, managing, and taking quizzes. 
The API supports user authentication, quiz categorization, and various administrative functionalities.

---
## **Requirements**
- Java Development Kit (JDK) 17 or above
- MySQL Database [Customize yours](https://github.com/salma-4/Quiz-API/blob/master/src/main/resources/application.yml)
---

## **Features**

- User registration and authentication using JWT.
- CRUD operations for quizzes.
- Quiz categorization and search **TODO**.
- User and admin-specific operations **TODO**.

---

## **Endpoints**

### **1. User Authentication (Auth)**

Handles user registration, login, and other authentication-related actions.

| No. | Feature             | Body                        | Header      | Description                            | Endpoint                                 |
|----:|---------------------|-----------------------------|-------------|----------------------------------------|------------------------------------------|
|  1. | Login               | username(unique) , password |             | generate new token for user            | `POST quizApp/v1/auth/login `            |
|  2. | Register            | Data of user                |             | Add new user create token              | `POST quizApp/v1/auth/newUser `          |
|  3. | Logout              |                             | Token       | Logging out                            | `POST quizApp/v1/auth/logout `           |
|  4. | Forget password     |                             | param/email | Send OTP to email                      | `POST /quizApp/v1/auth/user?email=`      |
|  5. | Reset password      | OTP ,new password           |             | Check OTP validity and update password | `POST quizApp/v1/auth/user/newPassword ` |
|  6. | Regenerate  password |                             | param/email | regenerate OTP and send it             | `POST quizApp/v1/auth/user/otp?email= `  |


### **2.Quiz Management**
Operations related to quizzes.

| No. | Feature        | Body                           | Header | Description                                | Endpoint                             |
|----:|----------------|--------------------------------|--------|--------------------------------------------|--------------------------------------|
|  1. | Create quiz    | Quiz data (category ,question) | Token  | Create new Quiz to specified category      | `POST quizApp/v1/quiz  `             |
|  2. | All categories |                                | Token  | Show all categories of quizzes exist       | `GET quizApp/v1/quiz/allCategories ` |
|  3. | Quiz by cat.   |                                | Token  | Show list of quizzes to specified category | `GET quizApp/v1/quiz/{category}  `   |
|  4. | Delete quiz    |                                | Token  | Delete quiz by its id                      | `GET quizApp/v1/quiz/{quizId}  `     |
|  5. | Get quiz by id |                                | Token  |                                            | `  `                                 |
|  6. | Assign quiz    |                                | Token  |                                            | `  `                                 |
|  7. |                |
|  8. |                |

