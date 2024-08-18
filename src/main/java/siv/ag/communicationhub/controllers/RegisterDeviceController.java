package siv.ag.communicationhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siv.ag.communicationhub.dto.RegisterDeviceDTO;

@RestController
@RequestMapping("/api/v1/register-device")
@Tags(@Tag(name = "Register device", description = "Register device from MobileApp"))
public class RegisterDeviceController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/send")
    @Operation(summary = "Register device", description = "Register device from MobileApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> sendMessage(@RequestBody RegisterDeviceDTO request) {
        try {

            // Сериализация сообщения в JSON
            String jsonMessage = objectMapper.writeValueAsString(request);
            // Отправка сообщения в Kafka
            kafkaTemplate.send("register_device_message", jsonMessage);

            return ResponseEntity.ok("Message sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending message");
        }
    }
}
