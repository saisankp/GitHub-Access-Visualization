
let services= require("./services.js")
let express = require("express");
let serveStatic = require("serve-static");
let history = require("connect-history-api-fallback");
let cors = require('cors');
let app = express();
app.get('/repo', services.mongodbcall);
app.get('/userdata', services.mongodbcall2);
app.use(history());
app.use(serveStatic(__dirname + "/dist"));
app.get("/", (req, res) => {
  res.status(200).send("This is the home page!");
});
app.get("/visualization", (req, res) => {
  res.status(200).send("This is the stats page!");
});
app.use(cors());
let port = process.env.APP_PORT || 8081;
module.exports = app.listen(port);
