package com.example.annotation.domain.VO;

import com.example.annotation.annotation.Query;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class UserVO {
    @Query()
    private Integer useId;

    @Query(type = Query.Type.INNER_LIKE)
    private String useName;

    @Query(type = Query.Type.IN)
    private List<String> useSex;

    private Integer useAge;

    private String useIdNo;

    @Query(type = Query.Type.ORDER_BY_DASC)
    private String[] orderDesc;

    private String useEmail;

    private LocalDate createTime;

    private LocalDate modifyTime;

    private String useState;
}