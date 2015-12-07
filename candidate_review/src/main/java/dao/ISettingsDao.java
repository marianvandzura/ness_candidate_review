package dao;

import model.Settings;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ISettingsDao {

    /**
     * add new setting
     * @param setting
     */
    void addSetting(Settings setting);

    /**
     * get all settings
     * @return
     */
    List<Settings> getAllSettings();
}
