//This server will be the backend (Node.js) and act as an API on port 8081 to provide the frontend (Vue.js) on port 8080 with data to visualize.
//Firstly, we can import all the neccessary packages for our server.
let services= require("./services.js")
let express = require("express");
let serveStatic = require("serve-static");
let history = require("connect-history-api-fallback");
let cors = require('cors');

//Now, we can use our services.js class to connect with the MongoDB Atlas database and provide the information as JSON at the endpoints '/repo' and '/userdata'.
let app = express();
app.get('/repo', services.repoCollectionAsJSON);
app.get('/userdata', services.userCollectionAsJSON);
app.use(history());
app.use(serveStatic(__dirname + "/dist"));

//We will have 2 pages in our application, one for the home page 'http://localhost:8080/' and the visualization page 'http://localhost:8080/visualization'.
app.get("/", (req, res) => {
  res.status(200).send("This is the home page!");
});
app.get("/visualization", (req, res) => {
  res.status(200).send("This is the stats page!");
});
app.use(cors());
let port = 8081;
module.exports = app.listen(port);
