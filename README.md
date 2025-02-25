![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=Starting%20Point&fontSize=40)

------------

## 📦Starting Point
### 👤 팀 명: E1GHTEEN

------------

### 🚩 프로젝트 목적
- 음식 주문 관리 플랫폼
    - 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼입니다.
- 주요 기능
    - 사용자 관리:  사용자는 로그인하여 정보를 조회 및 수정할 수 있으며, 관리자는 회원 정보를 열람할 수 있습니다. 
    - 음식 주문 관리: 사용자는 쉽게 음식을 주문할 수 있으며, 식당은 음식 정보를 등록할 수 있습니다.
    - 결제 시스템: 간편한 결제 기능으로 사용자와 음식점 간의 거래가 이루어집니다.
    - 주문 내역 관리: 사용자는 자신의 주문 내역을 관리할 수 있으며, 관리자는 전체 주문 내역을 확인하고 처리할 수 있습니다.
    - 배달 프로세스: 사용자가 음식을 주문하면 할당된 라이더가 음식을 배달하며, 배달이 완료된 건에 한해 사용자는 리뷰를 작성할 수 있습니다.
    - 리뷰 및 평점 시스템: 사용자는 배달이 완료된 후 음식에 대한 내용을 작성하고 평점을 부여하여 다른 사용자와 음식점에 리뷰를 제공합니다.

------------
### 📍 MVP 소개 (프로젝트 상세)

<details markdown="1">
  <summary>사용자</summary>
  <div>
    <ul>
      <li>일반 회원(고객/가게 주인)</li>
      <li>회원가입 : 사용자는 고객/가게 주인의 권한으로 회원가입할 수 있습니다.</li>
      <li>회원정보 수정 : 사용자는 주소, 이메일, 전화번호, 닉네임을 변경할 수 있습니다.</li>
      <li>로그인 : 사용자는 생성한 아이디와 비밀번호로 로그인할 수 있습니다.</li>
      <li>회원 탈퇴 : 사용자는 본인의 아이디를 삭제할 수 있습니다.</li>
    </ul>
  </div>
</details>

<details markdown="1">
  <summary>관리자</summary>
  <div>
    <ul>
      <li>회원정보 조회 : 관리자는 사용자 권한, 아이디, 특정 시점 사이의 가입자 등 여러 조건에 대해 사용자를 조회할 수 있습니다.</li>
      <li>회원정보 수정 : 관리자는 사용자 권한, 아이디, 삭제 여부등의 정보를 일부 수정할 수 있습니다.</li>
    </ul>
  </div>
</details>


<details markdown="1">
  <summary>식당</summary>
  <div>
    <ul>
      <li>식당 전체 조회 및 검색 : 식당 검색 및 전체 조회를 할 수 있습니다.</li>
      <li>식당 단건 조회 : 식당 내용에 대한 단건 조회를 할 수 있습니다.</li>
      <li>식당 생성 : 마스터 사용자는 식당을 생성할 수 있습니다.</li>
      <li>식당 수정 : 마스터, 매니저, 식당 주인은 식당 내용을 수정할 수 있습니다.</li>
      <li>식당 삭제 : 마스터, 매니저, 식당 주인은 식당을 삭제할 수 있습니다.</li>
    </ul>
  </div>
</details>


<details markdown="1">
  <summary>음식 - 메뉴</summary>
  <div>
    <ul>
      <li>메뉴 생성 : 마스터, 매니저, 가게 사장이 메뉴를 생성할 수 있습니다.</li>
      <li>메뉴 전체 조회 및 검색 : 모든 사용자가 식당의 전체 메뉴를 조회 및 검색 할 수 있습니다. </li>
      <li>메뉴 단건 조회 : 모든 사용자가 식당의 특정 메뉴를 조회할 수 있습니다.</li>
      <li>메뉴 수정 : 마스터, 매니저, 가게 사장이 메뉴를 수정할 수 있습니다.</li>
      <li>메뉴 삭제 : 마스터, 매니저, 가게 사장이 메뉴를 삭제할 수 있습니다.</li>
    </ul>
  </div>
</details>

<details markdown="1">
  <summary>음식 - 메뉴옵션</summary>
  <div>
    <ul>
      <li>메뉴 옵션 생성 : 마스터, 매니저, 가게 사장이 메뉴의 옵션을 생성할 수 있습니다.</li>
      <li>메뉴 옵션 전체 조회 : 모든 사용자가 메뉴의 옵션을 조회할 수 있습니다.</li>
      <li>메뉴 옵션 수정 : 마스터, 매니저, 가게 사장이 메뉴의 옵션을 수정할 수 있습니다.</li>
      <li>메뉴 옵션 삭제 : 마스터, 매니저, 가게 사장이 메뉴의 옵션을 삭제할 수 있습니다.</li>
    </ul>
  </div>
</details>

<details markdown="1">
  <summary>주문</summary>
  <div>
    <ul>
      <li>주문 생성 : 사용자나 가게 사장은 새 주문을 만들 수 있습니다.</li>
      <li>주문 조회 : 주문 ID를 통해 해당 주문 단건에 대한 상세 정보를 조회 할 수 있습니다.</li>
      <li>주문 수정 : 주문의 총금액과 가게/배달원에게 요청사항을 수정 할 수 있습니다.</li>
      <li>주문 취소 : 관리자, 식당 주인, 사용자는 주문을 취소 할 수 있습니다.</li>
      <li>주문 상태변경 : 가게 사장은 주문을 수락/조리중, 배달원은 배달중/배달완료 상태로 변경 할 수 있습니다.</li>
      <li>주문 목록 조회 : 관리자, 사용자, 가게 사장 주문 목록을 10,20,30개씩 날짜 내림/오름차순으로 조회 할 수 있습니다.</li>
      <li>주문 삭제 : 마스터는 주문을 삭제 할 수 있습니다.</li>
    </ul>
  </div>
