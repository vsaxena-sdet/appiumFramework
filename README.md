Welcome to the Appium Automation Framework repository! This framework is built on Java and utilizes TestNG and Maven for
test execution and management. The framework follows the Page Factory design model to enhance the maintainability and
scalability of your automated tests.

### **Features**

**Java-Based**: Built using the Java programming language.\

**TestNG Integration**: Utilizes TestNG for efficient test management and parallel execution.\

**Maven Project**: Managed by Apache Maven for easy dependency management and build processes.\

**Page Factory Design**: Follows the Page Factory design pattern for creating and maintaining page objects.\

**Appium Integration**: Seamless integration with Appium for mobile app automation.\

**Configurable**: Easily configurable with external configuration files for flexibility.\

**Log4j**: Incorporates logging for better traceability and debugging.

**Android SDK** : Auto Launch of AVD (Android Virtual Device) on running the test suite

### **Getting Started**

Prerequisites
Before you begin, ensure you have the following installed:

Java Development Kit (JDK) 11\
Maven\
Android SDK- Should be installed with path set in ANDROID_HOME env variable in bash\
Update the appium.properties before running the tests with the deviceName (physical/emulator).

### **Installation**
``git clone https://github.com/your-username/appium-automation-framework.git
``\
``cd appium-automation-framework
``\
``mvn clean install
``
