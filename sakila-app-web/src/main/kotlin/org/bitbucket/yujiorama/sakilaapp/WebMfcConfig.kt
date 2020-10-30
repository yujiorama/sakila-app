package org.bitbucket.yujiorama.sakilaapp

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebMfcConfig : WebMvcConfigurer {

    @Bean
    fun jacksonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {

        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            run {
                builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .deserializers(LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            }
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {

        registry.addInterceptor(LoggingInterceptor())
    }
}

class LoggingInterceptor : HandlerInterceptorAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        logger.info("request: method=[{}], url=[{}]", request.method, request.requestURL)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {

        logger.info("response: status=[{}], exception=[{}]", response.status, ex)
    }
}
