## Long story short
### Heroku host link app
<a href="https://git.heroku.com/serene-reef-85899.git" >https://git.heroku.com/serene-reef-85899.git</a>
### Sample application.properties
<code>
server.port=9000
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL82Dialect
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:postgresql://localhost:5432/betterprogramming
spring.datasource.username=apps-blog
spring.datasource.password=sherlockH@lmes05
spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER
springdoc.api-docs.path=/api/v1/public/api-docs
springdoc.swagger-ui.path=/api/v1/public/swagger-ui.html
spring.profiles.active=development
</code>

### Sample system.properties, at project room
<code>
java.runtime.version = 11
</code>