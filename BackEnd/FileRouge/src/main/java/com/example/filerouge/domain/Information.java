package com.example.filerouge.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "information")
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "announce_id")
    private Announce announce;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Boolean visiblePhone;

    @Column(nullable = false)
    private Integer numChambers;

    @Column(nullable = false)
    private Integer numSalons;

    @Column(nullable = false)
    private Integer numBain;

    @Column(nullable = false)
    private Integer numCuisine;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Double livingSurface;

    @Column(nullable = false)
    private Double totalSurface;

    @Column(nullable = false)
    private Double propertyAge;

    @Column(nullable = false)
    private Double syndicFees ;

    @Column(nullable = false)
    private Double cleaningFees;
}
