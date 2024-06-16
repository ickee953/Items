# Quick start

1. Create new project from <b>pom.xml</b><br>
2. Execute maven target <b>"package"</b><br>
3. Use Docker with docker-compose and compose-env.yaml to run mongoDB server container:<br>
	run from terminal command: <b>docker-compose -f compose-env.yaml up</b><br>
4. For run application server execute command: <b>java -jar target/items-1.0-SNAPSHOT.jar</b><br>
5. Open page http://localhost:8080/items in you web browser<br>
6. Create <b>uploads</b> directory to store uploaded images, or you can set it with properties param.
For example: <b>java -jar target/items-1.0-SNAPSHOT.jar --upload.dir=/uploads</b><br>
7. Werify that <b>/uploads</b> directory have write permissions, for example change "others" directory permissions to "write" with terminal command: <b>chmod o+w /uploads</b><br><br>
You can also try it without building at https://5.8.10.225/items with login/pass: test@domain.com/123

![f_781643e9931de41f](https://github.com/ickee953/items/assets/152408327/adf2be52-368b-41c6-b52b-a720efb5cfe0)
