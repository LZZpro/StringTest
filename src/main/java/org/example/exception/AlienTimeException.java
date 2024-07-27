package org.example.exception;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/27 18:30
 */
public final class AlienTimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    //错误信息
    public String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public AlienTimeException(){}

    public AlienTimeException(String message)
    {
        super(message);
    }
}
