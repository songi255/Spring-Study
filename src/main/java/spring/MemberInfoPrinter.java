package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {
    // setter를 호출하지 않아도 자동바인딩된다.
    @Autowired
    private MemberDao memDao;
    //@Autowired
    private MemberPrinter printer;

    public void printMemberInfo(String email){
        Member member = memDao.selectByEmail(email);
        if (member == null){
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }

    // 메서드에 의존 자동주입을 한다.
    // 클래스가 생성되면, setter가 자동 호출되는데, 이때 파라미터로 Spring이 자동으로 Bean을 주입해준다.
    @Autowired
    public void setMemberDao(MemberDao memberDao){
        this.memDao = memberDao;
    }

    @Autowired
    @Qualifier("printer")
    public void setPrinter(MemberPrinter printer){
        this.printer = printer;
    }
}
