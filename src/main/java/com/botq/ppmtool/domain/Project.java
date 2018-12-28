package com.botq.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Project {

    // == fields ==

    @Id // this indicates that this field is a primary key of current entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this will increment value of this field automatically
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")   // this will specify the minimum and maximum size of value for this field
    @Column(updatable = false, unique = true)   // this allows us to specify some attributes for the column. We don't allow updating this field and it has to be unique
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd") // we specify a JSON format for a date fields
    private Date start_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    // == constructor ==

    public Project() {
    }

    // == getters/setters are generated by Lombok @Data annotation ==

    // == methods ==

    @PrePersist // every time we create a new object
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate // every time we update an object
    protected void onUpdate(){
        this.updated_At = new Date();
    }
}
