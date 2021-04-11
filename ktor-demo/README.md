# README.md

## Creating container images:
1. Run `gradlew clean build`.
##### Create and publish container image app-a: 
2. Run `docker build -t "ktor-demo-app-a:0.0.1-SNAPSHOT-powuv" ./app_a`.
3. Run `docker run -d -p 8123:8123 ktor-demo-app-a:0.0.1-SNAPSHOT-powuv`.
4. Verify working via browser: `http://localhost:8123` and/or `http://localhost:8123/json/gson`.
5. Tag the remote repository: `docker tag ktor-demo-app-a:0.0.1-SNAPSHOT-powuv joramnv/ktor-demo-app-a:0.0.1-SNAPSHOT-powuv`.
6. Push the image to the remote repository: `docker push joramnv/ktor-demo-app-a:0.0.1-SNAPSHOT-powuv` <-- in this case it is public repo! (But you can make a private repo namespace first via the docker hub website.)
##### Create and publish container image app-b:
7. Run `docker build -t "ktor-demo-app-b:0.0.1-SNAPSHOT-powuv" ./app_b`.
8. Run `docker run -d -p 8223:8223 ktor-demo-app-b:0.0.1-SNAPSHOT-powuv`.
9. Verify working via browser: `http://localhost:8223` and/or `http://localhost:8223/json/gson`.
10. Tag the remote repository: `docker tag ktor-demo-app-b:0.0.1-SNAPSHOT-powuv joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-powuv`.
11. Push the image to the remote repository: `docker push joramnv/ktor-demo-app-b:0.0.1-SNAPSHOT-powuv` <-- in this case it is public repo! (But you can make a private repo namespace first via the docker hub website.)
12. Continue with `steps-to-set-up-cluster.md` in the parent directory of this project.



## Local development:
Running app-a, app-b locally can be done through: `gradlew :app_a:run` or `gradlew :app_b:run`

Running Dapr side car locally can be done through:
```
dapr run --app-id app-a --app-port 8123 --dapr-http-port 3500 --dapr-grpc-port 50001 -- waitfor FOREVER
dapr run --app-id app-b --app-port 8223 -- waitfor FOREVER
```
(Prerequisite is that you've the Dapr CLI installed on your machine.)

- Note - I had some issues where locally Dapr could not find app-b from app-a and visa versa. I was connected to a VPN, when I turned off the VPN Dapr was functioning as normal again.
