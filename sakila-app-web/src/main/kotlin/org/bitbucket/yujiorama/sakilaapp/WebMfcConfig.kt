package org.bitbucket.yujiorama.sakilaapp

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebMfcConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {

        registry.addInterceptor(LoggingInterceptor())
    }
}

class LoggingInterceptor : HandlerInterceptorAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {

        logger.info("request: method=[{}], url=[{}]", request.method, request.requestURL)
        logger.info("response: status=[{}], exception=[{}]", response.status, ex)
    }
}

