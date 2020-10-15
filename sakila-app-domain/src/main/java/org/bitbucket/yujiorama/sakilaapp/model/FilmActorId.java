package org.bitbucket.yujiorama.sakilaapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import java.io.Serializable;

@Data
@AllArgsConstructor
@With
public class FilmActorId implements Serializable {

    private static final long serialVersionUID = 1374L;

    public FilmActorId() {
    }

    private Integer actorId;

    private Integer film;
}
