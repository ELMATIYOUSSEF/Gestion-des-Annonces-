package com.example.filerouge.domain;

import com.example.filerouge.domain.Enums.StatusAnnounce;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "announces")
public class Announce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private StatusAnnounce status;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private AppUser publisher;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "announce")
    private Information information;

    @ManyToMany
    @JsonBackReference
    private Collection<Tag> tags = new ArrayList<>();

    @ManyToMany
    @JsonBackReference
    private Collection<Feature> features = new ArrayList<>();

    @OneToMany
    @JsonBackReference
    private Collection<Review> reviews;

    @OneToMany(mappedBy = "announce" )
    @JsonBackReference
    private Collection<Image> images;

    @OneToMany
    @JsonBackReference
    private Collection<Notification> notifications;

    private Long locationId;
    private Long categoryId;

    @ManyToOne
    @JsonBackReference
    private SubCategory subCategories;
}
