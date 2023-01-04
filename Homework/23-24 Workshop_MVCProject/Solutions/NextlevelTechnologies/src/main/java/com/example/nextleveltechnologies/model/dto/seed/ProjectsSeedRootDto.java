package com.example.nextleveltechnologies.model.dto.seed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectsSeedRootDto {

    @XmlElement(name = "project")
    private List<ProjectSeedDto> projects;
}
