package siv.ag.communicationhub.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "app_clients")
public class AppClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "benutzername", nullable = false)
    private String benutzername;

    @OneToMany(mappedBy = "appClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceEntity> devices;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }
}
