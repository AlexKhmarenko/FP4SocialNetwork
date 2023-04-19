# FP4SocialNetwork
____
## Backend Part:
____
*This project was bootstrapped with Spring Boot App*
## Springboot-app
### Requirements
____
For building and running the application you need:

+ [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
+ [Maven 3.0.4](https://maven.apache.org/)
### Running the application locally
____
There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins.maven) like so:

```mvn spring-boot:run```
### Deploying the application to OpenShift
____
The easiest way to deploy the sample application to OpenShift is to use the [OpenShift CLI](https://docs.okd.io/latest/cli_reference/index.html):

```
oc new-app codecentric/springboot-maven3-centos~https://github.com/AlexKhmarenko/FP4SocialNetwork
```
#### This will create:
+ An ImageStream called "springboot-maven3-centos"
+ An ImageStream called "springboot-sample-app"
+ A BuildConfig called "springboot-sample-app"
+ DeploymentConfig called "springboot-sample-app"
+ Service called "springboot-sample-app"

#### If you want to access the app from outside your OpenShift installation, you have to expose the springboot-sample-app service:
```
oc expose springboot-sample-app --hostname=www.example.com
```