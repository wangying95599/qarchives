package org.quetzaco.archives.util.runnable;

public class ThreadLocalTest {
    private static ThreadLocal<Long> usrTl = new ThreadLocal<>();

    public static ThreadLocal<Long> getThreadLocal(){
        return usrTl;
    }
    public  Long get(){
        return usrTl.get();
    }

    public void set(Long usrId){
        usrTl.set(usrId);
    }

    public static void setUsrTl(Long usrId){
        ThreadLocalTest.getThreadLocal().set(usrId);
    }

    public static Long getUsrTl(){
        return ThreadLocalTest.getThreadLocal().get();
    }

}
