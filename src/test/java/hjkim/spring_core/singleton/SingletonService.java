package hjkim.spring_core.singleton;


public class SingletonService {

    // class Level: 딱 하나만 나오게 됨
    // 자기 참조값 생성해서 클래스 레벨에서 참조 대입
    private static final SingletonService instance = new SingletonService();

    // 객체 인스턴스가 필요하면 오직 getInstance 메서드를 통해서만 조회가 가능
    public static SingletonService getInstance() {
        return instance;
    }

    // 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막음
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
