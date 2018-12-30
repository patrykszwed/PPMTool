package com.botq.ppmtool.web;

import com.botq.ppmtool.domain.Project;
import com.botq.ppmtool.services.MapValidationErrorService;
import com.botq.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /*
        @Valid annotation provides a better explanation of error, and instead of error 500 we get error 400 which is more accurate
        BindingResult result - will hold the result of response. If something went wrong, the result.hasErrors() method will return true.
        We use <?> generic type here as we want to return String if the request was invalid, but we also want to return ResponseEntity<Project> if everything went good
     */
    @PostMapping("")    // this will handle the upcoming request
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap != null) // there are some errors
            return errorMap;

        // there is no error
        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}") // projectId is a path variable, which is send together with the url
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId.toUpperCase());

        return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
    }
}
