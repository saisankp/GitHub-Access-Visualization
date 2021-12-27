mvn clean install
mvn exec:java -Dexec.mainClass="accessGitHubAPI.AccessGitHubAPI"
cd visualization
npm install
docker build -t vis .
docker run --env-file <(bash -c 'env | grep AWS_') -it -p 8080-8081:8080-8081 vis
