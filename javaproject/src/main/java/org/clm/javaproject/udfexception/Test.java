package org.clm.javaproject.udfexception;

public class Test {
    public static void main(String[] args) {

        if(true){
            try {
                throw new CommonException(ResultCode.FAIL);
            } catch (CommonException e) {
                e.printStackTrace();
            }
        }
    }
}
