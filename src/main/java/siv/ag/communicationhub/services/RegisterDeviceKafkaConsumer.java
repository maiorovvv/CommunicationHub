package siv.ag.communicationhub.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import siv.ag.communicationhub.dto.MessageDTO;
import siv.ag.communicationhub.models.MessageEntity;
import siv.ag.communicationhub.models.MessageStatus;
import siv.ag.communicationhub.repositories.MessageRepository;

import java.time.LocalDateTime;

@Service
public class RegisterDeviceKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(RegisterDeviceKafkaConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "register_device_message", groupId = "communicationhub_consumer_group")
    public void consume(String message) {
        try {
            // Десериализация сообщения из JSON
            MessageDTO messageDTOObject = objectMapper.readValue(message, MessageDTO.class);

            // Создание сущности MessageEntity на основе данных из MessageDTO
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setKundenID(messageDTOObject.getKundenID());
            messageEntity.setNachricht(messageDTOObject.getNachricht());
            messageEntity.setCreateDate(LocalDateTime.now());
            messageEntity.setStatus(MessageStatus.RECEIVED);  // Устанавливаем статус

            // Сохранение сущности в базу данных
            messageRepository.save(messageEntity);


//            // Поиск пользователя по benutzername
//            User user = userRepository.findByBenutzername(request.getBenutzername()).orElseThrow(() -> new UserNotFoundException());
//
//            // Сохранение устройства, если его еще нет
//            Device device = deviceRepository.findByDeviceId(request.getDeviceId()).orElseGet(() -> {
//                Device newDevice = new Device();
//                newDevice.setDeviceId(request.getDeviceId());
//                newDevice.setUser(user);
//                return deviceRepository.save(newDevice);
//            });

            log.info("Message processed and saved to DB: {}", messageEntity);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing message", e);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }
}
