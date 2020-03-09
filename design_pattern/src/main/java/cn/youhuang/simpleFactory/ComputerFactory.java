package cn.youhuang.simpleFactory;

/**
 * @author iaoao
 * @date 2020/3/5 18:22
 * @description
 */
public class ComputerFactory {
    public static Computer createComputer(String type){
        Computer computer = null ;
        switch (type){
            case "lenovo":
                computer = new LenovoComputer();
                break;
            case  "hp":
                computer = new HpComputer();
                break;
        }
        return computer;
    }
}
