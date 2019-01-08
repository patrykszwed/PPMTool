package com.botq.ppmtool.services;

import com.botq.ppmtool.domain.Backlog;
import com.botq.ppmtool.domain.Project;
import com.botq.ppmtool.exceptions.ProjectIdException;
import com.botq.ppmtool.repositories.BacklogRepository;
import com.botq.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// in @Service we
@Service
public class ProjectService {

    @Autowired  // this will allow us to use our database object and methods on it
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        String projectIdentifierToUpperCase = project.getProjectIdentifier().toUpperCase();
        try{
            project.setProjectIdentifier(projectIdentifierToUpperCase); // we don't want to have ambiguity, so we force to use upper cases

            if(project.getId() == null){    // check if the operation is save(that is new project, so id = null) or update(id already exists)
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifierToUpperCase);
            }

            if(project.getId() != null){    // we're updating the project as the project id is not null
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifierToUpperCase));
            }

            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("Project ID '" + projectIdentifierToUpperCase + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null){
            throw new ProjectIdException("Cannot delete project with ID '" + projectId + "'. This project does not exist");
        }

        projectRepository.delete(project);  // delete project if it is not null. This method is in super class CrudRepository
    }
}
