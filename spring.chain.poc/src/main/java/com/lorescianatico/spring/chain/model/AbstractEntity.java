package com.lorescianatico.spring.chain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSION")
    @Version
    private Long version;
}
