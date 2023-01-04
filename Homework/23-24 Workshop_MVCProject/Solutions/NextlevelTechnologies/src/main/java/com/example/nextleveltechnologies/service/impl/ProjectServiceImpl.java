package com.example.nextleveltechnologies.service.impl;

import com.example.nextleveltechnologies.model.dto.ProjectViewDto;
import com.example.nextleveltechnologies.model.dto.seed.ProjectSeedDto;
import com.example.nextleveltechnologies.model.dto.seed.ProjectsSeedRootDto;
import com.example.nextleveltechnologies.model.entity.Company;
import com.example.nextleveltechnologies.model.entity.Project;
import com.example.nextleveltechnologies.repository.ProjectRepository;
import com.example.nextleveltechnologies.service.CompanyService;
import com.example.nextleveltechnologies.service.ProjectService;
import com.example.nextleveltechnologies.util.ValidatorUtil;
import com.example.nextleveltechnologies.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser,
                              ValidatorUtil validatorUtil, ModelMapper modelMapper,
                              CompanyService companyService) {

        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.companyService = companyService;
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.existsAllBy();
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return Files.readString(Path.of(PROJECTS_XML_FILES_PATH));
    }

    @Override
    public void importProjects(String context) throws JAXBException {
        ProjectsSeedRootDto rootDto = this.xmlParser.parseXml(context, ProjectsSeedRootDto.class);

        for (ProjectSeedDto projectDto : rootDto.getProjects()) {
            if (this.validatorUtil.isValid(projectDto)) {
                Company company = this.companyService.findCompanyByName(projectDto.getCompany().getName());

                if (company != null) {
                    Project project = this.modelMapper.map(projectDto, Project.class);
                    project.setCompany(company);

                    this.projectRepository.save(project);
                }
            }
        }
    }

    @Override
    public Project findProjectByName(String name) {
        return this.projectRepository.findByName(name).orElse(null);
    }

    @Override
    public List<ProjectViewDto> finishedProjects() {
        return this.projectRepository.findAllByIsFinishedIsTrue()
                .stream()
                .map(project -> this.modelMapper.map(project, ProjectViewDto.class))
                .collect(Collectors.toList());
    }
}
