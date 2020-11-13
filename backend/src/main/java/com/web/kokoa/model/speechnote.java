package com.web.kokoa.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class speechnote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userid;
    private int subtitleid;
    private int engsubtitleid;
    private int videoid;
}
