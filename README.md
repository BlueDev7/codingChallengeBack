# Coding Challenge API  

## Description

A springboot API for products

## Prerequisites

Before running the Docker container, ensure you have the following installed:

- [Docker](https://docs.docker.com/get-docker/) (for running the container)
- [PostgreSQL](https://www.postgresql.org/download/) (for database setup)

## PostgreSQL Setup

1. **Install PostgreSQL**

   Follow the [official installation guide](https://www.postgresql.org/download/) to install PostgreSQL on your local machine.

2. **Create the Database**

   Once PostgreSQL is installed, create a database for your application called "products" with following user: root and password: root.

3. **Build and Run the Docker Container**
    
    To build your the application image use the command :
    docker build -t your-app-name . 

    once you created the app image , you can run it using
    docker run -d --name your-container-name -p 8080:8080 -e DB_URL=jdbc:postgresql://host.docker.internal:5432/products your-image-name

