package org.ingue.mall.posting.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
//access level , AllArgs의 create 를 이용 , NoArgs 같은 것에 접근제한자 설정 가능, Builder는 필드가 4개 이상일때(많을때) 고려 study feedback
//@Singular
public class PostingDto {

    @NotBlank
    private String postingTitle;
    @NotNull
    private String postingContent;
}
