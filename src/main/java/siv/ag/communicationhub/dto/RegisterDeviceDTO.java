package siv.ag.communicationhub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register device", description = "Register device from MobileApp")
public class RegisterDeviceDTO {

    @Schema(description = "Username", example = "aeral.energie@aeral.de")
    //@Size(max = 5, message = "Index should not exceed 5 characters")
    @NotBlank(message = "CUsername is required")
    private String benutzername;

    @Schema(description = "Device ID from MobileApp", example = "32FDAAFEA8CC86016")
    @NotBlank(message = "Massage is required")
    private String deviceID;

    public @NotBlank(message = "Username is required") String getBenutzername() {
        return benutzername;
    }

    public @NotBlank(message = "Massage is required") String getDeviceID() {
        return deviceID;
    }
}