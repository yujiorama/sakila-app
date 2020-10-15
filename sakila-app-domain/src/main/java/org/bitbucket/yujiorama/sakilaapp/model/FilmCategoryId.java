package org.bitbucket.yujiorama.sakilaapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import java.io.Serializable;

@Data
@AllArgsConstructor
@With
public class FilmCategoryId implements Serializable {

    private static final long serialVersionUID = 1374L;

    public FilmCategoryId() {
    }

    private Integer filmId;

    private Integer category;
}
