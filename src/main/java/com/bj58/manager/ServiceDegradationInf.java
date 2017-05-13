package com.bj58.manager;

/**
 * Created by fei.gao on 2017/5/12.
 * 服务降级定义 源数据：方法 降级级别粒度 方法
 */
public interface ServiceDegradationInf {

    /**
     * 打开降级  对方法进行降级标识
     * @param method
     * @return
     */
    public boolean openService(String method);


    /**
     * 关闭降级  对方法进行降级关闭标识
     * @param method
     * @return
     */
    public boolean closeService(String method);

    /**
     * 判断 方法是否为 降级状态
     * @param method
     * @return
     */
    public boolean isOpenService(String method);

}
