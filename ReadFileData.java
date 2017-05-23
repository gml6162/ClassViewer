package viewerPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gej48 on 2017-05-18.
 */
public class ReadFileData {

    public static void main(String[] args) {

        Parsing parsing;
        int b = 0;
        StringBuffer buffer = new StringBuffer();
        FileInputStream file = null;
        try {
            file = new FileInputStream(ReadFileData.class.getResource("").getPath()+"/Queue.cpp");
            b = file.read();
            while(b != -1) {
                buffer.append((char)b);
                b = file.read();
            }
            parsing = new Parsing(buffer);

            /* 확인용
            MethodInfo[] mis = parsing.getMethodInfo();

            System.out.println("클래스 이름 : " + parsing.getClassInfo().getName());
            System.out.println();
            System.out.println("메소드접근자: " + parsing.getClassInfo().getMethodAccess());
            System.out.println("메소드 이름 : " + parsing.getClassInfo().getMethodNames());
            System.out.println("메소드반환형: " + parsing.getClassInfo().getMethodTypes());
            System.out.println();
            System.out.println("변수접근자 : " +parsing.getClassInfo().getValueAccess());
            System.out.println("변수 이름 : " + parsing.getClassInfo().getValueNames());
            System.out.println("변수반환형 : "+parsing.getClassInfo().getValueTypes());
            System.out.println();
            for(int i = 0; i < parsing.getClassInfo().getMethodNames().size(); i++) {
                System.out.println();
                System.out.println("메소드이름 : " + mis[i].getName());
                System.out.println("메소드 매개변수이름 : " + mis[i].getParameterName());
                System.out.println("메소드 매개변수타입 : " + mis[i].getParameterType());
                System.out.println("▼ 메소드 몸체 : ");
                System.out.println(mis[i].getBody());
                System.out.println((i+1)+"번째 메소드의 멤버변수 : " + parsing.getMemberData()[i].getMemberDatas());
                System.out.println();
            }
            */

        }catch(FileNotFoundException e) {
            System.out.println("Oops : FileNotFoundException");
        }catch(IOException e) {
            System.out.println("Input error");
        }


    }
}
