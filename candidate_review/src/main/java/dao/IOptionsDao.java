package dao;

import model.Options;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IOptionsDao {

    Options addOption(Options option);

<<<<<<< HEAD
    List<Options> getAllOptions();

    List<Options> findOptionsByQuestion(Integer questionId);
=======
    List<Options> getAllOptiopns();

    Options findById(Integer id);

    List<Options> findOptionsForQuestion(Integer questionId);
>>>>>>> refs/remotes/origin/master
}
