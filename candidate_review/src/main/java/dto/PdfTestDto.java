package dto;

import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 7.11.2015.
 */
public class PdfTestDto extends TestDto {

    // Key represents Question ID and value represents
    // marked option.
    // Map will be used for creating test in PDF format.
    private Map<Integer, List<Integer>> markedAnswers;

    private CandidateDto candidate;

    public Map<Integer, List<Integer>> getMarkedAnswers() {
        return markedAnswers;
    }

    public void setMarkedAnswers(Map<Integer, List<Integer>> markedAnswers) {
        this.markedAnswers = markedAnswers;
    }

    public CandidateDto getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }
}
