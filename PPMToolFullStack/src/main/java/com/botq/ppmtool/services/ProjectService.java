package com.botq.ppmtool.services;

import com.botq.ppmtool.domain.Project;
import com.botq.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// in @Service we
@Service
public class ProjectService {

    @Autowired  // this will allow us to use our database object and methods on it
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        // Logic

        return projectRepository.save(project);
    }
}
