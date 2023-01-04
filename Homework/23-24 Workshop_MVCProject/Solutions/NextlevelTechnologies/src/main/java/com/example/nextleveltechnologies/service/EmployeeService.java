package com.example.nextleveltechnologies.service;

import com.example.nextleveltechnologies.model.dto.EmployeeViewDto;
import com.example.nextleveltechnologies.model.entity.Employee;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static com.example.nextleveltechnologies.common.Constants.PATH_FILES_XMLS;

public interface EmployeeService {

    String EMPLOYEES_XML_FILES_PATH = PATH_FILES_XMLS + "employees.xml";

    boolean areImported();

    String readEmployeeXmlFile() throws IOException;

    void importEmployees(String context) throws JAXBException;

    List<EmployeeViewDto> getEmployeesAfterTwentyFive();
}
