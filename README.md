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

  mail:
    host: smtp.gmail.com #SMTP 서버 호스트
    port: 587 #SMTP 서버 포트
    username: #SMTP 서버 로그인 아이디 (발신자) (예시 aaa@gmail.com 이라면 aaa부분 해당)
    password: #SMTP 서버 로그인 패스워드 (앱 비밀번호)
    properties:
      mail:
        smtp:
          auth: true #사용자 인증 시도 여부 (기본값 = false)
          timeout: 5000 #Socket Read Timeout 시간(ms) (기본값 = 무한)
          starttls:
            enable: true #StartTLS 활성화 여부 (기본값 = false)


logging.level:
  org.hibernate.SQL: debug

server:
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true
  # 스프링 부트가 오류 응답을 생성할 때 오류 메시지를 포함할지 여부 (오류 메시지를 클라이언트에 전달할지)
  error:
    include-message: always



# 아임포트 가맹점 번호 / 키 / 비밀키
imp:
  code: 포트원 가맹점 번호
  api:
    key: 일반 키
    secret_key: 시크릿 키


# AWS S3
cloud:
  aws:
    s3:
      bucket: 버킷 이름
    credentials:
      access-key: 에세스 키
      secret-key: 비밀 키
    region:
      static: ap-northeast-2
      auto: false
    stack:
      auto: false
</code></pre>
