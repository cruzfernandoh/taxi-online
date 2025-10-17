package org.taxionline.core.domain.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class IdModelBase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Setter( AccessLevel.NONE )
    Long id;

    @Setter( AccessLevel.NONE )
    String identifier;

    @CreationTimestamp
    @Setter( AccessLevel.NONE )
    Instant created;

    @UpdateTimestamp
    @Setter( AccessLevel.NONE )
    Instant updated;

    @PrePersist
    private void prePersist( ) {
        this.identifier = UUID.randomUUID( ).toString( );
    }
}
