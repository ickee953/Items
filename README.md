# Quick start

1. Create new project from pom.xml<br>
2. Execute maven target 'package'<br>
3. Use Docker with docker-compose and compose-env.yaml to run mongoDB server container:<br>
	run from terminal command: docker-compose -f compose-env.yaml up<br>
3. Run java -jar target/items-1.0-SNAPSHOT.jar<br>
3. Open page http://localhost:8080/items in you web browser<br><br>

You can also try it without building at https://5.8.10.225/items with login/pass: test@domain.com/123

![f_781643e9931de41f](https://github.com/ickee953/items/assets/152408327/adf2be52-368b-41c6-b52b-a720efb5cfe0)
