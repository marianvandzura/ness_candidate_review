package assemblers;

import dto.OptionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */
@Component
public class OptionAssembler {

    @Autowired
    private QuestionAssembler questionAssembler;

    public OptionDto extractDtoFromDomain(final Options domain) {
        OptionDto dto = new OptionDto();
        dto.setId(domain.getOptionId());
        dto.setOption(domain.getOption());
        dto.setTruth(domain.getTruth());
        dto.setQuestion(questionAssembler.extractDtoFromDomain(domain.getQuestion()));
        return dto;
    }

    public List<OptionDto> extractDtoFromDomain(final List<Options> domains) {
        List<OptionDto> dtos = new ArrayList<OptionDto>();
        for(Options domain : domains) {
            dtos.add(this.extractDtoFromDomain(domain));
        }
        return dtos;
    }
    public Options populateDomainFromDto(final OptionDto dto) {
        Options domain = new Options();
        domain.setOptionId(dto.getId());
        domain.setOption(dto.getOption());
        domain.setTruth(dto.getTruth());
        domain.setQuestion(questionAssembler.populateDomainFromDto(dto.getQuestion()));
        return domain;
    }

    public List<Options> populateDomainFromDto(final List<OptionDto> dtos) {
        List<Options> domains = new ArrayList<Options>();
        for(OptionDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
    }

}
