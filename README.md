# Rest Assured API Automation + Jenkins

A Git-ready API automation project for an SDET to practice:

- Rest Assured
- Java
- TestNG
- Maven
- POJO serialization/deserialization
- Request/Response specifications
- API client layer
- Path parameters
- Query parameters
- GET / POST / PUT / DELETE
- Positive and negative tests
- TestNG groups
- Environment configuration
- Jenkins Pipeline
- JUnit-style test result publishing

## 1. Architecture

```text
Test
  |
  v
PostsClient
  |
  v
Rest Assured
  |
  +---- RequestSpecification
  |
  +---- ConfigManager
  |
  v
JSONPlaceholder API
```

Jenkins flow:

```text
GitHub
   |
   v
Jenkins Pipeline
   |
   +--> Checkout
   |
   +--> Maven clean test
   |
   +--> TestNG / Rest Assured
   |
   +--> target/surefire-reports
   |
   +--> Jenkins Test Results
```

## 2. Project structure

```text
rest-assured-jenkins-framework
|
|-- Jenkinsfile
|-- Jenkinsfile.windows
|-- pom.xml
|-- testng.xml
|-- .gitignore
|
`-- src
    `-- test
        |-- java
        |   `-- com
        |       `-- sdet
        |           |-- base
        |           |   `-- BaseTest.java
        |           |-- client
        |           |   `-- PostsClient.java
        |           |-- config
        |           |   `-- ConfigManager.java
        |           |-- models
        |           |   |-- PostRequest.java
        |           |   `-- PostResponse.java
        |           |-- specs
        |           |   |-- RequestSpecFactory.java
        |           |   `-- ResponseSpecFactory.java
        |           `-- tests
        |               |-- GetPostTest.java
        |               |-- CreatePostTest.java
        |               |-- UpdateDeletePostTest.java
        |               `-- NegativeTest.java
        |
        `-- resources
            `-- config
                |-- qa.properties
                `-- dev.properties
```

## 3. Prerequisites

Install:

- Git
- Java 17+
- Maven
- Jenkins

Check:

```bash
java -version
mvn -version
git --version
```

## 4. Run locally

All tests:

```bash
mvn clean test
```

QA:

```bash
mvn clean test -Denv=qa
```

Smoke:

```bash
mvn clean test -Denv=qa -Dgroups=smoke
```

Regression:

```bash
mvn clean test -Denv=qa -Dgroups=regression
```

Override base URL from command line:

```bash
mvn clean test -Dbase.url=https://jsonplaceholder.typicode.com
```

## 5. What each layer does

### BaseTest
One-time suite setup. Installs the shared Rest Assured request specification.

### ConfigManager
Reads the environment chosen with `-Denv`.

Example:

```text
-Denv=qa
        |
        v
src/test/resources/config/qa.properties
        |
        v
base.url
```

### RequestSpecFactory
Stores common request configuration:

- Base URI
- Content-Type
- Accept
- Request logging

This avoids repeating the same setup in every test.

### ResponseSpecFactory
Stores reusable expected response rules:

- expected HTTP status
- response time

### PostsClient
API/service layer. Tests do not need to repeat raw Rest Assured calls.

Example:

```java
postsClient.getPost(1);
postsClient.createPost(payload);
```

### Models
Java POJOs for JSON request/response bodies.

### Tests
Contain test scenarios and assertions.

## 6. GitHub

Create an empty GitHub repository, then from the project directory:

```bash
git init
git add .
git commit -m "Initial Rest Assured API automation framework"
git branch -M main
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

## 7. Jenkins from scratch

### Step A - Install Jenkins

Install Jenkins and open:

```text
http://localhost:8080
```

Install suggested plugins.

Make sure these are available:

- Pipeline
- Git
- JUnit
- Maven Integration (optional but useful)

### Step B - Configure Java

Go to:

```text
Manage Jenkins
  -> Tools
  -> JDK installations
```

Add a JDK named exactly:

```text
JDK17
```

If Java is already installed on the Jenkins machine, configure its JAVA_HOME.

### Step C - Configure Maven

Go to:

```text
Manage Jenkins
  -> Tools
  -> Maven installations
```

Create:

```text
Name: Maven3
```

The name must match the `tools` block in the Jenkinsfile.

### Step D - Create Pipeline job

```text
Dashboard
  -> New Item
  -> Pipeline
```

Give it a name, for example:

```text
rest-assured-api-automation
```

Choose:

```text
Pipeline script from SCM
```

SCM:

```text
Git
```

Repository URL:

```text
YOUR_GITHUB_REPOSITORY_URL
```

Branch:

```text
*/main
```

Script Path:

```text
Jenkinsfile
```

Save.

### Step E - Run

Click:

```text
Build with Parameters
```

Choose:

```text
ENV = qa
TEST_GROUP = smoke
```

Then Build.

## 8. Understand the Jenkinsfile

```text
pipeline
 |
 +-- agent any
 |
 +-- tools
 |    +-- JDK17
 |    `-- Maven3
 |
 +-- parameters
 |    +-- ENV
 |    `-- TEST_GROUP
 |
 +-- stages
 |    +-- Checkout
 |    +-- Verify Tools
 |    `-- Run API Tests
 |
 `-- post
      +-- publish test XML
      `-- archive reports
```

The most important command is:

```bash
mvn clean test
```

Jenkins is executing the same Maven command you can execute locally.

Think of Jenkins as:

```text
Jenkins = machine/orchestrator
Maven   = build/test command runner
TestNG  = test runner
Rest Assured = API automation library
GitHub  = source code storage
```

## 9. Windows Jenkins Agent

The main `Jenkinsfile` uses Linux-style `sh`.

If your Jenkins executor is Windows, use `Jenkinsfile.windows`, which uses:

```groovy
bat 'mvn -version'
bat 'mvn clean test'
```

You can either rename `Jenkinsfile.windows` to `Jenkinsfile`, or set Jenkins
"Script Path" to:

```text
Jenkinsfile.windows
```

## 10. Jenkins result location

Maven Surefire generates reports under:

```text
target/surefire-reports/
```

The Jenkins Pipeline publishes JUnit-compatible XML from:

```text
target/surefire-reports/junitreports/*.xml
```

and archives the complete Surefire report directory.

## 11. Interview explanation

A concise answer:

> I built a layered Rest Assured automation framework using Java, TestNG and Maven.
> Environment configuration is externalized through properties files and system
> properties. Common Rest Assured request and response specifications are reusable.
> API operations are separated into a client layer, request/response payloads use
> POJOs, and test classes focus on assertions and scenarios. The framework is stored
> in Git and Jenkins uses a declarative Jenkinsfile to checkout the repository,
> execute Maven tests, publish TestNG/Surefire test results and archive reports.

## 12. Next improvements

After understanding this version, add these one by one:

1. Bearer-token authentication
2. API chaining
3. Schema validation
4. DataProvider/data-driven testing
5. Retry analyzer
6. Listeners
7. Allure or Extent reporting
8. Jenkins credentials
9. GitHub webhook
10. Dockerized Jenkins agent
11. Parallel TestNG execution
12. CI quality gates
