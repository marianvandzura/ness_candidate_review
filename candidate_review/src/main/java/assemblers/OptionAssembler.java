package assemblers;

import dto.OptionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marian_Vandzura on 6.11.2015.
 */
@Component
public class OptionAssembler {

    @Autowired
    private QuestionAssembler questionAssembler;

    public OptionAssembler(){
        //default
    }

    /**
     * create OptionDto object from domain model Options
     * @param domain Options model
     * @return OptionDto object
     */
    public OptionDto extractDtoFromDomain(final Options domain) {
        OptionDto dto = new OptionDto();
        dto.setId(domain.getOptionId());
        dto.setOption(domain.getOption());
        dto.setTruth(domain.getTruth());
        return dto;
    }


    /**
     * update questionDto with new values
     *
     * @param option
     * @param newOption
     * @return updated question
     */
    public OptionDto updateDto(final OptionDto option, final OptionDto newOption) {
        option.setOption(newOption.getOption());
        option.setTruth(newOption.getTruth());
        return option;
    }

    /**
     * create OptionDto object for all domain Options models in List
     * @param domains
     * @return List of OptionDto objects
     */
    public List<OptionDto> extractDtoFromDomain(final Collection<Options> domains) {
        List<OptionDto> dtos = new ArrayList<OptionDto>();
        for(Options domain : domains) {
            dtos.add(this.extractDtoFromDomain(domain));
        }
        return dtos;
    }

    /**
     * create domain object from DTO
     * @param dto
     * @return domain object
     */
    public Options populateDomainFromDto(final OptionDto dto) {
        Options domain = new Options();
        domain.setOption(dto.getOption());
        domain.setTruth(dto.getTruth());
        return domain;
    }

    /**
     * create domain objects from DTO List
     * @param dtos
     * @return List of domain objects
     */
    public List<Options> populateDomainFromDto(final List<OptionDto> dtos) {
        List<Options> domains = new ArrayList<Options>();
        for(OptionDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
    }

}
