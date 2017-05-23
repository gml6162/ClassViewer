package viewerPackage;

import java.util.ArrayList;

/**
 * Created by gej48 on 2017-05-18.
 */
public class MethodInfo {

    //FIELDS
    private String name;
    private StringBuffer body = new StringBuffer();
    private MethodArgumentInfo mai = new MethodArgumentInfo();

    //CONSTRUCTOR
    MethodInfo() { }

    //METHODS
    public void setName(String name) { this.name = name; }

    public void setParameter(String paraName, String paraType) {
        mai.setParameterInfo(paraName, paraType);
    }

    public void setBody(String tmp) {
        body.append(tmp);
        body.append("\r\n");
    }

    public void modifyBody(String text) {
        body.setLength(0);
        body.append(text);
    }

    public String getName() { return name; }

    public ArrayList<String> getParameterName() {
        if(mai.getParameterName().isEmpty()) { return null; }
        return mai.getParameterName();
    }
    public ArrayList<String> getParameterType() {
        if(mai.getParameterType().isEmpty()) { return null; }
        return mai.getParameterType();
    }

    public ArrayList<Object> getArgument() {
        if(mai.getArgument().isEmpty()) { return null; }
        return mai.getArgument();
    }

    public String getBody() {
        if(body.length() == 0)
            return "";
        else
            return body.substring(0, body.length()-1);
    }
}
