package com.hsamgle.basic.exception;

/**
 * @类功能: TODO
 * @文件名: ThreadPoolException
 * @所在包: com.com.dplus.project.exception
 * @开发者: 黄先国
 * @邮_件: hsamgle@qq.com
 * @时_间: 2017/10/16 11:50
 * @公_司: 广州讯动网络科技有限公司
 */
public class ThreadPoolException extends RuntimeException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ThreadPoolException ( String message ) {
        super(message);
    }
}
