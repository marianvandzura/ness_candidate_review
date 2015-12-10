package assemblers;

import dto.CandidateDto;
import model.CandidatesReports;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 24.11.2015.
 */
@Component
public class CandidateReportAssembler {

    /**
     * Extract Dto from domain.
     *
     * @param domain
     * @return extracted DTO
     */
    public CandidateDto extractDtoFromDomain(final CandidatesReports domain) {
        CandidateDto dto = new CandidateDto();
        dto.setId(domain.getReportId());
        dto.setTestName(domain.getTestName());
        dto.setTotalTime(domain.getTotalTime());
        dto.setEmail(domain.getEmail());
        dto.setDate(domain.getDate());
        dto.setFirstName(domain.getCandidateName());
        dto.setLastName(domain.getCandidateSurname());
        dto.setNumberOfQuestions(domain.getNumberOfQuestions());
        dto.setPdf(domain.getPdfResult());
        dto.setTestResult(domain.getTestResult());
        dto.setPosition(domain.getPosition());
        dto.setSuccesRate(domain.getSuccesRate());
        return dto;
    }


    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<CandidateDto> extractDtoFromDomain(final List<CandidatesReports> domain) {
        List<CandidateDto> dtos = new ArrayList<CandidateDto>();
        for (CandidatesReports category : domain) {
            dtos.add(extractDtoFromDomain(category));
        }
        return dtos;
    }

    /**
     * create domain object from DTO
     * @param dto
     * @return domain object
     */
    public CandidatesReports populateDomainFromDto(final CandidateDto dto) {
        CandidatesReports domain = new CandidatesReports();
        domain.setReportId(dto.getId());
        domain.setTestName(dto.getTestName());
        domain.setTotalTime(dto.getTotalTime());
        domain.setEmail(dto.getEmail());
        domain.setDate(dto.getDate());
        domain.setCandidateName(dto.getFirstName());
        domain.setCandidateSurname(dto.getLastName());
        domain.setNumberOfQuestions(dto.getNumberOfQuestions());
        domain.setPdfResult(dto.getPdf());
        domain.setTestResult(dto.getTestResult());
        domain.setPosition(dto.getPosition());
        domain.setSuccesRate(dto.getSuccesRate());
        return domain;
    }

    /**
     * create domain objects from DTO List
     * @param dtos
     * @return List of added domain objects
     */
    public List<CandidatesReports> populateDomainFromDto(List<CandidateDto> dtos) {

        List<CandidatesReports> domains = new ArrayList<CandidatesReports>();
        for (CandidateDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
    }
}
