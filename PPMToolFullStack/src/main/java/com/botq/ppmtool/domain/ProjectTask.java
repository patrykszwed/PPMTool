package com.botq.ppmtool.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    private String acceptanceCriteria;

    private String status;

    private Integer priority;

    private Date dueDate;

    // ManyToOne with Backlog

    @Column(updatable = false)
    private String projectIdentifier;

    private Date created_At;

    private Date updated_At;

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updated_At = new Date();
    }
}
