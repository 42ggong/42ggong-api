# 42ggong-api


# 요구사항
- mariadb
- java 17


## Dependencies
- Spring Boot 3.1.2
- Spring Web
- Lombok


## Makefile 명령어
- Makefile 설치
  - `brew install make`


- make start
  1. mariadb 실행
  2. 환경 변수 설정
  3. 프로젝트 빌드
  4. API 서버 실행


- make clean
  1. 빌드 디렉토리 삭제
  2. mariadb 종료

  
- make build
  - 프로젝트 빌드

    
- make prebuild
  - 빌드 디렉토리 삭제


- make start_db
  - mariadb 실행


- make stop_db
  - mariadb 종료


- make restart_db
  - mariadb 재실행