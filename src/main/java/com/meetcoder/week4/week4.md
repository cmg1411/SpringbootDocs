# Spring Application

## Lazy Initialize
 - 빈을 에플리케이션 시작 당시가 아닌 필요한 시점에 생성하도록 지연시키는 방법.
 - 예를 들어, Web 프로그램에서는 요청이 들어오기 전에는 많은 Bean 을 생성하지 않고 있을 수 있다.
 - 장점
    - 에플리케이션의 시작 시간을 단축시킬 수 있다.
 - 단점
    - 에플리케이션의 에러 발견을 시작시점에 찾을 수 없다.
    - 또한 Bean 이 생성될 수 있는 메모리를 사전에 확인해야 한다.
        - 그렇지 않으면 프로그램 런타임에 메모리부족 에러가 뜰 수 있다.
        
 ### 지연 초기화 방법
 1. 속성 파일에 지정하여 전체 에플리케이션에 지정
     ```yaml
    // application.properties
    spring.main.lazy-initialization=true
    
    // yaml
    spring:
      main:
        lazy-initialization: true
    ```
    - 모두 지연초기화 후 지연초기화 하고 싶지 않은 빈에는 명시적으로 `@Lazy(false)` 를 붙인다.
    
 1. 특정 Bean 을 지연 초기화 하는 방법
    - 해당 빈 등록부분에 `@Lazy` 에너테이션을 붙인다.
    
    
 <br>
 
 
 # 배너 바꾸기 !
 
 1. 배너 끄기
     ```java
    public static void main (String[] args) {
        SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    ```
    
 1. 링크에 있는 이미지로 바꾸기
    ```java
    public static void main (String[] args) throws MalformedURLException {
    	SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
    	UrlResource r = new UrlResource("http://pngimg.com/uploads/google/google_PNG102346.png");
    	app.setBanner(new ImageBanner(r));
    	app.run(args);
    }
    ```
 1. resource/ 경로에 banner 라는 이름으로 저장 (txt, png, jpg, gif 아무거나 가능)
    - txt 파일의 경우 스프링 부트의 버전정보등을 변수로 사용가능
        - [여기서 확인](https://docs.spring.io/spring-boot/docs/2.4.3/reference/html/spring-boot-features.html#boot-features-banner)
     ```java
     // /resources/banner.txt 에 원하는 배너 저장
     public static void main (String[] args) throws MalformedURLException {
         SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
         app.setLogStartupInfo(false);
         app.run(args);
     }
    ```
    
 1. 코드상으로 조작하기
    ```java
        public static void main (String[] args) throws MalformedURLException {
            SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
            app.setLogStartupInfo(false);
            app.setBanner(new Banner() {
                @Override
                public void printBanner (Environment environment,
                                         Class<?> sourceClass,
                                         PrintStream out) {
                    out.println("--- my custom banner ---");
                }
            });
            app.run(args);
        }
    ```


<br>

# 스프링 부트 설정이 별로라면 ?
 1. SpringApplication 의 run() 를 정적으로 실행하지 말고, 객체를 생성하여 여러 설정을 할 수 있다.
    ```java
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MySpringConfiguration.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    ```
    - 전체 메서드는 [javadoc](https://docs.spring.io/spring-boot/docs/2.4.3/api/org/springframework/boot/SpringApplication.html) 에서 확인 가능.
    
  1. application.properties 설정으로도 가능하다.
  
  
<br>

# Fluent Builder API
 - 계층적인 SpringContext 를 구성할 수 있게 해준다.
 - 컨텍스트 계층 구조를 사용하면 여러 하위 컨텍스트가 상위 컨텍스트에있는 Bean 을 공유 할 수 있다.
    - 각 하위 컨텍스트는 상위 컨텍스트에서 상속 된 구성을 재정의 할 수 있습니다.
   
 - 컨텍스트를 사용하여 한 컨텍스트에 등록 된 빈이 다른 컨텍스트에 액세스하지 못하도록 제한할 수도 있다. 
    - 이것은 모듈의 생성을 낮은 결합도로 할 수 있게 해준다.
   
 - 마치 상속과 같아서, 부모 컨텍스트는 하나여야 하지만 자식 컨텍스트는 여러개여도 된다.
 - 또한, 자식에서 부모 컨텍스트의 빈에 접근할 수 있지만, 부모 컨텍스트에서도 자식 컨텍스트로 접근가능하다.
 
## 어떻게 ?
 - SpringApplicationBuilder 객체를 생성하여 빌더패턴으로 애플리케이션을 구동하는 방법으로 가능하다.
 ```java
new SpringApplicationBuilder()
        .sources(AdditionalSource.class)
        .parent(Parent.class)
        .child(Application.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
```
  
  - [벨덩의 예제](https://www.baeldung.com/spring-boot-context-hierarchy)
  
  
  
<br>

# 이벤트 리스너
 - [이벤트 리스너 개념잡기](https://shinsunyoung.tistory.com/88)
 - 주의할 점은, 이벤트 리스너는 멀티 스레드로 동작하는게 아니라 동일한 스레드에서 동작하기 때문에, 긴 작업을 할당하면 안된다.
 - @SpirngApplicaiton 은 몇가지 이벤트를 발생시킨다.
 - 몇가지 이벤트는 ApplicationContext 이전에 발생하기 때문에, @Bean 으로 등록할 수 없다.
    - 그런 빈들은 `SpringApplication.addListeners(..)`나 `SpringApplicationBuilder.listeners(...)` 로 등록가능하다.
    - 또는 에플리케이션의 동작과 관계없이 `META-INF/spring.factories` 파일을 생성하고, `org.springframework.context.ApplicationListener` 를 키값으로 리스너의 FQCN 을 값으로 넣는다.
    
## 이벤트의 동작 순서
 1. `ApplicationStartingEvent` : listener 와 initializer 의 등록을 제외하고, 실행 시작 직후 아무것도 하기 전에 발생하는 이벤트
 
 1. `ApplicationEnvironmentPreparedEvent` : `Environment` 가 컨텍스트에 사용될 것을 알고 있지만 컨텍스트가 아직 생성되기전 발생하는 이벤트
 
 1. `ApplicationContextInitializedEvent` : `ApplicationContext` 가 준비되고 ApplicationContextInitializers 가 호출되었지만 Bean 정의가 로드되기전 발생하는 이벤트
 
 1. `ApplicationPreparedEvent` : 정의된 빈들이 로딩되고 refresh 가 시작되기 바로전에 전송
 
 1. `ApplicationStartedEvent` : refreshed 후, 아직 아무 에플리케이션이나 command line runner 가 호출되기전에 전송
 
 1. `AvailabilityChangeEvent` : `LivenessState.CORRECT` 직후에 전송. 에플리케이션이 살아 있음을 나타냄.
 
 1. `ApplicationReadyEvent` : application, command line runner 가 호출된 이후 전송.
 
 1. `AvailabilityChangeEvent` : `ReadinessState.ACCEPTING_TRAFFIC` 직후에 전송. 프로그램이 service request 에 대한 준비가되어 있음을 나타냄.
 
 1. `ApplicationFailedEvent` : 예외가 발생했을 때 전송.
  
 
## 이벤트 발생과 계층관계
 - 해당 이벤트가 발생한 컨텍스트가 부모 컨텍스트인지 자식 컨텍스트인지 구분해야할 상황이 생길 수 있다.
 - 컨텍스트를 @Autowired 로 주입받아 확인해야한다.
 
 
 
 <br>
 <br>
 
 
 
 # 웹 환경
 웹 환경에서 ApplicationContext 는 다음과 같은 알고리즘을 사용하여 만들어진다.
 
  1. Spring MVC 가 있다면, `AnnotationConfigServletWebServerApplicationContext` 로 생성된다.
  1. Spring MVC 가 없고 Spring WebFlux 가 있다, `AnnotationConfigReactiveWebServerApplicationContext` 로 생성된다.
  1. 위의 경우가 다 아니면, `AnnotationConfigApplicationContext` 가 생성된다.
  
 - 위의 알고리즘에 따르면, Spring MVC 와 Spring WebFlux 가 모두 존재하면 MVC 에 맞추게 된다.
 - `setWebApplicationType(WebApplicationType)` 를 오버라이드하여 쉽게 바꿀 수 있다.
 - `setWebApplicationType(WebApplicationType.NONE)` 는 JUnit 으로 테스트할때 종종 쓰인다.
 
 
 <br>
 <br>


# Application Arguments 접근하기
 - SpringApplication.run(…) 에 전달되는 인자에 접근하고 싶으면, `org.springframework.boot.ApplicationArguments` 을 사용하면 된다.
 - `ApplicationArguments` 인터페이스는 `String[]`을 정제한 option, non-option 에 접근할 수 있는 방법을 제공한다.
 
```java 
import org.springframework.boot.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*

@Component
public class MyBean {

	@Autowired
	public MyBean(ApplicationArguments args) {
		boolean debug = args.containsOption("debug");
		List<String> files = args.getNonOptionArgs();
		// if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
	}
}
```

 - 스프링 부트는 CommandLinePropertySource 를 스프링 Environment 에 등록할 수있다. 그렇게 하면 @Value 어노테이션을 사용해 하나의 어플리케이션 인수들을 주입할 수있다.
 
 <br>
 <br>
 
# CommandLineRunner, ApplicationRunner
 - 만약 SpringApplication 이 시작할 때 특정 코드를 한번 실행 시킬 필요가 있으면 ApplicationRunner 나 CommandLineRunner 인터페이스를 implements 할 수 있다.
 - 두 인터페이스는 같은 방식으로 작동하며 SpringApplication.run(...) 메소드 실행이 완료되기 직전에 호출 되는 run 메소드를 하나 제공 한다.
 - CommandLineRunner 인테페이스는 어플리케이션 인수를 한개의 스트링 배열로 접근해 사용 하는 방법을 제공 하지만 ApplicationRunner 는 이전에 우리가 봤던 ApplicationArguments 인터페이스를 사용한다.
 
```java
import org.springframework.boot.*
import org.springframework.stereotype.* 
@Component 
public class MyBean implements CommandLineRunner { 
   	public void run(String... args) { 		
       // Do something... 	
   }
}
```

 - CommandLineRunner나 ApplicationRunner빈들을 여러개 정의 하고 그들의 실행 순서를 정의 해야 하는 경우 추가적으로 org.springframework.core.Ordered 인터페이스를 구현하거나 org.springframework.core.annotation.Order 어노테이션을 사용할 수 있다.
 
 
 <br>
 <br>
 
 # Application exit
  - Spring boot 는 안전하게 ApplicationContext 을 종료하기 위해 shutdown hook 을 JVM 에 등록한다.
  - 모든 스프링 생명주기 콜백 (DisposableBean, @PreDestroy 등) 은 이를 사용할 수 있다.
  - 또한, `org.springframework.boot.ExitCodeGenerator` 인터페이스를 구현하면, `SpringApplication.exit()` 이 호출될 때 특정한 종료코드를 반환하도록 할 수 있다.
    ```java
     @SpringBootApplication
     public class ExitCodeApplication {
     
         @Bean
         public ExitCodeGenerator exitCodeGenerator() {
             return () -> 42;
         }
     
         public static void main(String[] args) {
             System.exit(SpringApplication.exit(SpringApplication.run(ExitCodeApplication.class, args)));
         }
     
     }
    ```     
  - 또한, `ExitCodeGenerator` 의 `getExitCode()` 메서드를 정의하여 특정 예외시 발생하도록 구현할 수도 있다.
  
  
# 관리자 기능
 - `spring.application.admin.enabled` 프라퍼티를 사용하면 어플리케이션에 관리자-관련 기능을 활성화 시킬 수 있다.
 - MBeanServer 플랫폼에 SpringApplicationAdminMXBean 을 노출시킨다.
 - 이것을 이용해 스프링 부트 어플리케이션의 관리자 기능을 원격으로 사용할 수있다. 이 기능은 서비스 어떤 랩퍼 구현에도 유용할 수있다.
  
  만약 어플리케이션이 실행 중인 HTTP 포트번호를 알려면, local.server.port 키로 프라퍼티를 조회한다.
  
  주의 : 이 기능을 활성화 시킬때 MBean 이 어플리케이션을 종료 Shutdown 하는 메소드를 노출하므로 주의해야 한다.
  
  
<br>
<br>

# Application startup tracking
 - 아래와 같이 ApplicationStartup 를 사용하면 에플리케이션의 시작시 SpringApplication, ApplicationContext 등을 추적할 수 있게 해준다.
 - 이는 profiling 이나 단순코드이해 목적으로 사용될 수 있다.
 
 ```java
 public static void main(String[] args) {
     SpringApplication app = new SpringApplication(MySpringConfiguration.class);
     app.setApplicationStartup(new BufferingApplicationStartup(2048));
     app.run(args);
 }
```