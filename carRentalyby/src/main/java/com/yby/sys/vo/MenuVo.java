package com.yby.sys.vo;

import com.yby.sys.domain.Menu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuVo extends Menu {
    /* 分页参数 */
    private Integer page;
    private Integer limit;

}