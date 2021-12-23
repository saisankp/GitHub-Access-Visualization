
    //This file will connect with our MongoDB Atlas database and provide the database information to our server as JSON.
    //Firstly, we can import all the neccessary packages for our server.
    var util= require('util');
    var encoder = new util.TextEncoder('utf-8');
    const { MongoClient } = require("mongodb");
    const dotenv = require('dotenv');
    dotenv.config({ path: './.env' });
    

    //Connect to MongoDB Atlas with the details in the .env file inside the visualization directory.
    const uri = "mongodb+srv://" + process.env.MONGO_USERNAME + ":" + process.env.MONGO_PASSWORD + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
    console.warn("Server running on port 8081 with data for the application. Now we can run the application on port 8080 in this docker container.");
    const client = new MongoClient(uri);
    console.warn(process.env.MONGO_USERNAME);
    
    //Variables which will soon hold the data from our Database.
    var repoData = 0;
    var userData = 0;

    exports.repoCollectionAsJSON = (req, res) => {
        getRepoData();
        const repoDataAsJSON = JSON.parse(JSON.stringify(repoData));
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, \Authorization');
        res.json(repoDataAsJSON);
    }

    exports.userCollectionAsJSON = (req, res) => {
        getUserData();
        const userDataAsJSON = JSON.parse(JSON.stringify(userData));
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, \Authorization');
        res.json(userDataAsJSON);
    }

    async function getRepoData() {
      try {
        await client.connect();
        const database = client.db(process.env.DATABASE_NAME);
        const collection = database.collection(process.env.COLLECTION_REPOSITORIES);
        const movie = collection.find();
        const results = await movie.toArray();
        repoData = results;
      } catch (e) {
        console.log(e);
      }
    }
    getRepoData().catch(console.dir);
    async function getUserData() {
        try {
          await client.connect();
          const database = client.db(process.env.DATABASE_NAME);
          const collection = database.collection(process.env.COLLECTION_USER);
          const movie = collection.find();
          const results = await movie.toArray();
          userData = results;
        } catch (e) {
          console.log(e);
        }
      }
    getUserData().catch(console.dir);