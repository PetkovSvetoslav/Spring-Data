package com.example.nextleveltechnologies.service;

import com.example.nextleveltechnologies.model.dto.ProjectViewDto;
import com.example.nextleveltechnologies.model.dto.seed.ProjectSeedDto;
import com.example.nextleveltechnologies.model.entity.Project;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static com.example.nextleveltechnologies.common.Constants.PATH_FILES_XMLS;

public interface ProjectService {

    String PROJECTS_XML_FILES_PATH = PATH_FILES_XMLS + "projects.xml";

    boolean areImported();

    String readProjectsXmlFile() throws IOException;

    void importProjects(String context) throws JAXBException;

    Project findProjectByName(String name);

    List<ProjectViewDto> finishedProjects();
}
