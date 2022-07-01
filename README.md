# RESTAPI
## 목적
DB를 연동해 조회, 저장, 삭제 등의 기능 추가

개발 환경  
OS : windows 10  
dev tool : Eclipse IDE (jee 2022 03)  
java : 16  
framework : Spring boot 2.7.0  
build tool : gradle 7.4.1  
RDBMS : MySQL  
  DBCP : hikariCP  
  persistence framework : X  

MySQL workbench  
lombok  
gradle 3.0  
groovy 4.6  
sts 4.15.1  
jdbc  
gson  
slf4j, logback  
  
## 공부 내용
DB : Use MySQL as jdbcTemplate  
데이터 전송 형식 : json  
비주얼 데이터베이스 설계 도구 : MySQL workbench  
Gradle : Maven 보다 사용성, 성능 좋음. 라이브러리 활용 도움. 프로젝트 라이프사이클 관리 도구  
build.gradle : 	dependencies(Spring Boot DevTools(개발 편리 기능등 제공, Lombok(코드 심플해짐), Mybatis Framework(db framework), MySQL Driver, Spring Web  

기능  
build.gradle - dependencies :	slf4j - logging framework를 라이브러리만 추가해 binding 가능, deployement time에 log library와 동적 연결 -> log library 변경 시 로그 엔진 변경 가능  
log library - logback : spring은 설정 파일은 logback-spring.xml으로 작성  
log4j - log level : ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF (case : log level이 WARN이면 WARN, ERROR, FATAL 로그가 찍힌다.)  

커밋  
0608commit  
feat: Create Project, Add DB Info to yml, Test api and json api  

Create Project : Use SpringBoot and gradle.  
Add DB Info to yml : add driver class to run Spring Boot App  
Test json api : Jackson, GSON  
comments to remind  

0610commit  
feat: Add slf4j, logback  

Add slf4j, logback at build.gradle dependencies, Add slf4j at InfoController  
결과 17:53:18.479 [http-nio-8080-exec-1] DEBUG com.example.demo.info.InfoController - /info start  

0613commit  
feat: Add log level info, Save file as log of above info level, Use DI to InfoController  

Add log level info.  
Save file as log of above info level : And print all level log to   
console. The file name is date like every midnight.  
Use DI to InfoController : Add service class. Use @service, @autowired  
comments remind to point  

0614commit  
feat: Use MySQL as JdbcTemplate  

add jdbcTemplate at dependency of build.gradle. add db connect info at   
datasource of application.yml. create City.java for test DB at model.  
create for test DB. create more efficient query as groovy, RowMapper,   
Repository.  

0615no commit  
Learn DB tool. Use DB.  
/cityList output ex) [{"id":1,"name":"Kabul","countryCode":"AFG",...  

0616commit  
feat: Add sql select function at controller as JdbcTemplate  

add DBCP : HikariCP.  
  add hikariCP at application.yml  
use @RequestMapping("info").  
add function for sql select.  
  add @PathVariable, @RequestParam at controller.  
  the service sends the result of jdbcTemplate.query() of CityRepository to controller  
  add function for sql query at CityRepository, CitySql.groovy  

0620commit  
feat: Add GetMapping, PostMapping  

test GetMapping with @PathVariable, @RequestParam.  
test PostMapping.  
  request as JSON, Query Params.  
    use rest client for post request as json form.  
  do exception handling.  
{  
  "name": "TEST",  
  "countryCode": "TST",  
  "district": "Seoul",  
  "population": 100  
}  

0623commit  
feat: Add cityInsert, cityEdit, cityDelete function  

Add cityInsert, cityEdit, cityDelete function using jdbcTemplate.  
  use INSERT, UPDATE, DELETE in sql query statement.  
CitySql.groovy : static final member variable for make sql query   
statement.  
CityRepository.java : NamedParameterJdbcTemplate use datasource to   
connect DB.  
  keyholder function : get created key when insert.  
InfoService.java : just call cityRepository function.  
InfoController.java : recieve json as post form. call infoService for   
INSERT, UPDATE, DELETE function  

0630commit  
feat: add file upload, file download, file delete, print file list  

add storage package  
  interface StorageService  
  FileSystemStorageService implements StorageService  
file upload, download, delete, print list  

0701commit  
fix: Write README.md

write README.md as organized what i have studied

다음엔 인텔리제이 ide 사용해보기
# end project