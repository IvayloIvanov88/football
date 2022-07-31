package softuni.exam.config;

import com.google.gson.Gson;
import softuni.exam.util.XMLParser;
import softuni.exam.util.impls.XMLParserImpl;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.impls.ValidationUtilImpl;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }
//Когато има @Component не ни трябва Bean
//    @Bean
//    public XMLParser XMLParser() {
//        return new XMLParserImpl();
//    }

}


