# Veeva Automation Framework

This project demonstrates an end-to-end automation framework for web applications, designed and implemented for Veeva Systems. The framework supports functional UI automation, parallel execution, and reporting using Selenium, TestNG, Maven, and Cucumber.

---

## Prerequisites

Ensure the following tools are installed on your system:

- **Java Development Kit (JDK):** Version 11 or higher
- **Maven:** Version 3.6.0 or higher
- **Web Browsers:** Latest versions of Chrome, Firefox, and Edge
- **WebDriver Binaries:** Managed dynamically using WebDriver Manager
- **Integrated Development Environment (IDE):** IntelliJ IDEA (recommended)

---

## Project Structure

```plaintext
veeva-automation/
├── automation-framework/    # Core framework components (utilities, drivers, reports)
├── core-product-tests/      # Test cases for the Core Product module
├── derived-product1-tests/  # Test cases for Derived Product 1 module
├── derived-product2-tests/  # Test cases for Derived Product 2 module
├── target/                  # Generated test reports and compiled files
└── pom.xml                  # Maven configuration file
```

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/veeva-automation.git
   ```

2. Navigate to the project directory:
   ```bash
   cd veeva-automation
   ```

3. Install project dependencies:
   ```bash
   mvn clean install
   ```

4. Configure test settings in `src/test/resources/config.yaml`:
    - Update the `browser` parameter to specify the browser for execution.
    - Set `baseUrl` to the target application's URL.

---

## Reports

1. **Extent Reports:**
    - Generated under `target/extent-reports`.
    - Open the `$ModuleName-Report.html` file in a browser to view detailed execution results, including screenshots and attached files.

2. **Cucumber HTML Reports:**
    - Generated under `target/cucumber-reports`.
    - Open the `cucumber-html-reports/$ModuleName-Report.html` file in a browser to view detailed results for scenarios and steps.


---

## Key Features

- **Multi-Module Maven Project:** Separate modules for reusable framework components and individual test cases.
- **Page Object Model (POM):** Centralized and maintainable web element locators.
- **Parallel Execution:** Supported via Maven and TestNG.
- **Dynamic WebDriver Management:** Automatically manages browser driver binaries.
- **Reporting:** Integrated with Extent Reports and Cucumber HTML Reports for detailed execution results.
- **Parameterized Configuration:** Test data and configurations managed via YAML files.
- **Cucumber Integration:** Supports BDD-style feature files.

---

## Known Issues and Troubleshooting

1. **WebDriver Timeout Issues:**
    - Ensure the browser drivers match the installed browser versions.

2. **Configuration Errors:**
    - Double-check the `config.yaml` file for accuracy.

3. **Browser-Specific Failures:**
    - Test across multiple browsers to identify compatibility issues.

---

## Contributors

- **Prabhakar Reddy Gollapudi**


