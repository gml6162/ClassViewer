package viewerPackage;

import java.util.ArrayList;

/**
 * Created by gej48 on 2017-05-20.
 */
public class MethodArgumentInfo {

    //FIELDS
    private ArrayList<String> paraNames = new ArrayList<>(); //매개변수의 이름
    private ArrayList<String> paraTypes = new ArrayList<>(); //매개변수의 타입
    private ArrayList<Object> arguments = new ArrayList<>(); //실제 매개변수에 저장된 입력값(인자)

    //CONSTRUCTOR
    MethodArgumentInfo() { }

    //METHODS
    public void setParameterInfo(String paraName, String paraType) {
        paraNames.add(paraName);
        paraTypes.add(paraType);
    }

    public void setArgument(Object argument) { arguments.add(argument); }

    public ArrayList<String> getParameterName() { return paraNames; }
    public ArrayList<String> getParameterType() { return paraTypes; }
    public ArrayList<Object> getArgument() { return arguments; }

}
