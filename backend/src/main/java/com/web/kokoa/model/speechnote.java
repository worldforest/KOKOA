package com.web.kokoa.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Data
public class speechnote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userid;
    private int subtitleid;
    private int engsubtitleid;
    private int videoid;
}
