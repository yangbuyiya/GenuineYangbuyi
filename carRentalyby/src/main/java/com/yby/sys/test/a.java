package com.yby.sys.test;

import com.yby.sys.utils.Desensitization;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class a {
    @Desensitization
    String phone;
}