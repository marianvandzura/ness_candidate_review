package dao;

import model.Options;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IOptionsDao {

    void addOptions(Options options);

    List<Options> getAllOptiopns();
}
