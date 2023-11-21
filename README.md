# 👨‍💻 SSAFY TRIP OPEN API
**여행 서비스를 위한 OPEN API**<br><br>
**바로가기 : https://k9b205.p.ssafy.io**
<br>


## ⬇️⬇️프로젝트 소개 (youtube로 보러가기)⬇️⬇️
  
싸피 1학기 개발자들이 진행하는 여행 웹 서비스 개발을 좀 더 풍성하게 해주기위한 여행관련 openAPI

기존 싸피 1학기 개발자들이 제공받는 여행지 데이터베이스를 이용, 그와 관련한 숙소, 음식점, 위키 데이터를 추가적으로 수집하여다양한 openAPI를 만들어 제공한다.
[![동영상 제목](https://img.youtube.com/vi/Qzm23sjetHo/0.jpg)](https://www.youtube.com/watch?v=Qzm23sjetHo)


**목표 : 여행지 관련 데이터를 제공하는 OPEN API**  

1. 여행지 관련 숙소 API
 - 여행지 이름 기준으로 주변 숙소 정보를 얻는다
 - 위경도 기준으로 주변 숙소 정보를 얻는다

2. 여행지 관련 음식점 API
 - 여행지 이름 기준으로 주변 음식점 정보를 얻는다 
 - 위경도 기준으로 주변 숙소 정보를 얻는다

3. 검색 보정 API
 -  

4. 스크래핑
 -
 
 <br>


## 📅프로젝트 기간

**23.10.10 ~ 23.11.16 (6주간)**    

## 🛠️주요 기능  

<details>

설명 한두줄 하고 토글
<summary>Home</summary>

![Alt text](/image/home1.png)
</details></br>

<details>
<summary>Docs</summary>

![Alt text](/image/docs2.png)
</details></br>

<details>
<summary>TokenPage</summary>
![Alt text](/image/token3.png)

</details></br>



## 🧝‍♂️팀원 및 역할  

| **팀장** | 신창학 (BE: Elastic search, Elastic search API)   |
|----------|---------------------|
| **팀원** | 강현곤 (BE: 숙소 데이터 크롤링, 숙소 데이터 API)             |
|          | 이지현 (BE: 음식점 데이터 크롤링, 음식점 데이터 API )  |
|          | 이진호 (Infra : EC2, nginx, docker, jenkins 세팅)     |
|          | 정형준 (BE: 데이터 크롤링 보조, 숙소 데이터 API, Java Global 객체, 스프링풀 성능측정) |
|          | 홍유빈 (BE: 환율 API )  |

## 👨‍👩‍👧협업 툴  

- GitLab
- Jira
- Notion
- Mattermost


## 🖥️ 개발 환경  

🖱**Backend**

- IntelliJ
- spring boot 2.7.17
- spring-boot-jpa
- Spring Security
- Java 11

🖱**Backend**

- mysql 8.0.23
- Redis 7.2.1
- MongoDB Cloud

🖱**Frontend**

- Visual Studio Code
- React.js 18.2.0
- node.js 18.16.1
- axios 1.4.0
- styled-components 6.0.4

🖱**CI/CD**

- AWS EC2
- docker
- nginx
- jenkins

## 🔧 서비스 아키텍쳐  

![Alt text](readme사진/image-23.png)

## 📑 API 명세서  

![Alt text](readme사진/image-2.png)


## ✨ERD  

![Alt text](readme사진/image.png)

## 📚 커밋 컨벤션 규칙

| Type 키워드 | 사용 시점 |
| --- | --- |
| 첫 커밋 | CREATE: start project |
| Add | 새로운 파일 추가 |
| Delete | 파일 삭제 |
| Feat | 새로운 기능 추가, 기존의 기능을 요구 사항에 맞추어 수정 |
| Fix | 기능에 대한 버그 수정 |
| Build | 빌드 관련 수정 |
| Chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
| Ci | CI 관련 설정 수정 |
| Docs | 문서(주석) 수정 |
| Style | 코드 스타일, 포맷팅에 대한 수정 |
| Refactor | 기능의 변화가 아닌 코드 리팩터링 ex) 변수 이름 변경 |
| Test | 테스트 코드 추가/수정 |
| Release | 버전 릴리즈 |
| Rename | 파일 혹은 폴더명을 수정만 한 경우 |
| Readme | README |
| Comment | 주석관련 |

 ***commit message***
  - commit은 최대한 자세히

`키워드(대문자) :  (영어로 위치/함수/기능) + 설명`

## 🌐EC2 PORT

| 서비스                 | 포트  |
|-----------------------|-------|
| Spring Boot | 8080  |
| Spring Boot: Elastic search | 8081  |
| React                 | 3000  |
| MySQL                 | 4000  |
| Jenkins               | 9090  |
