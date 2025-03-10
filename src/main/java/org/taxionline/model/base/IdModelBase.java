package org.taxionline.model.base;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class IdModelBase {

    @Id
    @Setter( AccessLevel.NONE )
    String id;

    @PrePersist
    private void prePersist( ) {
        this.id = UUID.randomUUID( ).toString( );
    }
}
