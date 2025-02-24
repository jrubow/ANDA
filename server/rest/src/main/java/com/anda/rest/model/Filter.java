package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Embedded;
import jakarta.persistence.Table;

/*
 * Filter class which holds what a user would see from their side from weather type to weather occurrences
 */

@Entity
@Table(name="filters")
public class Filter {
    @Id
    private int filter_num;
}
