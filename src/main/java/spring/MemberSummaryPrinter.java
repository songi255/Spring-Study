package spring;

public class MemberSummaryPrinter extends MemberPrinter{
    //상속받은 객체는, 조상의 의존자동주입에 사용될 수 있으므로, Qualifier로 한정해줘야한다.
    @Override
    public void print(Member member) {
        System.out.printf("회원 정보: 이메일=%s, 이름=%s\n", member.getEmail(), member.getName());
    }
}
