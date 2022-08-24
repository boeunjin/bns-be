# LG Uplus Quick Launcher
<img src = "https://user-images.githubusercontent.com/101620963/176378568-02d151c0-5b6e-43ea-9322-ff25d6ce955d.svg" width="100" height="100">  <img src = "https://user-images.githubusercontent.com/101620963/176588353-aa686a9d-bebd-4720-898c-b9bcd1a34c99.png" width="150" height="100">


# Repo 주의사항
Java Spring 프레임워크 배포를 위한 CI/CD 관련 파일 및 설정은 아래와 같습니다.

### Service Listening Port 설정 (✅ 필수)
- 현재 기본적으로 Build 시 서비스 포트가 **5000번**으로 되어있습니다. 코드 내 Listening port를 **5000**으로 변경 후 배포 바랍니다.

### .github/workflows/cicd.yml (🚫 삭제 및 변경 금지)
- CI/CD 파이프라인을 위한 파일이며 수정 및 삭제할 경우 정상 배포되지 않을 수 있습니다.

### waypoint.hcl (🚫 삭제 및 변경 금지)
- Quick Launcher 서버에 정상적으로 Build 및 배포하기 위해 필요한 필수 파일입니다.

### build.gradle (🛠 추가 필요)
- 소스코드 새로 업로드 시 build.gradle 안에
```
tasks.jar {
	enabled = false
}
```
추가해야 합니다.

# 시작하기

### 공식 문서


* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)

### 가이드


* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### 기타 참고 사항


* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

