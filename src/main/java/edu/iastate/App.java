package edu.iastate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App extends SpringBootServletInitializer
{
    public static void main(String[] args) throws Exception {
        //ApplicationContext ctx = SpringApplication.run(App.class, args);
        SpringApplication app = new SpringApplication(App.class);
        //app.setShowBanner(false);

        //SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        //addDefaultProfile(app, source);

        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}
