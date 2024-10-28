package com.example.banggusuk_michelin;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.function.Function;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        return factory;
    }

    @Bean
    public WebClient webClient() {
        //HttpClient를 이용해 WebClient의 디폴트 HTTP 클라이언트 라이브러리인 Reactor Netty를 커스텀
        Function<HttpClient, HttpClient> mapper = client -> HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000) // 커넥션 타임아웃
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(10)) // read 타임아웃
                        .addHandlerLast(new WriteTimeoutHandler(10))) // write 타임아웃
                .responseTimeout(Duration.ofSeconds(1));

        ClientHttpConnector connector =
                new ReactorClientHttpConnector(resourceFactory(), mapper);
        return WebClient.builder().clientConnector(connector).build();
    }
}