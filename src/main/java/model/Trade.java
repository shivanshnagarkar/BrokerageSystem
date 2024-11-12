package model;

import enums.Status;
import enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId ;

    @Column(nullable = false)
    private String shareName;

    @Column(nullable = false)
    private Integer shareQuantity;

    @Column(nullable = false)
    private Double sharePrice;

    @Column(nullable = false)
    private char buyOrSell;

    @Column(nullable = false)
    private Integer traderId;

    @Enumerated(EnumType.STRING)
    @Column
    Status status;

    @Column(nullable = false)
    private Double tradedValue;

    @Enumerated(EnumType.STRING)
    @Column
    private Type tradeType;


}
