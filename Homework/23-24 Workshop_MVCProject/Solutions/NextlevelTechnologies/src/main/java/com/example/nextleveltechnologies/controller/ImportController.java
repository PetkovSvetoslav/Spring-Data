package com.example.nextleveltechnologies.controller;

import com.example.nextleveltechnologies.model.dto.ImportCompaniesDto;
import com.example.nextleveltechnologies.model.dto.ImportEmployeesDto;
import com.example.nextleveltechnologies.model.dto.ImportProjectsDto;
import com.example.nextleveltechnologies.service.CompanyService;
import com.example.nextleveltechnologies.service.EmployeeService;
import com.example.nextleveltechnologies.service.ProjectService;
import com.example.nextleveltechnologies.util.XmlParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public ImportController(CompanyService companyService, EmployeeService employeeService,
                            ProjectService projectService, XmlParser xmlParser) {

        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public String importXml(Model model, HttpServletRequest request) {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("areImported",
                new boolean[]{
                        this.companyService.areImported(),
                        this.projectService.areImported(),
                        this.employeeService.areImported()
                }
        );

        return "xml/import-xml";
    }

    @GetMapping("/companies")
    public String importCompanies(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute(
                "companies",
                this.companyService.readCompaniesXmlFile()
        );

        return "xml/import-companies";
    }

    @PostMapping("/companies")
    public String importCompanies(ImportCompaniesDto dto, HttpServletRequest request) throws JAXBException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        this.companyService.importCompanies(dto.getCompanies());

        return "redirect:/import/xml";
    }

    @GetMapping("/projects")
    public String importProjects(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute(
                "projects",
                this.projectService.readProjectsXmlFile()
        );

        return "xml/import-projects";
    }

    @PostMapping("/projects")
    public String importProjects(ImportProjectsDto dto, HttpServletRequest request) throws JAXBException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        this.projectService.importProjects(dto.getProjects());

        return "redirect:/import/xml";
    }

    @GetMapping("/employees")
    public String importEmployees(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute(
                "employees",
                this.employeeService.readEmployeeXmlFile()
        );

        return "xml/import-employees";
    }

    @PostMapping("/employees")
    public String importEmployees(ImportEmployeesDto dto, HttpServletRequest req) throws JAXBException {
        if (!this.isLogged(req)) {
            return "redirect:/";
        }

        this.employeeService.importEmployees(dto.getEmployees());

        return "redirect:/import/xml";
    }
}
