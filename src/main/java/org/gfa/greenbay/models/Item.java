package org.gfa.greenbay.models;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  @Column(name = "photo_url")
  private String photoUrl;
  @Column(name = "ending_date_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime endingTimestamp;
  @Column(name = "bid_price")
  private Long bidPrice;
  @Column(name = "purchase_price")
  private Long purchasePrice;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User seller;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User buyer;
}
