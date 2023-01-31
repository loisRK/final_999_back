# 위치기반 실시간 채팅 웹 개발
<ul>
<li>✨ 프로젝트 명 : 999.com</li>
<li>🙎🏻‍♀️ 팀 장 : 김륜경(<a href="https://github.com/loisRK">loisRK</a>)</li>
<li>🙌🏻 팀 원 : 채근영(<a href="https://github.com/cgy0627">gy</a>), 
                   최민기(<a href="https://github.com/GRIN-96">Grin_:)</a>),
                   조혜원(<a href="https://github.com/hwch0">hwch0</a>),
                   이나현(<a href="https://github.com/illcnd921">nahyeon_eel</a>)
</li>
<li>📅 개발 기간 : 2022.11.22 ~ 2022.12.23 (4 weeks)</li>
<!-- 개발 일정표 이미지 -->
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/schedule.png" width="60%" />
</ul>

# INDEX
[1️⃣ 사용 기술](#1️-사용-기술) <br/>
[2️⃣ ERD 설계](#2️-ERD-설계) <br/>
[3️⃣ 주요 기능 및 화면 구성](#3-주요-기능-및-화면-구성) <br/>
[4️⃣ 트러블슈팅](#4-트러블슈팅)

## 1️. 사용 기술
<!-- 사용 기술 스택 -->
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/stack.png" width="60%"/>

## 2️. ERD 설계
- 연관관계를 통한 관리가 효율적인 데이터는 SQL을 사용한 관계형 데이터베이스 구조를 사용했습니다.
- 채팅과 관련한 실시간성 대용량 데이터는 추후 기능의 확장 및 트래픽 증가로 인한 데이터 증가 시 관리의 효율을 높이기 위해 NoSQL을 사용한 비관계형 데이터베이스 구조 사용했습니다.
<!-- ERD 다이어그램 -->
<div>
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/sql.png" width="45%"/>
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/nosql.png" width="45%"/>
</div>

## 3. 주요 기능 및 화면 구성
🔗[시연영상](https://www.youtube.com/watch?app=desktop&v=1uD3U-tUBIs)
### 주요 기능
- 실시간 위치기반 채팅 및 포스트의 Map View / List View
- 포스팅 게시판
- 마이페이지
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/mainmenu.png" width="70%"/>

### 화면 구성
- 모바일 기기에서도 접속 시 앱처럼 사용할 수 있도록 반응형 웹 컴포넌트 구성했습니다.
<img src="https://github.com/loisRK/md_source/blob/main/final_prj_src/mainpage.png" width="70%"/>

## 4. 트러블슈팅
### 주요 트러블슈팅
<details>
<summary>로그인 API 적용 이슈</summary>
<div markdown="1">

![image](https://user-images.githubusercontent.com/39821066/215045850-62f65580-5672-45c4-a6de-9a225bf2820b.png)

</div>
</details>

### 그 외 트러블슈팅 리스트
🔗<a href="https://docs.google.com/spreadsheets/d/1egXVOZWhBpeTN812BIy81WCy3DWp-L4Y2sEKtPqrb3E/edit?usp=share_link">에러 및 이슈 공유 스프레드시트</a>
