
    var util= require('util');
    var encoder = new util.TextEncoder('utf-8');
    const { MongoClient } = require("mongodb");
    const dotenv = require('dotenv');
    dotenv.config({ path: './.env' });
    const uri = "mongodb+srv://" + process.env.MONGO_USERNAME + ":" + process.env.MONGO_PASSWORD + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
    console.warn("Server running on port 8081 with data for the application. Now we can run the application locally in this docker container.");
    const client = new MongoClient(uri);
    var value = 0;
    var value2 = 0;
    var i = 0;

    exports.mongodbcall = (req, res) => {
        run();
        const obj = JSON.parse(JSON.stringify(value))
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, \Authorization');
        res.json(obj)
    }

    exports.mongodbcall2 = (req, res) => {
        run2();
        const obj = JSON.parse(JSON.stringify(value2))
        i++;
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, \Authorization');
        res.json(obj)
    }

    async function run() {
      try {
        await client.connect();
        const database = client.db(process.env.DATABASE_NAME);
        const movies = database.collection(process.env.COLLECTION_REPOSITORIES);
        const movie = movies.find();
        const results = await movie.toArray();
        value = results;
      } catch (e) {
        console.log(e);
      }
    }
    run().catch(console.dir);


    async function run2() {
        try {
          await client.connect();
          const database = client.db(process.env.DATABASE_NAME);
          const movies = database.collection(process.env.COLLECTION_USER);
          const movie = movies.find();
          const results = await movie.toArray();
          value2 = results;
        } catch (e) {
          console.log(e);
        }
      }
run2().catch(console.dir);