# Spring Cloud Gateway

1. Dependencies
   
 ```bash
<dependency>
 <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```
```bash
<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
```bash
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
```
```bash
<dependency>
 <groupId>org.postgresql</groupId>
 <artifactId>r2dbc-postgresql</artifactId>
 <scope>runtime</scope>
</dependency>
```
```bash
<dependencyManagement>
 <dependencies>
  <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-dependencies</artifactId>
   <version>${spring-cloud-version}</version>
   <type>pom</type>
   <scope>import</scope>
  </dependency>
 </dependencies>
</dependencyManagement>
```

2. Application.yml
```bash
server:
  port: 8181
logging:
  level:
    io:
     # r2dbc: DEBUG
    org:
      springframework:
        data:
        #  r2dbc: DEBUG
spring:
   application:
     name: api-gateway
   r2dbc:
     url: r2dbc:postgresql://localhost:5432/food_delivery
     username: codestorykh
     password: password
```

3. Schema

```bash
CREATE SEQUENCE IF NOT EXISTS api_route_id_seq start with 1;

CREATE TABLE IF NOT EXISTS api_route (
    id BIGINT PRIMARY KEY DEFAULT nextval('api_route_id_seq'),
    uri VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    group_code VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);
```
```bash
<build>
    <plugins>
        <!-- Remove it plugin if you want to run on IDE if not sure what wrong with my IDE -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>23</source> <!-- Or the Java version you are using -->
                <target>23</target> <!-- Or the Java version you are using -->
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```