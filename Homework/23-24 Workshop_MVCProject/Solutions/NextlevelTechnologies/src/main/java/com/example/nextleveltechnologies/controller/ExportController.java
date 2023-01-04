package com.example.nextleveltechnologies.controller;

import com.example.nextleveltechnologies.model.dto.EmployeeViewDto;
import com.example.nextleveltechnologies.model.dto.ProjectViewDto;
import com.example.nextleveltechnologies.service.EmployeeService;
import com.example.nextleveltechnologies.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController{

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public String finishedProjects(Model model, HttpServletRequest request) {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute(
                "projectsIfFinished",
                this.projectService.finishedProjects()
                        .stream()
                        .map(ProjectViewDto::toString)
                        .collect(Collectors.joining(System.lineSeparator()))
        );

        return "export/export-project-if-finished";
    }

    @GetMapping("/employees-above")
    public String employeesAbove(Model model, HttpServletRequest request) {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute(
                "employeesAbove",
                this.employeeService.getEmployeesAfterTwentyFive()
                        .stream()
                        .map(EmployeeViewDto::toString)
                        .collect(Collectors.joining(System.lineSeparator()))
        );

        return "export/export-employees-with-age";
    }
}
