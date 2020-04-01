package com.yby.sys.test;

import cn.hutool.extra.mail.MailUtil;

public class send {
    public static void main(String[] args) {
        MailUtil.send("1692700664@qq.com", "测试", "邮件来自Hutool测试", false);
    }
}