# Ch-958, Geo Citizen
### Getting started
1) First of all, clone the repository with `git clone`.
1) Go to the directory with cloned repository (`cd` or something).
1) Switch to the branch `dev` with next commands:
	```bash
	# checkout the branch
	git checkout dev
	# fetch last commits
	git fetch && git pull
	```
	Now you have the latest version of working project code.
1) Config everything you need:
	```bash
	# Edit with nano or any other text processor
	nano ./src/main/resources/application.properties
	```
	There are the properties, below are described just that you have to know:
	 - `db.url` - address to your created database (create if you haven't yet) for __back-end__;
	 - `db.username` - login for the database, for __back-end__;
	 - `db.password` - password for the database, for __back-end__;
	 - `url` - the same as `db.url` but for _Liquibase_ (read below);
	 - `username` - the same as `db.username` but for _Liquibase_;
	 - `password` - the same as `db.password` but for _Liquibase_;
1) Build and deploy the __back-end__ _.war_-file:
	```bash
	# Generate our .war.
	mvn install
	# Move generated WAR into your Tomcat webapps directory
	# (/usr/share/tomcat9/webapps/ in my case)
	mv ./target/ss*.war /usr/share/tomcat9/webapps/
	```
1) Restore necessary __database__ with _Liquibase_:
	```bash
	mvn liquibase:update
	```
1) Run your _Tomcat_ with the _.war_.
	```bash
	# The next is just my case
	/usr/share/tomcat9/bin/startup.sh
	```
1) Set the __back-end__ URL-address for our __front-end__:
	```bash
	# Edit the main.js file with nano or any other text processor
	nano ./front-end/src/main.js
	```
	Find the line with `...'http://...'` and replace it with your address from _Tomcat_.
	The result will be `http://localhost:8080/ss-demo1-0.0.2-SNAPSHOT` in my case.
1) Install __front-end__ dependencies:
	```bash
	# Go to front-end directory
	cd ./front-end
	# Run downloading and installing
	npm install
	```
	### Note about the next warning:
    ```bash
    npm WARN The package vue-material is included as both a dev and production dependency.
    ```
    That warning is O.K., just never mind.
1) Run __front-end__:
	```bash
	# Starting Vue.js
	npm run dev
	```
	After successful starting you will see the URL-address of your __front-end__.
