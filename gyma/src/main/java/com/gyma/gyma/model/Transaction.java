package com.gyma.gyma.model;

import com.gyma.gyma.model.enums.CategoryTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name="transactions", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name="created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "update_by", nullable = false)
    private Profile updateBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private Profile createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CategoryTransaction category;

}
