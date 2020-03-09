package cn.youhuang.constructor;

import com.sun.javaws.IconUtil;

/**
 * @author iaoao
 * @date 2020/3/5 18:09
 * @description
 */
public class ConstructorTest {
    public static void main(String[] args) {
        ComputerBuilder computerBuilder = new ComputerBuilder();

        computerBuilder.installDisplay("显示器");
        computerBuilder.installMainUnit("主机");
        computerBuilder.installMouse("鼠标");
        computerBuilder.installKeyboard("键盘");

        Computer computer = computerBuilder.getComputer();
        System.out.println(computer);
    }
}
