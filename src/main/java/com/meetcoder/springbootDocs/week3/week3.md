# Spring boot 의존성 주입

 1. @Configuration 을 이용한 의존성 주입
    - @Configuration 이 붙은 클래스는 CGLIB 기술로 바이트코드 조작이 된다.
    - 해당 클래스를 상속하는 클래스를 스프링부트에서 자동으로 동적으로 만든다.
    - 동적으로 만들어진 클래스는, 해당 클래스 안의 메서드 (빈으로 등록한 객체를 불러오는 메서드) 에서 등록된 빈들이 싱글톤이 되게 보장해준다.

 1. @ComponentScan 을 이용한 자동 의존성 주입
    - 프로젝트에서 @Component, @Service, @Controller, @Repository 가 붙은 클래스를 빈으로 등록한다.
    - 스캔할 위치를 지정할 수 있다.