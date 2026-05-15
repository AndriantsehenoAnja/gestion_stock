#!/bin/bash

# =========================
# CREATION DOSSIER BUILD
# =========================

mkdir -p build

# =========================
# COMPILATION
# =========================

javac -cp "lib/postgresql-42.7.8.jar" \
-d build \
Main.java \
connection/*.java \
dao/*.java \
model/*.java \
ui/*.java

# =========================
# EXECUTION
# =========================

java -cp "build:lib/postgresql-42.7.8.jar" Main