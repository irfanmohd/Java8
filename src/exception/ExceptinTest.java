package exception;

import java.io.IOException;

public class ExceptinTest {

    void myMethod(int num) throws IllegalAccessException {
        if (num == 1)
            throw new IllegalAccessException("IOExcetion Occurred");
        else
            throw new RuntimeException("ClassNotFoundException");
    }


    public static void main(String args[]) throws IllegalAccessException {
        //try{
        ExceptinTest obj = new ExceptinTest();
        obj.myMethod(1);
        // }catch(Exception ex){
        //System.out.println(ex);
        // }
    }
}
