package com.example.nextleveltechnologies.service.impl;

import com.example.nextleveltechnologies.model.dto.seed.CompaniesSeedRootDto;
import com.example.nextleveltechnologies.model.entity.Company;
import com.example.nextleveltechnologies.repository.CompanyRepository;
import com.example.nextleveltechnologies.service.CompanyService;
import com.example.nextleveltechnologies.util.ValidatorUtil;
import com.example.nextleveltechnologies.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;


    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser,
                              ValidatorUtil validatorUtil, ModelMapper modelMapper) {

        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.existsAllBy();
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_XML_FILES_PATH));
    }

    @Override
    public void importCompanies(String context) throws JAXBException {
        CompaniesSeedRootDto rootDto = this.xmlParser.parseXml(context, CompaniesSeedRootDto.class);

        rootDto.getCompanies()
                .stream()
                .filter(this.validatorUtil::isValid)
                .map(companyDto -> this.modelMapper.map(companyDto, Company.class))
                .forEach(this.companyRepository::save);
    }

    @Override
    public Company findCompanyByName(String name) {
        return this.companyRepository.findCompanyByName(name).orElse(null);
    }
}
