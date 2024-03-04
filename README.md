# application.yml

<pre><code>
  spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/shop
    username: postgres
    password: "rainick98"
    driver-class-name: org.postgresql.Driver

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    database: postgresql
    hibernate:
      ddl-auto: create
    defer-datasource-initialization: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

  h2:
    console:
      enabled: true
      path: /h2-console

  sql:
    init:
      mode: always


logging.level:
  org.hibernate.SQL: debug

server:
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true



# 아임포트 가맹점 번호 / 키 / 비밀키
imp:
  code: "포트원 가맹점 번호 (큰 따옴표 없이)"
  api:
    key: "일반 키 (큰 따옴표 없이)"
    secret_key: "시크릿 키 (큰 따옴표 없이)"
</code></pre>
