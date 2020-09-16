# README.md

1. Run `./gradlew clean build`.
2. Run `docker build -t "ktor-demo:0.0.1-SNAPSHOT-ljsfd" .`.
3. Run `docker run -d -p 8123:8123 ktor-demo:0.0.1-SNAPSHOT-ljsfd`.
4. Verify working via browser: `http://localhost:8123` and/or `http://localhost:8123/json/gson`.
5. Tag the remote repository: `docker tag ktor-demo:0.0.1-SNAPSHOT-ljsfd joramnv/ktor-demo:0.0.1-SNAPSHOT-ljsfd`.
6. Push the image to the remote repository: `docker push joramnv/ktor-demo:0.0.1-SNAPSHOT-ljsfd` <-- in this case it is public repo! (But you can make a private repo namespace first via the docker hub website.)
7. Continue with `steps-to-setup-cluster.md` in the parent directory of this project.
