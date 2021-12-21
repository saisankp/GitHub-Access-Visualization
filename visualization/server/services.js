
    const { MongoClient } = require("mongodb");
    const dotenv = require('dotenv');
    dotenv.config({ path: '../.env' });
    // Replace the uri string with your MongoDB deployment's connection string.
    const uri = "mongodb+srv://" + process.env.MONGO_USERNAME + ":" + process.env.MONGO_PASSWORD + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
    const client = new MongoClient(uri);
    var value = 0;
    var value2 = 0;
    var i = 0;

    exports.mongodbcall = (req, res) => {
        run();
        const obj = JSON.parse(JSON.stringify(value))
        console.log(value);
        console.log("test");
        res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, \Authorization');
        res.json(obj)
    }

    exports.mongodbcall2 = (req, res) => {
        run2();
        const obj = JSON.parse(JSON.stringify(value2))
        console.log(value2);
        i++;
        console.log("testm" + i);
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
        // Query for a movie that has the title 'Back to the Future'
        const movie = movies.find();
        const results = await movie.toArray();
        //console.log(results);
        value = results;
        return results;
      } finally {
        // Ensures that the client will close when you finish/error
        //await client.close();
      }
    }
    run().catch(console.dir);


    async function run2() {
        try {
          await client.connect();
          const database = client.db(process.env.DATABASE_NAME);
          const movies = database.collection(process.env.COLLECTION_USER);
          // Query for a movie that has the title 'Back to the Future'
          const movie = movies.find();
          const results = await movie.toArray();
          //console.log(results);
          value2 = results;
          return results;
        } finally {
          // Ensures that the client will close when you finish/error
          //await client.close();
        }
      }
run2().catch(console.dir);