package spharos.settle.batch;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

@UtilityClass
public class BeanUtils {
    public static <T> T getBean(String beanName, Class<T> classType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(beanName, classType);
    }

    public static PlatformTransactionManager getTransactionManagerBean(int key) {
        return getBean(String.format("db%dTransactionManager", key), PlatformTransactionManager.class);
    }
}