</details>

<details markdown="1">
  <summary>결제</summary>
  <div>
    <ul>
      <li>결제 요청 : 사용자나 가게 사장은 네이버 페이 결제 요청을 할 수 있습니다.</li>
      <li>결제 내역 생성 : 결제 요청 후 결제 내역을 생성합니다.</li>
      <li>결제 상태 변경 : 결제 상태를 변경 할 수 있습니다.</li>
      <li>결제 삭제 : 마스터는 결제를 삭제 할 수 있습니다.</li>
    </ul>
  </div>
</details>

<details markdown="1">
  <summary>리뷰</summary>
  <div>
    <ul>
      <li>리뷰 전체 조회 및 검색 : 리뷰 검색 및 전체 조회를 할 수 있습니다.</li>
      <li>리뷰 단건 조회 : 리뷰 단건 조회를 할 수 있습니다.</li>
      <li>리뷰 생성 : 주문을 한 고객은 리뷰를 작성할 수 있습니다. </li>
      <li>리뷰 수정 : 마스터, 매니저, 고객(본인 리뷰)은 리뷰를 수정할 수 있습니다.</li>
      <li>리뷰 삭제 : 마스터, 매니저, 고객(본인 리뷰)은 리뷰를 삭제할 수 있습니다.</li>
    </ul>
  </div>
</details>

------------

### 👩‍💻 팀원역할
- [팀장] 윤 관 : 음식, 발표
- [팀원] 박보현: 주문, 결제
- [팀원] 정균민: 사용자, CI/CD
- [팀원] 이수연: 식당, 리뷰

------------
### 🖥️ 서비스 구성 및 실행 방법

#### 🗝️ API Key 발급 받는 방법

#### 결제 API (Naver Pay)

프로젝트에서는 네이버페이 개발 연동 체험에서 제공하는 샌드백스 환경을 활용하고 있습니다.
[네이버페이 개발자센터](https://developers.pay.naver.com/docs/v2/api)에 접속해서 부여 받는 API Key를 설정하고 체험용 포인트를 사용해주세요.
- API 공통 가이드 → 체험하기 → 내 인증값 확인하기 → 샌드박스 환경
- 네이버페이 샌드박스 가맹점의 인증키를 애플리케이션 설정에 추가
  
		- pay.naver.merchant-key : {가맹점 Id}
		- pay.naver.client-id : {Client Id}
		- pay.naver.client-secret : {Client Secret}
		- pay.naver.chain-id : {Chain Id}
- API 공통 가이드 → 체험하기 → 테스트용 표인트 받기에서 결제 요청시 받는 url에서 결제 할 수 있는 샌드박스 포인트를 요청

#### AI API(Gemini)

- [구글 AI 스튜디오](https://aistudio.google.com/apikey)에 접속하여 API Key를 발급 받아야합니다.
- Get API Key(왼쪽 상단) → 키 API 키 만들기(화면 중단)
  
		- gemini.api-url : "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" (API 사용 End-Point)
		- gemini.api-key : 발급 받은 API Key
- yml 파일에 넣어 사용

#### application.yml 예시
```
spring:
  datasource:
    url:
      jdbc:postgresql://localhost:5432/{Database명}
    username: {사용자ID}
    password: {사용자비밀번호}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    
# Naver Pay
pay:
  naver:
    merchant-key : {가맹점 Id}
    client-id : {Client Id}
    client-secret : {Client Secret}
    chain-id : {Chain Id}
    api-domain: https://dev-pub.apis.naver.com/
    return-url: https://test-m.pay.naver.com/z/payments/
    partner-id: naverpay-partner
		
# Gemini AI
gemini
	api-url : https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=  {API 사용 End-Point}
	api-key : {발급 받은 API Key}
	
# JWT
jwt:
  secret: {임의의 텍스트 Base64 인코딩}
  access:
    exp: 1209600 # 14days(2weeks)
  rfresh:
    exp: 2592000 # 30days(1month)
```

------------
### 🔧 기술 스택
<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Java-007396?&style=flat&logo=java&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?&style=flat&logo=springboot&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/ApachetTomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=white"style="margin-right: 10px;"/>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=Postgresql&logoColor=white" style="margin-right: 10px;"/>
  
  <img src="https://img.shields.io/badge/JPA-6DB33F?style=flat&logo=&logoColor=white" style="margin-right: 10px;"/>
  <img src="https://img.shields.io/badge/QueryDSL-6DB33F?style=flat&logo=&logoColor=white" style="margin-right: 10px;"/>
</div>
  
<div style="display: flex; justify-content: center;">
    <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat&logo=amazonrds&logoColor=white" style="margin-right: 10px;"/>
    <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat&logo=amazonec2&logoColor=white" style="margin-right: 10px;"/>
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/IntelliJ Idea-000000?style=flat&logo=intellijidea&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Slack-4A154B?style=flat&logo=slack&logoColor=white" style="margin-right: 10px;">
</div>

------------
### 👨‍💻 아키텍처
![architecture_v1 0](https://github.com/user-attachments/assets/0469db11-0232-4ba1-8752-9c6c47f9a1bc)


### 📃 ERD 테이블
[ERD 테이블](https://www.erdcloud.com/d/YAeKeeCFY7MhbHR8W)
<details>
<summary>ERD</summary>
<p align="center"><img src="https://github.com/user-attachments/assets/11128912-f074-47f5-ae8e-640338bd1b3e" width="800"/></p>
</details>

<br>

### 📜 API 명세서
[API 명세서](https://documenter.getpostman.com/view/42568083/2sAYdeLX9c)

------------

![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=footer)
