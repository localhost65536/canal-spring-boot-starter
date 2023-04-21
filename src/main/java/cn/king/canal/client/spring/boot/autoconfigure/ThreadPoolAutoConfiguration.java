package cn.king.canal.client.spring.boot.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ConditionalOnProperty(value = "canal.async", havingValue = "true", matchIfMissing = true)
public class ThreadPoolAutoConfiguration {
    
    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("canal-execute-thread-%d")
                // 当一个线程在运行过程中由于某些原因抛出了未处理异常，
                // 且该线程所属的线程组没有专门的异常处理器时，就会调用 Thread.UncaughtExceptionHandler 
                // 接口的 uncaughtException(Thread t, Throwable e) 方法。在该方法中，可以编写自定义的异常处理逻辑。
                .uncaughtExceptionHandler(new CanalThreadUncaughtExceptionHandler()).build();
        return Executors.newFixedThreadPool(20, factory);
    }

    @Slf4j
    static class CanalThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        /**
         * 当某个线程抛出未捕获的异常时，该方法将被调用。
         */
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("thread " + t.getName() + " have a exception", e);
        }

    }
    
}
