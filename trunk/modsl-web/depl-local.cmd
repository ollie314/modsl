call mvn clean install
copy target\modsl-web*.* "%CATALINA_HOME%\webapps"
