# AutoRide

AutoRide is an autonomous rides ordering system system built with Java, JavaFX, and PostgreSQL.

This is the Desktop version (JavaFX). For the modern Web version (Spring Boot + React), check out: [https://github.com/DanielOhana8/AutoRide-web.git]"

## Features
- User registration and login
- Find nearest available car
- Start and end rides
- Balance management

## Technologies
- Java 17
- JavaFX (FXML)
- PostgreSQL
- JDBC

## Setup

**Database Setup:**
  - Create PostgreSQL database
  - Run `database/schema.sql`

**Configuration:**
  - Copy `src/config.properties.example` to `src/config.properties`
  - Update with your database details

**Run:**
  - Add PostgreSQL JDBC driver to build path
  - Run `AutoRideApp.java`

## Author
Daniel Ohana
