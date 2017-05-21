package viewerPackage;

/**
 * Created by gej48 on 2017-05-18.
 */
public class MethodInfo {

    //FIELDS
    private String name;
    private String paraType;
    private StringBuffer body = new StringBuffer();

    //CONSTRUCTOR
    MethodInfo() { }

    //METHODS
    public void setHead(String name, String paraType) {
        this.name = name;
        this.paraType = paraType;
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
    public String getParaType() { return paraType; }
    public String getBody() {
        if(body.length() == 0)
            return "";
        else
            return body.substring(0, body.length()-1);
    }

}
