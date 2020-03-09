package cn.youhuang.simpleFactory;

/**
 * @author iaoao
 * @date 2020/3/5 18:27
 * @description
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        Computer computer1 = ComputerFactory.createComputer("hp");
        computer1.start();
        Computer computer2 = ComputerFactory.createComputer("lenovo");
        computer2.start();

    }
}
