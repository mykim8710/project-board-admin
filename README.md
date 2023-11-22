# 게시판 서비스 어드민 개발

## 개요
Springboot + JPA를 사용하여 개발한 게시판 서비스를 관리하기 위한 관리자 서비스입니다.

* 기능목록
  * JWT 인증/인가
  * ADMIN 관리
    * 간단한 Dashboard
    * 관리자 계정 관리
  * Board Service 관리(게시판 서비스와 API 통신)
    * 게시글 관리 페이지
    * 회원 관리 페이지
    * 댓글관리 페이지
    * 해시태그 관리 페이지

## 개발 환경

* Intellij IDEA Ultimate
* Java 17
* Gradle 7.6.1
* Spring Boot 2.7.11

## 기술 세부 스택

Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Thymeleaf
* H2 Database
* MySQL Driver
* Lombok
* Spring Boot DevTools
* QueryDSL 5.0.0
* WebSocket
* AWS Parameter Store
* JWT
* Rest template

그 외
* Bootstrap
* JavaScript
* JQuery


## Package Design
```
 └── src
    ├── main
    │   ├── java
    │   │     └── io.mykim.projectboardadmin
    │   │            ├── adminuser
    │   │            │    ├── api
    │   │            │    │    └── AdminUserApiController(C)    
    │   │            │    ├── dto
    │   │            │    │    │── AdminUserInfoDuplicateCheckDto(C)
    │   │            │    │    │── RequestAdminUserCreateDto(C)
    │   │            │    │    │── RequestAdminUserFindDto(C)
    │   │            │    │    └── RequestAdminUserListDto(C)
    │   │            │    ├── entity
    │   │            │    │    │── AdminUserRole(E)
    │   │            │    │    └── AdminUser(C)
    │   │            │    ├── repository
    │   │            │    │    │── AdminUserQuerydslRepository(I)
    │   │            │    │    │── AdminUserRepository(I)
    │   │            │    │    └── AdminUserQuerydslRepositoryImpl(C)
    │   │            │    └── service
    │   │            │         └── AdminUserService(C)
    │   │            ├── config
    │   │            │    ├── resttemplate
    │   │            │    │    └── RestTemplateConfig(C)
    │   │            │    └── security
    │   │            │         ├── dto
    │   │            │         │    ├── PrincipalDetail(C)
    │   │            │         │    └── SignInRequestDto(C)
    │   │            │         ├── handler
    │   │            │         │    ├── CustomAccessDeniedHandler(C)
    │   │            │         │    ├── CustomAuthenticationEntryPoint(C)
    │   │            │         │    ├── CustomAuthenticationDailureHandler(C)
    │   │            │         │    └── CustomAuthenticationSuccessHandler(C)
    │   │            │         ├── jwt
    │   │            │         │    ├── entity
    │   │            │         │    │      └── JwtRefreshToken(E)
    │   │            │         │    ├── enums
    │   │            │         │    │      └── TokenType(E)
    │   │            │         │    ├── filter
    │   │            │         │    │      │── JwtAuthenticationFilter(C)
    │   │            │         │    │      └── JwtAuthorizationFilter(C)
    │   │            │         │    ├── repository
    │   │            │         │    │      └── JwtRefreshTokenRepository(I)
    │   │            │         │    ├── service
    │   │            │         │    │      └── JwtRefreshTokenService(C)
    │   │            │         │    ├── JwtProperties(C)
    │   │            │         │    └── JwtProvider(C)
    │   │            │         ├── service
    │   │            │         │    └── CustomPrincipalDetailService(C)
    │   │            │         └── SpringSecurityConfig(C)
    │   │            ├── global
    │   │            │    │── jpa
    │   │            │    │    │── AuditingFeild(C)
    │   │            │    │    └── QuerydslUtils(C)
    │   │            │    │── pageable
    │   │            │    │     └── CustomPaginationResponse(C)
    │   │            │    ├── response
    │   │            │    │    │── dto
    │   │            │    │    │    │── CommonResponse(C)
    │   │            │    │    │    └── ValidationError(C)
    │   │            │    │    │── enums
    │   │            │    │    │     │── CustomErrorCode(E)
    │   │            │    │    │     └── CustomSuccessCode(E)
    │   │            │    │    │── exception
    │   │            │    │    │     │── BusinessRollbackException(C)
    │   │            │    │    │     │── DuplicateException(C)
    │   │            │    │    │     │── NotFoundException(C)
    │   │            │    │    │     └── NotValidRequestException(C)
    │   │            │    │    │── handler
    │   │            │    │    │     └── GlobalExceptionHandler(C)
    │   │            │    │    │── model
    │   │            │    │    │     │── CommonResponse(C)
    │   │            │    │    │     └── ValidationError(C)
    │   │            │    │    └── CommonResponseUtils(C)
    │   │            │── user    
    │   │            │    ├── api
    │   │            │    │    │── ArticleCommentManagementApiController(C)
    │   │            │    │    │── ArticleManagementApiController(C)
    │   │            │    │    │── HashtagManagementApiController(C)
    │   │            │    │    └── ServiceUsersManagementApiController(C)
    │   │            │    ├── controller
    │   │            │    │    └── ManagementController(C)    
    │   │            │    ├── dto
    │   │            │    │    │── HashtagCreateDto(C)
    │   │            │    │    └── RequestSearchConditionDto(C)
    │   │            │    └── service
    │   │            │         └── BoardRestTemplateService(C)   
    │   │            │── todo    
    │   │            │    ├── api
    │   │            │    │    └── TodoApiController(C)
    │   │            │    ├── controller
    │   │            │    │    └── UserViewController(C)    
    │   │            │    ├── dto
    │   │            │    │    │── TodoCreateDto(C)
    │   │            │    │    │── TodoFindDto(C)
    │   │            │    │    └── TodoListDto(C)
    │   │            │    ├── entity
    │   │            │    │    │── Todo(C)
    │   │            │    │    └── TodoStatus(E)
    │   │            │    ├── repository
    │   │            │    │    └── TodoRepository(C)
    │   │            │    └── service
    │   │            │         └── TodoService(C)   
    │   │            │── ViewController(C)
    │   │            └── ProjectBoardAdminApplication(C)
    │   └── resources
    │       ├── static           
    │       │     ├── css
    │       │     │    └── sign-in.css
    │       │     ├── images
    │       │     │    └── sign-in.png
    │       │     └── js
    │       │          ├── apis.js
    │       │          ├── common.js
    │       │          └── sign-in.js
    │       ├── templates
    │       │     ├── admin
    │       │     │    ├── admin-users.html
    │       │     │    └── dashboard.html
    │       │     ├── layout
    │       │     │    ├── layout-head.html
    │       │     │    ├── layout-header.html
    │       │     │    ├── layout-main.html
    │       │     │    └── layout-sidebar.html
    │       │     ├── management
    │       │     │    ├── articles.html
    │       │     │    ├── article-comments.html
    │       │     │    ├── hashtags.html
    │       │     │    └── service-users.html
    │       │     └── index.html
    │       ├── data.sql        
    │       ├── application-prod.yaml           
    │       └── application.yaml
```

## 데모 페이지
url :http://ec2-3-36-158-39.ap-northeast-2.compute.amazonaws.com:9090
테스트 계정 : master/1234

## CI/CD method
- 스크립트 작성 후 서버에 저장한 뒤 서버에서 스크립트를 실행하는 방식으로 채택
- 스크립트 내에서 git으로 부터 pull 받고 build, jar파일 실행하는 방식

## Reference
* 유즈케이스 다이어그램
<img src="documents/Project-Board-Admin_usecase.svg">
