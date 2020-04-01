package com.yby.sys.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author TeouBle
 */
@Setter
@Getter
@ToString
public class Permission {
    private Long pid;

    private String pname;

    private String presource;

}