package siv.ag.communicationhub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "MessageOMS", description = "Massage from OMS")
public class MessageDTO {

    @Schema(description = "Client ID", example = "123456782")
    //@Size(max = 5, message = "Index should not exceed 5 characters")
    @NotBlank(message = "Client ID is required")
    private String kundenID;

    @Schema(description = "Massage from OMS", example = "Hallo Herr Drewes, Sie haben ein neues Dokument in Ihrer Postbox erstellt. Vielen Dank!")
    @NotBlank(message = "Massage is required")
    private String nachricht;

    // Геттеры и сеттеры для полей

    public String getKundenID() {
        return kundenID;
    }

    public void setKundenID(String kundenID) {
        this.kundenID = kundenID;
    }

    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }
}