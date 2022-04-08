package com.rivi.blueprint.dao;

import com.rivi.blueprint.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames={"name", "email"}))
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Constant.UserType type;

    private Date createdDate;

    private Date updatedDate;
}
