package ru.itis.listeners;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.config.ApplicationConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    private ApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        springContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("springContext", springContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariDataSource hikariDataSource = springContext.getBean(HikariDataSource.class);
        hikariDataSource.close();
    }
}

