package dao;

import model.Settings;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ISettingsDao {

    void addSettings(Settings settings);

    List<Settings> getAllSettings();
}
