package viewerPackage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by gej48 on 2017-05-18.
 */
public class Parsing {

    //FIELDS
    private Keyword keyword = new Keyword();
    private ClassInfo ci = new ClassInfo();
    private ArrayList<MethodInfo> mi = new ArrayList<>();
    private ArrayList<MemberData> md = new ArrayList<>();

    //CONSTRUCTOR
    Parsing(StringBuffer buffer) {
        StringTokenizer st = new StringTokenizer(buffer.toString(), "\r\n", false);
        StringBuffer methodBody = new StringBuffer();

        int methodOrder = 0;
        int methodNum = 0;

        while(st.hasMoreTokens()) {
            String tmp = st.nextToken();
            //System.out.println(tmp);

            if(tmp.contains(keyword.cName)) { //클래스 이름
                ci.setName(tmp.substring(tmp.indexOf(" ")+1, tmp.length()));
                continue;
            }

            if(tmp.contains(");")) { //메소드 개수 세기
                mi.add(new MethodInfo());
                md.add(new MemberData());
                continue;
            }

            if(buffer.indexOf(tmp) < buffer.indexOf("};")) { // 클래스 몸체

                if(tmp.lastIndexOf(':') == tmp.length() - 1) { //접근제한자 파싱
                    for(int i=0; i < keyword.access.length; i++) {
                        if(tmp.contains(keyword.access[i])) {
                            if (buffer.indexOf(keyword.access[i]) < buffer.indexOf("~")) { //소멸자보다 앞이면 메소드 접근제한자
                                ci.setMethodAccess(keyword.access[i]);
                                break;
                            } else { //소멸자보다 뒤이면 변수 접근제한자
                                ci.setValueAccess(keyword.access[i]);
                                break;
                            }
                        }
                    }
                }

                if(buffer.indexOf(tmp) > buffer.indexOf("~")) { //변수 파싱
                    for(int i = 0; i < keyword.type.length; i++) {
                        if(tmp.contains(keyword.type[i])) {
                            if(tmp.indexOf("];") != -1) { //배열인 경우
                                ci.setValueInfo(tmp.substring(tmp.lastIndexOf(" ")+1, tmp.indexOf("[")), keyword.type[i]+"[]");
                            }else { //배열이 아닌 경우
                                ci.setValueInfo(tmp.substring(tmp.lastIndexOf(" ") + 1, tmp.indexOf(";")), keyword.type[i]);
                            }
                            break;
                        }
                    }
                }
            }else { //메소드 영역

                if(tmp.contains("::") && !tmp.contains(keyword.controlStatement[0])) { //메소드 이름인 경우

                    StringBuffer methodName = new StringBuffer(tmp.substring(tmp.lastIndexOf(':')+1, tmp.indexOf('(')+1));
                    String paraType, paraName;

                    for(int returnType=0; returnType < keyword.type.length; returnType++) {
                        if(tmp.contains(keyword.type[returnType] + " ")) { //생성자나 소멸자가 아닌 경우

                            if(!tmp.contains("()")) { //매개변수가 있는 경우
                                if(tmp.contains(",")) { //매개변수가 여러 개인 경우
                                    String[] parameters = tmp.substring(tmp.indexOf('(')+1, tmp.indexOf(')')).split(", ");
                                    for(int i = 0; i < parameters.length; i++) {
                                        paraType = parameters[i].split(" ")[0]; //타입
                                        paraName = parameters[i].split(" ")[1]; //이름
                                        methodName.append(paraType);
                                        if(i == parameters.length-1) methodName.append(')');
                                        else methodName.append(", ");
                                        mi.get(methodOrder).setParameter(paraName, paraType);
                                    }

                                }else {//매개변수가 한개인 경우
                                    methodName.append(tmp.substring(tmp.indexOf('(')+1, tmp.lastIndexOf(' '))+')');
                                    paraType = tmp.substring(tmp.indexOf('(')+1, tmp.lastIndexOf(' '));
                                    paraName = tmp.substring(tmp.lastIndexOf(' ')+1, tmp.indexOf(')'));
                                    mi.get(methodOrder).setParameter(paraName, paraType);
                                }

                            }else { //매개변수의 타입이 void 인 경우
                                methodName.append(")");
                            }

                            ci.setMethodInfo(methodName.toString(), keyword.type[returnType]);
                            mi.get(methodOrder).setName(methodName.toString());
                            methodOrder++;
                            break;

                        }else if (!tmp.contains(keyword.type[returnType] + " ") && tmp.indexOf(ci.getName()) == 0) {
                            //생성자나 소멸자인 경우 -> void 타입의 매개변수가 표시됨.
                            if(!tmp.contains(keyword.type[0])) { //매개변수가 있는 경우
                                if(tmp.contains(",")) { //매개변수가 여러 개인 경우
                                    String[] parameters = tmp.substring(tmp.indexOf('(')+1, tmp.indexOf(')')).split(", ");
                                    for(int i = 0; i < parameters.length; i++) {
                                        paraType = parameters[i].split(" ")[0]; //타입
                                        paraName = parameters[i].split(" ")[1]; //이름
                                        methodName.append(paraType);
                                        if(i == parameters.length-1) methodName.append(')');
                                        else methodName.append(", ");
                                        mi.get(methodOrder).setParameter(paraName, paraType);
                                    }

                                }else {//매개변수가 한개인 경우
                                    methodName.append(tmp.substring(tmp.indexOf('(')+1, tmp.indexOf(' ')) + ')');
                                    paraType = tmp.substring(tmp.indexOf('(')+1, tmp.lastIndexOf(' '));
                                    paraName = tmp.substring(tmp.lastIndexOf(' ')+1, tmp.indexOf(')'));
                                    mi.get(methodOrder).setParameter(paraName, paraType);
                                }

                            }else{ //매개변수 타입이 void 인 경우
                                methodName.append(")");
                            }

                            ci.setMethodInfo(methodName.toString(), keyword.type[0]);
                            mi.get(methodOrder).setName(methodName.toString());
                            methodOrder++;
                            break;
                        }
                    }
                }else {
                    if(tmp.charAt(0) != '{' && tmp.charAt(0) != '}') {
                        if(methodOrder > 0) {
                            mi.get(methodOrder-1).setBody(tmp);
                            for(String val : ci.getValueNames()) {
                                if(tmp.contains(val)) {
                                    md.get(methodOrder-1).addMemberData(val);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //METHODS
    public Keyword getKeyword() { return keyword; }
    public ClassInfo getClassInfo() { return ci; }
    public MethodInfo[] getMethodInfo() { return mi.toArray(new MethodInfo[mi.size()]); }
    public MemberData[] getMemberData() { return md.toArray(new MemberData[md.size()]); }
}
