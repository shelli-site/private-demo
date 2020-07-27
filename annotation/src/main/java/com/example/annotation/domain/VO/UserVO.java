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

    @Query(blurry = "use_name,use_id")
    private String search;

    @Query(type = Query.Type.ORDER_BY_DESC)
    private String[] orderDesc;

    @Query(type = Query.Type.BETWEEN)
    private List<LocalDate> createTime;

}