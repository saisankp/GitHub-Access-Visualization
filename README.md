# GitHub-Access
<p align=justify>Welcome to my Github API Access & Software Engineering Metric Visualisation for the module Software Engineering (CSU33012) at Trinity College Dublin. The goal of this project is to interrogate the GitHub API, and use the data from that interrogation to visualize an aspect of the software engineering process.</p>

### A brief description of the technologies and approaches used in my project.
<p align=justify>This project uses Java with Maven to interrogate the GitHub API using the <a href="https://mvnrepository.com/artifact/org.eclipse.mylyn.github/org.eclipse.egit.github.core/2.1.5">The Eclipse EGit GitHub API Core 2.1.5 API</a>. The data from the interrogation is processed and stored into a cloud database using <a href="https://www.mongodb.com/atlas/database">MongoDB Atlas</a>. This data in the cloud database is retrieved using Node.js. The data is parsed as JSON and a server on port 8081 has endpoints such as <b><i>/userdata</i></b> for user data and  <b><i>/repo</b></i> for information about all the repositories of the chosen user. For visualization, this project uses Vue.js to retrieve the data from these endpoints by making a series of API calls to the server, and visualizes it on port 8080. </p>

### Prerequisites to run this project.
<p align=justify>You need a few things before you can run this project.</p>

#### Java
<p align=justify>You can install Java on <a href="https://www.oracle.com/java/technologies/downloads/">Oracle's Java Downloads page</a>. There are also YouTube videos to help you download Java for <a href="https://www.youtube.com/watch?v=9s8saAeKEfg&ab_channel=ProgrammingKnowledge2">Windows</a>, <a href="https://www.youtube.com/watch?v=pxi3iIy4F5A&ab_channel=ProgrammingKnowledge">MacOS</a>, and <a href="https://www.youtube.com/watch?v=ogWKP9Lm-Qo&ab_channel=ProgrammingKnowledge">Linux</a>. Sometimes this may require setting the JAVA_HOME environment variable.</p>

#### Maven

<p align=justify>You can download Maven on the <a href="https://maven.apache.org/download.cgi">Apache Maven Downloads page</a>. There are also instructions on how to install Maven for Windows, MacOS, and Linux on the  <a href="https://maven.apache.org/install.html">Apache Maven Installation page</a>.</p>

#### MongoDB Atlas
<p align=justify>You don't need to necessarily <i>download</i> anything for this, but you do need to setup an account, and make a few databases and collections to store data with. Here are the steps you need to follow:</p>
  1. Log-in or Sign-up for your MongoDB account on the <a href="https://account.mongodb.com/account/login">MongoDB Atlas Login page</a>. <br>
  2. Choose the FREE deployment option (Shared Cluster) when prompted.  <br>
  3. When asked to choose a Cloud Provider, Region, Cluster Tier etc, choose anything here as it does not matter for this project. <br>
  4. Name your Cluster anything you want, and press "Create Cluster". <br>
  5. You will be directed to the Security Quickstart page. Here, you can create a database user using a username and password. <br>
  6. Under "Where would you like to connect from?", click "Cloud Environment". <br>
  7. Click the "Add my Current IP Address" button so we will be able to connect to our project's clusters. <br>
  8. Now click on your Cluster, and go to your collections and click on "Add My Own Data". <br>
  9. Create a database with two collections. This will be used for storing our GitHub user and repository information respectively. <br>
  10. Create another database, with one collection. This will be used for testing. <br><br>
  
 
  <p align="center">
     An example may look like this:<br><br>
  <img width="285" alt="Screenshot 2021-12-23 at 03 32 00" src="https://user-images.githubusercontent.com/34750736/147185973-8015217f-1f13-419d-8b51-1d7facb26d31.png"></p>

  
#### Docker
<p align=justify>You can install Docker from the <a href="https://docs.docker.com/get-docker/">Docker Docs Get Docker page</a>.</p>
  
### Setup .env files (only for running the project for the first time).
 
<p align=justify>Since we interrogate the GitHub API and store data in our MongoDB Atlas collections within our databases, we need the project to know information like your GitHub username, password, OAuth token, MongoDB Atlas database username, password, database names, collection names etc. For security reasons, I store this data in a .env file - there is one in the root directory as well as an indentical one in the visualization directory. The format of the files looks like this: <br>
 
```
GITHUB_USERNAME=YourGitHubUsername
GITHUB_PASSWORD=YourGitHubPassword
OAUTH_TOKEN=YourOAuthTokenFrom[https://github.com/settings/tokens]
MONGO_USERNAME=YourMongoDatabaseUsername
MONGO_PASSWORD=YourMongoDatabasePassword
MONGO_CLUSTER_URL=TheBlueHighlightedPartAt[https://user-images.githubusercontent.com/34750736/147285458-37d3d5d9-d530-46fd-8a81-5c690c16b629.png]
DATABASE_NAME=YourMongoDatabaseName
DATABASE_TEST=YourMongoTestDatabaseName
COLLECTION_REPOSITORIES=YourMongoCollectionForRepositoryDataName
COLLECTION_USER=YourMongoCollectionForUserDataName
COLLECTION_TEST=YourMongoCollectionForTestingName
```
  
  
For simplicity reasons, I have the script <b><i>setupEnv.sh</i></b> that makes these .env files for you by asking you to input each of the requested values needed to run the program. You can run this by using the command:</p>
  
```
  bash setupEnv.sh
```
 
### Run the project
<p align=justify>After all the setup has been done, we can finally run the project using the script <b><i>visualize.sh</i></b> as so:</p>
  
```
  bash visualize.sh
```
  
<p align=justify>First, this will compile, test & package the Java portion of this project. Then, it will run the file <b><i>AccessGitHubAPI.java</i></b> with Maven to ask you what GitHub user you would like to visualize data from. After entering the username, you will see JSON printed to the screen as each repository from the user is stored in the MongoDB Atlas database, with the user data being stored at the end. Now that the data is stored in the cloud, the script will then build a Docker image using the Dockerfile inside of <b><i>/visualization</i></b>. After building this Docker Image, the script creates a Docker container with the image and starts the container. If you go to <b><i>http://localhost:8080/</i></b>, you will see the application there.</p>
  
  
### Testing
<p align=justify>There are two elements to test in this project. One is the Java aspect of the project (inside the <b><i>/src</i></b>) directory, which is done with JUnit. Another element is the Javascript aspect of the project (inside the <b><i>/visualization</i></b>) directory (specifically Node.js) which is done with Jest. Tests are ran automatically every time you run <b><i>visualize.sh</i></b>. This script runs <b><i>mvn clean install</i></b> which runs all JUnit tests. The script also runs <b><i>docker run</i></b> which in turn will run <b><i>npm test</i></b> to run all tests with Jest.</p>