# 👨‍💻 SSAFY FINANCE OPEN API
**마이데이터 규격에 맞게 금융 데이터를 제공하는 OPEN API**<br><br>
**바로가기 : https://j9b309.p.ssafy.io**
<br>


## ⬇️⬇️프로젝트 소개 (youtube로 보러가기)⬇️⬇️
  
현대 교육은 디지털 기술의 발전과 함께 많은 변화를 겪고 있습니다.  
특히, 온라인 학습은 교육의 패러다임을 바꾸며 학습 방식과 환경을 혁신하고 있습니다.  
이에 따라 학생과 선생님 모두에게 효과적인 학습 도구와 플랫폼이 필요하게 되었습니다.  
Forest는 이러한 변화에 발맞춰 개발된 온라인 학습 플랫폼입니다.  
학생들이 온라인 상에서 학습을 진행하고 성적을 분석하여 개인의 학습 상태를 파악할 수 있는 기능을 제공합니다.  
또한, 선생님들은 커스텀 학습지를 만들고 배포하여 학생들의 학습을 관리할 수 있습니다.  
온라인 학습지 Forest로 더 효과적인 학습을 경험해 보세요!  

[![동영상 제목](https://img.youtube.com/vi/Qzm23sjetHo/0.jpg)](https://www.youtube.com/watch?v=Qzm23sjetHo)


**목표 : 다양한 금융 데이터를 제공하는 OPEN API**  

1. OAuth 2.0 사용자 인증

 - 개인신용정보 전송요구에 OAuth2.0 방식 채택 

2. 개인 카드목록 조회 & 카드 사용내역 조회 API

 - 개인 카드 사용내역을 통한 소비패턴 분석

3. 한 눈에 보는 나의 투자정보 API

 - 증권사 별, 계좌 별로 흩어져 있는 나의 투자내역를 한 번에 조회할 수 있는 서비스

4. 환율 API

 - 실시간 은행별, 나라별 환율 정보 제공

 <br>


## 📅프로젝트 기간

**23.08.28 ~ 23.10.06 (6주간)**    

## 🛠️주요 기능  

<details>
<summary>1. 로그인</summary>

![Alt text](readme사진/image-3.png)
![Alt text](readme사진/image-21.png)
![Alt text](readme사진/image-22.png)
</details></br>

<details>
<summary>2. 소개팅</summary>

![Alt text](readme사진/image-4.png)
![Alt text](readme사진/image-7.png)
![Alt text](readme사진/image-6.png)
</details></br>

<details>
<summary>3. 프로젝트</summary>
![Alt text](readme사진/image-8.png)
![Alt text](readme사진/image-9.png)
![Alt text](readme사진/image-10.png)
![Alt text](readme사진/image-11.png)
</details></br>

<details>
<summary>4. 채팅</summary>
![Alt text](readme사진/image-12.png)
![Alt text](readme사진/image-13.png)
![Alt text](readme사진/image-14.png)
</details></br>

<details>
<summary>5. SSAFY-TOWN</summary>
![Alt text](readme사진/image-15.png)
![Alt text](readme사진/image-20.png)
![Alt text](readme사진/image-16.png)
![Alt text](readme사진/image-17.png)
![Alt text](readme사진/image-18.png)
![Alt text](readme사진/image-19.png)
</details></br>

## 🧝‍♂️팀원 및 역할  

| **팀장** | 홍유빈 (BE : OAuth 2.0, docs API)   |
|----------|---------------------|
| **팀원** | 강현곤 (BE : )             |
|          | 권기연 (BE : 개인 카드 목록 조회 및 카드 사용 내역 조회 API)  |
|          | 김하영 (BE : 환율 API )  |
|          | 이진호 (BE : 한 눈에 보는 나의 투자정보 API )     |
|          | 정형준 (Infra) |

## 👨‍👩‍👧협업 툴  

- GitLab
- Jira
- Notion
- Mattermost


## 🖥️ 개발 환경  

🖱**Backend**

- IntelliJ
- spring boot 2.7.15
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
| Spring Boot: Stock    | 8082  |
| Spring Boot: FinancialData | 8083  |
| Spring Boot: Exchange | 8084  |
| Spring Boot: Oauth    | 8087  |
| Spring Boot: Resource | 8088  |
| Spring Boot: Security | 8089  |
| React                 | 3000  |
| MySQL                 | 3306  |
| Jenkins               | 9090  |


