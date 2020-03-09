package cn.youhuang.constructor;

/**
 * @author iaoao
 * @date 2020/3/5 18:02
 * @description
 */
public class ComputerBuilder {
    private Computer computer = new Computer();

    public void installDisplay(String display){
        computer.setDisplay(display);
    }


    public void installMainUnit(String mainUnit){
        computer.setMainUnit(mainUnit);
    }

    public void installMouse(String mouse){
        computer.setMouse(mouse);
    }

    public void installKeyboard(String keyboard){
        computer.setKeyboard(keyboard);
    }

    public Computer getComputer(){
        return computer;
    }
}
