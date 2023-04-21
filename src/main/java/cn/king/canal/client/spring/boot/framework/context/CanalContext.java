package cn.king.canal.client.spring.boot.framework.context;

import cn.king.canal.client.spring.boot.framework.model.CanalModel;

public class CanalContext {
    
    private static ThreadLocal<CanalModel> threadLocal = new ThreadLocal<>();
    
    public static CanalModel getModel(){
       return threadLocal.get();
    }
    
    public static void setModel(CanalModel canalModel){
        threadLocal.set(canalModel);
    }
    
    public  static void removeModel(){
        threadLocal.remove();
    }
    
}
