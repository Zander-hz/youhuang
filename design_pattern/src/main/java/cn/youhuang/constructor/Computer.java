package cn.youhuang.constructor;

/**
 * @author iaoao
 * @date 2020/3/5 17:59
 * @description 构建者模式
 */
public class  Computer{
    //显示器
    private String display;
    //主机
    private String mainUnit;
    //鼠标
    private String mouse;
    //键盘
    private String keyboard;

    @Override
    public String toString() {
        return "Computer{" +
                "displayer='" + display + '\'' +
                ", mainUnit='" + mainUnit + '\'' +
                ", mouse='" + mouse + '\'' +
                ", keyboard='" + keyboard + '\'' +
                '}';
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMainUnit() {
        return mainUnit;
    }

    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }
}
