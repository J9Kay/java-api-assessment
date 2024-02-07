# StockAPI

The StockAPI provides real-time stock market data. It enables developers to get stock quotes, historical prices,
dividends, and other important investment details.

## Table of Contents

1. [Development Setup](#development-setup)
    - [Prerequisites](#prerequisites)
    - [GitHub Instructions](#github-instructions)
        - [Cloning the Repository](#cloning-the-repository)
        - [Contributions](#contributions)
    - [Local Development](#local-development)
        - [Code Style](#code-style)
        - [Building and Running the API](#building-and-running-the-api)
        - [Running tests](#running-tests)
2. [Deployment](#deployment)
3. [Versioning](#versioning)
4. [Authors](#authors)
5. [License](#license)

## Development Setup

### Prerequisites

Ensure that you have the following tools installed in your development setup:

- Java: `JDK 17`
- Docker (for running the database locally): `version XX.XX`

### GitHub Instructions

#### Cloning the Repository

Clone this repository to your local machine to begin contributing:
bash git clone https://github.com/YOUR-USERNAME/YOUR-REPOSITORY
Replace 'YOUR-USERNAME' and 'YOUR-REPOSITORY' with the appropriate details.

#### Contributions

We welcome contributions from community members. Please see the `CONTRIBUTING.md` document for our code of conduct and
the process for submitting pull requests.

### Local Development

We use IntelliJ IDEA as our preferred IDE for this project. Here's how to get the API running locally.

#### Code Style

We follow the Google Java Style guide in this project. Please install the style guide settings in your IDE.

#### Building and Running the API

The API can locally be built and run using Maven's wrapper commands:
bash ./mvnw clean install
This will take care of downloading dependencies, compiling source code and running unit tests.

#### Running Tests

We use JUnit tests for unit testing. Run them with the following command:
bash ./mvnw test

## Deployment

Detailed instructions on the deployment process of the StockAPI are provided here.

## Versioning

We use Semantic Versioning (SemVer) for versioning. See [SemVer](http://semver.org/) for more details.

## Authors

This API was developed by Janine, and she is always looking to recognize contributions from
people just like you.

## License

The StockAPI is released under XYZ License. This allows others to use and reference our code in their work while
acknowledging its original source.