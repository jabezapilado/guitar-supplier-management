#!/bin/bash

set -euo pipefail

# Run the Guitar Supplier Management application with MySQL connector in classpath
java -cp "bin:lib/mysql-connector-j-9.2.0.jar" com.guitar.main.GuitarSupplierManagement
