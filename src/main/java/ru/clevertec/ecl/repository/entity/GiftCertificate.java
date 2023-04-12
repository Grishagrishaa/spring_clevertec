package ru.clevertec.ecl.repository.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gift_certificates", schema = "clev")
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
public class GiftCertificate extends BaseEntity{
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.REFRESH
                   })
    @JoinTable(name = "gift_certificates_tags", schema = "clev",
    joinColumns = {@JoinColumn(name = "gift_certificate_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag){
        this.tags.add(tag);
    }
}
