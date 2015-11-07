package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @Column(name = "setting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer settingId;

    public Settings() {
        //default
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }
}
