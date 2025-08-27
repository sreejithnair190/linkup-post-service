package me.sreejithnair.linkup.post_service.config;

import me.sreejithnair.linkup.post_service.constants.Events;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.sreejithnair.linkup.post_service.constants.Events.POST_CREATED_EVENT;
import static me.sreejithnair.linkup.post_service.constants.Events.POST_LIKED_EVENT;


@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic postCreatedTopic() {
        return new NewTopic(POST_CREATED_EVENT, 3, (short) 1);
    }

    @Bean
    public NewTopic posLikedTopic() {
        return new NewTopic(POST_LIKED_EVENT, 3, (short) 1);
    }
}
