package cn.youhuang.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author iaoao
 * @date 2020/3/4 21:09
 * @description
 */
//此注释表明此插件类，通过类名，方法名，方法参数类，确定拦截的是哪个类的哪个方法。可配置多个@Signature，同时拦截多个对象
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class,Integer.class})
})
public class MyPlugin implements Interceptor {
    /*
    * 拦截方法：只要被拦截的目标对象的目标方法被执行时，每次都会执行次intercept方法
    * */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法进行了增强。。。");
        return invocation.proceed();//增强后原方法执行
    }

    /*
    * 主要为了把当前的拦截器生成代理，存到拦截器链中
    * */
    @Override
    public Object plugin(Object target) {
        Object wrap = Plugin.wrap(target, this);//this表示当前这个类
        return wrap;
    }

    /*
    * 获取配置文件的参数
    * */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("获取到的配置文件的参数是："+properties);
    }
}
