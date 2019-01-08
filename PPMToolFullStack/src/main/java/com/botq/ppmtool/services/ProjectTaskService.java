package com.botq.ppmtool.services;

import com.botq.ppmtool.domain.Backlog;
import com.botq.ppmtool.domain.ProjectTask;
import com.botq.ppmtool.repositories.BacklogRepository;
import com.botq.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        // Project Tasks to be added to a specific project, project != null => Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // set the Backlog to Project Task
        projectTask.setBacklog(backlog);
        // we want our project sequence to be for example like this: IDPRO-1, IDPRO-2, etc.
        Integer backlogSequence = backlog.getPTSequence();
        // Update the Backlog Sequence
        backlogSequence++;

        backlog.setPTSequence(backlogSequence);

        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // INITIAL priority when priority == null
        if(projectTask.getPriority() == null){ // In the future we need projectTask.getPriority() == 0 to handle the form
            projectTask.setPriority(3);
        }
        // INITIAL status when status == null
        if(projectTask.getStatus() == null || projectTask.getStatus().equals("")){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
