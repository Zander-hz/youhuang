package cn.youhuang.dynamicproxy;

/**
 * @author iaoao
 * @date 2020/3/5 18:57
 * @description
 */
public class ProxyTest {
    public static void main(String[] args) {
        System.out.println("不使用代理类，调用doSometing方法");
        Person person = new Ace();
        person.doSomething();

        System.out.println("~~~~~~~~~~~~~~~~~~~~");

        System.out.println("使用代理类，调用doSometing方法");
        Person proxy = (Person) new JDKDynamicProxy(new Ace()).getTarget();
        proxy.doSomething();
    }
}
