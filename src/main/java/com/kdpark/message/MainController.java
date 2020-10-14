package com.kdpark.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/order")
    public String orderCoffee(@RequestParam("coffee") String coffeeName) {
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, "order.coffee.first", coffeeName);
        return "order";
    }
}
