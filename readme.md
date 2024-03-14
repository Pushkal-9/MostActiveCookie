
# Most Active Cookie Finder

## Introduction
The Most Active Cookie Finder is a command line program/application that processes a log file containing cookie information and timestamps. It returns the most active cookie(s) for a specified day. The program defines the most active cookie as the one that appears the most times in the log for a given day.

## Getting Started

### Prerequisites
- Java JDK 11 or higher
- Maven

### Building the Application
1. Clone the repository or extract the provided ZIP file to your local machine.
2. Navigate to the project directory where the `pom.xml` file is located.
   ```sh
   cd path/to/MostActiveCookie
   ```
3. Compile the project and package it using Maven.
   ```sh
   mvn clean package
   ```

### Running the Application
To run the application, use the following command:
```sh
java -jar target/MostActiveCookie-1.0.jar cookie_log.csv -d YYYY-MM-DD
```
Replace `cookie_log.csv` with the path to your cookie log file, and `YYYY-MM-DD` with the date you wish to query.

### Usage Example
```sh
$ java -jar target/MostActiveCookie-1.0.jar cookie_log.csv -d 2018-12-09
```
Output:
```
AtY0laUfhglK3lC7
```
To query for a different date, simply replace `2018-12-09` with the desired date in `YYYY-MM-DD` format.

### Handling Multiple Most Active Cookies
If multiple cookies meet the criteria of being the most active for the specified day, the program will return all of them, each on a separate line. For example:
```sh
$ java -jar target/MostActiveCookie-1.0.jar cookie_log.csv -d 2018-12-08
```
Output:
```
SAZuXPGUrfbcn5UA
4sMM2LxV07bPJzwf
fbcn5UAVanZf6UtG
```

## Running Test Cases
To execute the test cases, run the following command in the project directory:
```sh
mvn test
```

## Assumptions
- The `-d` parameter takes the date in UTC time zone.
- Enough memory is available to store the contents of the whole file.
- Cookies in the log file are sorted by timestamp, with the most recent occurrences at the beginning of the file. (This is an important assumption as the search logic is based on a binary search of these ordered dates.)
- Additional libraries are only used for testing, logging, and CLI parsing.
- The choice to use Maven Build System/Dependency Management Tool was made to use JUnit Testing Framework, without which this can be done as a simple Java Application.
