package org.gfa.greenbay.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gfa.greenbay.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionCreationRequestDTO {

  private String name;
  private String description;
  @JsonProperty("photo_url")
  private String photoUrl;
  @JsonProperty("ending_date_time")
  private String endingDateTime;
  @JsonProperty("starting_price")
  private Long bidPrice;
  @JsonProperty("purchase_price")
  private Long purchasePrice;

  @JsonIgnore
  private LocalDateTime endingTimestamp;
  @JsonIgnore
  private User seller;

}
