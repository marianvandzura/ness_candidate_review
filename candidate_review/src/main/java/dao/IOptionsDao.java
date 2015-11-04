package dao;

import model.Options;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IOptionsDao {

    Options addOption(Options option);

    List<Options> getAllOptiopns();

    Options findById(Integer id);

    List<Options> findOptionsForQuestion(Integer questionId);
}
