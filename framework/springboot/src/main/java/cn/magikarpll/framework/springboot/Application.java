package cn.magikarpll.framework.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

    /**
     * springboot框架实现的webmvc项目一定要通过Maven插件或者springboot命令行启动，
     * 虽然通过这下面的main也能直接启动并不会报错，但是访问页面的时候找不到资源，会报出404的错误
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
        return springApplicationBuilder.sources(Application.class);
    }

}
