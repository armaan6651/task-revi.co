package com.rivi.blueprint.dao;

import com.rivi.blueprint.utils.Constant;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "USER_STORY")
@Entity
public class UserStoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private Date dueDate;

    @Enumerated(EnumType.STRING)
    private Constant.StoryState status;

    @OneToMany(mappedBy = "userStoryEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<TaskEntity> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    private Date createdDate;

    private Date updatedDate;
}
