# ch-058, geocitizen
###build and deploy (ubuntu16, git2, maven3, tomcat9)
1) `git clone https://github.com/nromanen/Ch-058.git; cd Ch-058`
1) `mvn install -Dmaven.test.skip && mv target/citizen.war /usr/share/tomcat9/webapps/`
1) [geocitizen](http://localhost:8080/citizen/)
1) THE END
###config   
1) config file [GiHub](git.io/vA4Sw)
	you might want to edit following properties
	 * `front.url` - url where front will be deployed to (for redirecting use `#` at the url's end)
     * `front-end.url` - same as `front.url` but w/o `#`
	 * `db.url` - uri to db (__you need to create one manually if you haven't done it yet__)
	 * `db.username` and `db.password` - db credentials

1) start tomcat
	```bash
	#e.g.
	/usr/share/tomcat9/bin/startup.sh
	```
1) set the backend url for frontend
	```bash
	vim ~/front-end/src/main.js
	```
	on the line ([GitHub](git.io/vA49U)) `export const backEndUrl =` and replace  url with tomcat's address
	(in my case url is `'http://localhost:8080/citizen'`)
1) install frontend dependencies
	```bash
	#run in the frontend directory
	npm install
	```
	___nevermind following warning___
    ```bash
    npm WARN The package vue-material is included as both a dev and production dependency
    ```
1) start frontend
	```bash
	npm run dev
	```
	after successful execution of prev step you'll see frontend url
###other
[swagger](http://localhost:8080/citizen/swagger-ui.html)

[heroku](https://geocitizen.herokuapp.com)  
  

