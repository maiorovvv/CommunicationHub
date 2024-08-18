package siv.ag.communicationhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siv.ag.communicationhub.models.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    // Здесь можно добавлять дополнительные методы для специфичных запросов
}

