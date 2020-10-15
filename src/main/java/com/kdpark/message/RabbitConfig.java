package com.kdpark.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String REQUEST_QUEUE = "coffee-order";
    public static final String RESPONSE_QUEUE = "coffee-result";

    public static final String EXCHANGE = "coffee-exchange";

    public static final String REQUEST_ROUTING = "order.coffee.#";
    public static final String RESPONSE_ROUTING = "result.coffee.#";

    @Bean
    public Queue reqQueue() {
        return new Queue(REQUEST_QUEUE, true);
    }

    @Bean
    public Queue resQueue() {
        return new Queue(RESPONSE_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding reqBinding(@Qualifier("reqQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(REQUEST_ROUTING);
    }

    @Bean
    public Binding resBinding(@Qualifier("resQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RESPONSE_ROUTING);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
