package org.example.service.impl;

import org.example.service.IStringOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/10 20:20
 */
@Service
public class StringOperationImpl implements IStringOperation
{
    Logger logger =  LoggerFactory.getLogger(StringOperationImpl.class);
    @Override
    public String deleteConsecutiveCharactersMoreThen3(String inputStr)
    {
        if (!StringUtils.hasText(inputStr))
        {
           return "输入字符不能为空或空串";
        }
        String result = inputStr;
        boolean flag;

        do {
            int i = 0;
            flag = false;
            StringBuilder sb = new StringBuilder();
            while (i<result.length())
            {
                int count = 1;
                while (i+count<result.length() &&
                        result.charAt(i) == result.charAt(i+count))
                {
                    count++;
                }
                if (count < 3)
                {   //去掉重复超过三个计以上的字符
                    sb.append(result.substring(i,i+count));
                }else
                {
                    flag = true;
                }
                i +=count;  // 重新设置起始位置  如：asdddbjkkkef,去掉ddd后 i的位置就是b
            }
            result = sb.toString();
            if (flag)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("->{}", result);
                }
                //System.out.println("->"+ result);
            }
        } while (flag);
       return result;
    }

    @Override
    public String replaceConsecutiveByPreAlpha(String inputString) {

        if (!StringUtils.hasText(inputString))
        {
            return "输入字符不能为空或空串";
        }
        String result = inputString;
        boolean flag;

        do {
            int i = 0;
            flag = false;
            StringBuilder sb = new StringBuilder();

            while (i<result.length())
            {
                int count = 1;
                while (i+count<result.length() &&
                        result.charAt(i) == result.charAt(i+count))
                {
                    count++;
                }
                if (count >= 2)
                {
                    //获取当前字符的前一个字符
                    char replaceChar = (char)(result.charAt(i) -1);
                    sb.append(replaceChar);
                    flag = true;  //有重复连续的继续进行下一步检索
                }else
                {
                    sb.append(result,i,i+count);
                }
                i += count; //一步一步后移
            }
            result = sb.toString();
            if (flag){
                if (logger.isDebugEnabled())
                {
                    logger.debug("->{}", result);
                }
            }
        }while (flag);


        return result;
    }

}
