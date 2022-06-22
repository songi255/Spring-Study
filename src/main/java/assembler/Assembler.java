package assembler;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

// Main 함수에서 의존객체들을 조립하는게 아니고, Assembler 클래스에서 조립해서 제공하는 방식으로 사용할 수 있다.
// 필요한 클래스에서는 Assembler를 생성하고 get으로 필요한 객체를 얻어서 사용한다.
// 하지만 Spring 이 기본적으로 조립기 기능을 제공하므로, 사용할 필요는 없다
public class Assembler {
    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;

    public Assembler() {
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao);
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao);
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public MemberRegisterService getRegSvc() {
        return regSvc;
    }

    public ChangePasswordService getPwdSvc() {
        return pwdSvc;
    }
}
