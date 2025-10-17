package org.taxionline.core.domain.position;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.core.domain.base.IdModelBase;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Position extends IdModelBase {

    Double lat;

    Double lon;

    LocalDateTime date;

}
