package org.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/10 22:45
 */
@SpringBootTest
public class StringOperationTest {

    @Resource
    IStringOperation stringOperation;

    @Test
    public void function1Test()
    {
        String str = "sdgdfhhghhhhnnnbb";
        System.out.println("Input: " + str);
        System.out.println("Output:\t");
        String result = stringOperation.deleteConsecutiveCharactersMoreThen3(str);
        System.out.println("result:"+result);
    }

    @Test
    public void function2Test()
    {
        String str = "abcccbad";
        System.out.println("Input: " + str);
        System.out.println("Output:\t");
        String result = stringOperation.replaceConsecutiveByPreAlpha(str);
        System.out.println("result:"+result);
    }
}
