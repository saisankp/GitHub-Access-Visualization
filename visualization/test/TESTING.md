## How this app is tested

This application uses Node.js which is tested with Jest. 

<h3><u>server.test.js</u></h3>

Requirements: `Jest`

What is tested: `server.js`

The file `server.test.js` uses Jest, and tests the file server.js, which uses Node.js. It checks the response codes from the routing of pages, and also outputs a code coverage report in `visualization/coverage`. 

You can run it with: `npm test` inside of  `visualization`

`Test 1: Test the root path`

`Test 2: Test the path to visualization page`
