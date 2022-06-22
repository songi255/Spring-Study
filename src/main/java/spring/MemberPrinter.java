package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MemberPrinter {
//    @Autowired
//    @Nullable
    // 이렇게 필드 자동주입에도 Nullable을 사용할 수 있다.
    private DateTimeFormatter dateTimeFormatter;
    //private Optional<DateTimeFormatter> formatterOpt; 마찬가지로 사용할 수 있다.

    public void print(Member member){
        if (dateTimeFormatter == null){
            System.out.printf("회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
                    member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime());
        }else{
            System.out.printf("회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
                    member.getId(), member.getEmail(), member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
        }
    }

    @Autowired(required = false) // 이와 같이, 의존주입이 필수는 아닌경우, false로 지정해주면 오류가 나지 않는다.
    // null 이면 메서드 자체가 호출되지 않는다.
    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    // 혹은 아래와 같이, Optional로 주입할 수도 있다.(Spring 5, Java 8)
    @Autowired
    public void setDateTimeFormatter(Optional<DateTimeFormatter> formatterOpt) {
        if (formatterOpt.isPresent()){
            this.dateTimeFormatter = formatterOpt.get();
        }else{
            this.dateTimeFormatter = null;
        }
    }

    /* 혹은, 아래와 같이 매개변수에 @Nullable 을 적용할 수 있다.
        null 이어도 호출된다
    @Autowired
    public void setDateTimeFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
     */
}
