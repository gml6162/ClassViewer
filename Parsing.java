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
    private MemberData[] memberData;
    private MethodInfo[] mi;

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
                methodNum++;
                continue;
            }

            if(buffer.indexOf(tmp) == buffer.indexOf("};")) { //클래스 몸체 끝났을 때
                mi = new MethodInfo[methodNum];
                memberData = new MemberData[methodNum];
            }else if(buffer.indexOf(tmp) < buffer.indexOf("};")) { // 클래스 몸체

                if(tmp.contains(":")) { //접근제한자 파싱
                    for(int i=0; i < keyword.access.length; i++) {
                        if(buffer.indexOf(keyword.access[i]) < buffer.indexOf("~")) { //소멸자보다 앞이면 메소드 접근제한자
                            ci.setMethodAccess(keyword.access[i]);
                            break;
                        }else { //소멸자보다 뒤이면 변수 접근제한자
                            ci.setValueAccess(keyword.access[i]);
                            break;
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
                    String methodName;
                    mi[methodOrder] = new MethodInfo();
                    memberData[methodOrder] = new MemberData();

                    for(int returnType=0; returnType < keyword.type.length; returnType++) {
                        if(tmp.contains(keyword.type[returnType] + " ")) { //반환형이 있는 경우 (only 메소드)

                            //메소드는 void 타입의 매개변수는 표시하지 않음.
                            if(!tmp.contains("()")) { //매개변수의 타입이 void 가 아닌 경우
                                methodName = tmp.substring(tmp.lastIndexOf(':') + 1, tmp.lastIndexOf(' ')) + ")";

                                for (int paraType = 1; paraType < keyword.type.length; paraType++) {
                                    if (tmp.contains("(" + keyword.type[paraType]) && paraType != 0) {//void 가 아닌 경우
                                        ci.setMethodInfo(methodName, keyword.type[returnType]);
                                        mi[methodOrder].setHead(methodName, keyword.type[paraType]);
                                        break;
                                    }
                                }

                            }else { //매개변수의 타입이 void 인 경우
                                methodName = tmp.substring(tmp.lastIndexOf(':')+1, tmp.indexOf('('))+"()";
                                ci.setMethodInfo(methodName, keyword.type[returnType]);
                                mi[methodOrder].setHead(methodName, keyword.type[0]);
                            }

                            break; //바깥 for문 벗어나기 (안벗어나면 중복 저장됨)

                        }else { //생성자나 소멸자인 경우 -> void 타입의 매개변수가 표시됨.
                            if (!tmp.contains(" ")) {

                                for (int paraType = 1; paraType < keyword.type.length; paraType++) {

                                    if (tmp.indexOf(keyword.type[paraType]) > tmp.indexOf(':')) {//매개변수의 타입이 void가 아닌 경우
                                        methodName = tmp.substring(tmp.lastIndexOf(':') + 1, tmp.lastIndexOf(' ')) + ")";
                                        ci.setMethodInfo(methodName, null);
                                        mi[methodOrder].setHead(methodName, keyword.type[paraType]);
                                        break;

                                    } else { //매개변수의 타입이 void 인 경우
                                        methodName = tmp.substring(tmp.lastIndexOf(':')+1, tmp.indexOf('('))+"()";
                                        ci.setMethodInfo(methodName, null);
                                        mi[methodOrder].setHead(methodName, keyword.type[0]);
                                        break;
                                    }

                                }

                                break; //바깥 for문 벗어나기 (안벗어나면 중복 저장됨)
                            }
                        }
                    }
                    methodOrder++;

                }else {
                    if(tmp.charAt(0) != '{' && tmp.charAt(0) != '}') {
                        if(methodOrder > 0) {
                            mi[methodOrder-1].setBody(tmp);
                            for(String val : ci.getValueNames()) {
                                if(tmp.contains(val)) {
                                    memberData[methodOrder-1].addMemberData(val);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
