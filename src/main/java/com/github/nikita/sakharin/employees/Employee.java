package com.github.nikita.sakharin.employees;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="employee")
public final class Employee {
    @Id @GeneratedValue
    @Column(name="id")
    @Getter @Setter
    private Long id;

    @Column(name="full_name", nullable=false)
    @Getter @Setter
    private String fullName;

    @ManyToOne(optional=true)
    @JoinColumn(name="supervisor", nullable=true, referencedColumnName="id", table="employee")
    @Getter @Setter
    private Employee supervisor;
}

