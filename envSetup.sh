#!/bin/bash

touch .env
 
read -p "Enter your GitHub username: "

echo "GITHUB_USERNAME=$REPLY" > .env

read -p "Enter your GitHub password: "

echo "GITHUB_PASSWORD=$REPLY" >> .env

read -p "Enter your GitHub OAuth token: "

echo "OAUTH_TOKEN=$REPLY" >> .env

read -p "Enter your MongoDB Atlas username: "

echo "MONGO_USERNAME=$REPLY" >> .env

read -p "Enter your MongoDB Atlas password: "

echo "MONGO_PASSWORD=$REPLY" >> .env

read -p "Enter your MongoDB Atlas database name: "

echo "DATABASE_NAME=$REPLY" >> .env

read -p "Enter your MongoDB Atlas test database name: "

echo "DATABASE_TEST=$REPLY" >> .env

read -p "Enter your MongoDB Atlas Collection for repository data: "

echo "COLLECTION_REPOSITORIES=$REPLY" >> .env

read -p "Enter your MongoDB Atlas Collection for user data: "

echo "COLLECTION_USER=$REPLY" >> .env

read -p "Enter your MongoDB Atlas Collection for tests: "

echo "COLLECTION_TEST=$REPLY" >> .env

cp .env visualization/