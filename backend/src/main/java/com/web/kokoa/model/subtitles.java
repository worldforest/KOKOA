package com.web.kokoa.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class subtitles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int videoid;

    private String starttime;

    private String endtime;

    private String content;

    private int type;

}
