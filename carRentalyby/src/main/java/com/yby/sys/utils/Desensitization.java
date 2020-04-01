package com.yby.sys.utils;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description: 数据脱敏
 * 1、默认不传部位、不传显示*号数量时字段全部脱敏
 * <p>
 * 原始字符串 adminis 总长度从0计算 总数6
 * index=(0,2) size = 1 下标即从0到2以内的字符标注“ * ”，size=1 则只填充一个* size 不能超过截取字符
 * index=(2,3) size =2 下标即从2到3以内的字符标注“ * ”，size=2 则只填充二个* size 不能超过截取字符
 * <p>
 * date: 2020/3/13 15:56
 *
 * @author oakdog
 * @version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = Desensitization.ConvertDesensitization.class)
public @interface Desensitization {

    /**
     * 传入的下标索引
     * 规则 第一位起始下标 第二位是结束下标 默认值6位下标
     **/
    int[] index() default {0, 6};

    /**
     * 需要脱敏的字符长度
     * 规则 输入 3 ：则根据index下标索引对应脱敏3个字符 默认6个长度脱敏
     **/
    int size() default 6;

    class ConvertDesensitization extends StdSerializer<Object> implements ContextualSerializer {

        private int[] index;

        private int size;

        public ConvertDesensitization() {
            super(Object.class);
        }

        private ConvertDesensitization(int[] index, int size) {
            super(Object.class);
            this.size = size;
            this.index = index;
        }

        @Override
        public void serialize(Object value, JsonGenerator jgen,
                              SerializerProvider provider) throws IOException {
            char[] str = value.toString().toCharArray();
            StringBuilder builder = new StringBuilder();
            String char1 = (String) value;
            System.out.println(char1);
            if (str.length > 0) {
                //字符长度超长处理
                if (index[0] < str.length && index[1] < str.length) {
                    //使用默认初始值的脱敏处理
                    if (index[0] == 0) {
                        //如果输入脱敏大小长度小于0或大于原始脱敏字符长度，则全脱敏字符
                        if (size < 0 || size < str.length) {
                            char[] charStr = char1.substring(index[1], str.length).toCharArray();
                            char[] charStr1 = char1.substring(index[0], index[1]).toCharArray();
                            builder.append(charStr1);
                            for (int i = 0; i < charStr.length; i++) {
                                if (size > i) {
                                    builder.append("*");
                                } else {
                                    builder.append(charStr[i]);
                                }
                            }
                        } else {
                            builder.append(getDefaultChar((String) value, "left"));
                        }
                    } else {
                        //从中间位置截取脱敏处理
                        //如果输入脱敏大小长度小于0或大于原始脱敏字符长度，则全脱敏字符
                        if (size < 0 || size < str.length) {
                            char[] charStr = char1.substring(index[0], str.length - index[1] + 1).toCharArray(); //2 6-4 2 //中间截取部分
                            List<Integer> prefix = getPrefix(index[0], (String) value);
                            //List<Integer> suffix = getSuffix(index[0],index[1], (String) value);
                            for (Integer integer : prefix) {
                                builder.append(str[integer]);
                            }
                            for (int i = 0; i < charStr.length; i++) {
                                if (size > i) {
                                    builder.append("*");
                                } else {
                                    builder.append(charStr[i]);
                                }
                            }
                            char[] chars = Arrays.copyOfRange(str, index[1], str.length);
                            builder.append(String.valueOf(chars));
                        } else {
                            builder.append(getDefaultChar((String) value, "right"));
                        }
                    }
                } else {
                    //默认处理
                    builder.append(getDefaultChar((String) value, ""));
                }
            }
            jgen.writeString(builder.toString());
        }

        /**
         * 默认的填充方式
         *
         * @param str      原始字符串
         * @param position 位置
         * @return
         */
        String getDefaultChar(String str, String position) {
            char[] desensitizationStr = str.toCharArray();
            for (int i = 0; i < desensitizationStr.length; i++) {
                if ("left".equals(position)) {
                    if (i != 0) {
                        desensitizationStr[i] = '*';
                    }
                } else if ("right".equals(position)) {
                    if (i != desensitizationStr.length - 1) {
                        desensitizationStr[i] = '*';
                    }
                } else {
                    if (i != 0 && i != desensitizationStr.length - 1) {
                        desensitizationStr[i] = '*';
                    }
                }
            }
            return String.valueOf(desensitizationStr);
        }

        /**
         * 获取字符前缀下标
         *
         * @param index 下标
         * @param val   原始字符串
         * @return
         */
        List<Integer> getPrefix(int index, String val) {
            //int[] chars = {};
            List<Integer> listIndex = new ArrayList<>();
            for (int i = 0; i < val.length(); i++) {
                if (i != index) { //0 1 != 2
                    listIndex.add(i);
                    continue;
                }
                break;
            }
            return listIndex;
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            int[] index = {0, 6}; //初始值
            int size = 6; //初始值
            Desensitization ann = null;
            if (property != null) {
                ann = property.getAnnotation(Desensitization.class);
            }
            if (ann != null) {
                index = ann.index();
                size = ann.size();
            }
            return new Desensitization.ConvertDesensitization(index, size);
        }
    }
}
