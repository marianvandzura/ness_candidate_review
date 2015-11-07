package dao;

import model.Options;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IOptionsDao {

    /**
     * Add option to DB
     * @param option
     * @return added question
     */
    Options addOption(Options option);

    /**
     * delete option
     * @param option
     */
    void deleteOption(Options option);

    /**
     * Add multiple options to DB
     * @return List of added options
     */
    List<Options> getAllOptions();

    /**
     * Find option in DB based on optionId
     * @param id ID of option
     * @return Options object
     */
    Options findOptionById(Integer id);

    /**
     * Find all options in DB based on question ID
     * @param questionId ID of question
     * @return List of options with matched question ID
     */
    List<Options> findOptionsByQuestionId(Integer questionId);
}
