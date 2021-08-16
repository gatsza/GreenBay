package org.gfa.greenbay.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class AuctionCreationResponseDTO {

  private Long id;
  private String name;
  private String description;
  @JsonProperty("photo_url")
  private String photoUrl;
  @JsonProperty("ending_date_time")
  private LocalDateTime endingDateTime;
  @JsonProperty("bid_price")
  private Long bidPrice;
  @JsonProperty("purchase_price")
  private Long purchasePrice;

}
