1) Build:
cd 33across
mvn clean install

2) After deploying the application to Tomcat, run  Postman, set headers: EADER_API_KEY:12345, Content-Type: application/json, always use POST method
Populate ccds1.csv in http request body, url: http://localhost:8080/exercise/v1/process/ccds1
Populate ccds2.tsv in http request body, url: http://localhost:8080/exercise/v1/process/ccds2

Populate EU counties "AT,BE,BG,CY,CZ,DE,DK,EE,ES,FI,FR,GB,GR,HR,HU,IE,IT,LT,LU,LV,MT,NL,PO,PT,RO,SE,SI,SK" as filter in http request body, url: http://localhost:8080/exercise/v1/fetch

