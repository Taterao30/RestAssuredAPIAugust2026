# Jenkins Learning Path for this project

## Phase 1 - Do not start Jenkins yet

First understand this flow locally:

```text
pom.xml
  -> testng.xml
  -> BaseTest
  -> ConfigManager
  -> RequestSpecFactory
  -> PostsClient
  -> Test class
  -> API
  -> assertion
```

Run:

```bash
mvn clean test
```

If this command works, your automation framework works.

## Phase 2 - Learn only five Jenkins concepts

1. Jenkins Controller/Agent
2. Job
3. Workspace
4. Pipeline
5. Stage

Do not try to learn every Jenkins plugin first.

## Phase 3 - First Jenkins job

Create a Pipeline from SCM and execute only:

```bash
mvn clean test
```

Once it passes, open:

```text
Build
 -> Console Output
```

Read the logs.

## Phase 4 - Understand CI

Developer/SDET pushes code:

```text
Local
  |
 git push
  v
GitHub
  |
  v
Jenkins
  |
 checkout
  v
Maven
  |
  v
TestNG
  |
  v
Rest Assured
  |
  v
API
```

## Phase 5 - Add parameters

This framework already provides:

```text
ENV
TEST_GROUP
```

So Jenkins can execute:

```bash
mvn clean test -Denv=qa -Dgroups=smoke
```

without changing source code.

## Phase 6 - Learn reports

Surefire creates XML.

Jenkins reads the XML through the `junit` Pipeline step.

That is how Jenkins knows:

```text
Tests: 5
Passed: ...
Failed: ...
Skipped: ...
```

## Phase 7 - Add webhook later

Manual:

```text
git push
then click Build
```

CI:

```text
git push
   |
 webhook
   v
Jenkins starts automatically
```

Learn webhook only after manual Jenkins execution works.

## Phase 8 - Interview topics after this project

Be able to explain:

- Why Jenkins?
- What is CI?
- Freestyle vs Pipeline
- Declarative vs Scripted Pipeline
- What is Jenkinsfile?
- What is an agent?
- What is a stage?
- How Jenkins checks out Git code
- How Maven is called
- How TestNG reports reach Jenkins
- How environment parameters are passed
- How secrets should be stored in Jenkins Credentials, not Git
- How to trigger builds from GitHub
- How to schedule builds using cron
- How to run tests in parallel
- How to archive reports/artifacts
- What to do when a Jenkins build fails
