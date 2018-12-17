package com.botq.ppmtool.web;

import com.botq.ppmtool.domain.Project;
import com.botq.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /*
        @Valid annotation provides a better explanation of error, and instead of error 500 we get error 400 which is more accurate
        BindingResult result - will hold the result of response. If something went wrong, the result.hasErrors() method will return true.
        We sue <?> generic type here as we want to return String if the request was invalid, but we also want to return ResponseEntity<Project> if everything went good
     */
    @PostMapping("")    // this will handle the upcoming request
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        if(result.hasErrors()){
            return new ResponseEntity<String>("Invalid Project Object", HttpStatus.BAD_REQUEST);
        }

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
}
