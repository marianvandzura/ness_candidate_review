package dao;

import model.Candidates;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICandidatesDao {

    void addCandidates(Candidates candidates);
    List<Candidates> getAllCandidates();
}
