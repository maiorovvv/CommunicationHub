package siv.ag.communicationhub.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import siv.ag.communicationhub.dto.MessageDTO;


import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/messages")
@Tags(@Tag(name = "OMS Messages"))
public class OMSMessageController {

    private static final Logger log = LoggerFactory.getLogger(OMSMessageController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/send")
    @Operation(summary = "Receive a message", description = "Receive a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> receiveMessage(@Valid @RequestBody MessageDTO messageDTO) {
        CompletableFuture.runAsync(() -> {
            try {
                // Сериализация сообщения в JSON
                String jsonMessage = objectMapper.writeValueAsString(messageDTO);
                // Отправка сообщения в Kafka
                kafkaTemplate.send("receiving_oms_message", jsonMessage);
            } catch (JsonProcessingException e) {
                // Обработка исключения при сериализации
                log.error("Error serializing message", e);
            }
        });

        return ResponseEntity.ok("Message received");
    }
}
