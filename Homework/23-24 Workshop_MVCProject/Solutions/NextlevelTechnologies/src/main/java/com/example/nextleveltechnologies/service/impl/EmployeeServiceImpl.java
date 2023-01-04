package com.example.nextleveltechnologies.service.impl;

import com.example.nextleveltechnologies.model.dto.EmployeeViewDto;
import com.example.nextleveltechnologies.model.dto.seed.EmployeeSeedDto;
import com.example.nextleveltechnologies.model.dto.seed.EmployeesSeedRootDto;
import com.example.nextleveltechnologies.model.entity.Employee;
import com.example.nextleveltechnologies.model.entity.Project;
import com.example.nextleveltechnologies.repository.EmployeeRepository;
import com.example.nextleveltechnologies.service.EmployeeService;
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

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, XmlParser xmlParser,
                               ValidatorUtil validatorUtil, ModelMapper modelMapper,
                               ProjectService projectService) {

        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.projectService = projectService;
    }

    @Override
    public boolean areImported() {
        return this.employeeRepository.existsAllBy();
    }

    @Override
    public String readEmployeeXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_XML_FILES_PATH));
    }

    @Override
    public void importEmployees(String context) throws JAXBException {
        EmployeesSeedRootDto rootDto = xmlParser.parseXml(context, EmployeesSeedRootDto.class);

        for (EmployeeSeedDto employeeDto : rootDto.getEmployees()) {
            if (validatorUtil.isValid(employeeDto)) {
                Project project = projectService.findProjectByName(employeeDto.getProject().getName());

                if (project != null) {
                    Employee employee = modelMapper.map(employeeDto, Employee.class);
                    employee.setProject(project);

                    employeeRepository.save(employee);
                }
            }
        }
    }

    @Override
    public List<EmployeeViewDto> getEmployeesAfterTwentyFive() {
        return this.employeeRepository.findAllByAgeAfter((byte) 25);
    }
}
