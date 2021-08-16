package org.gfa.greenbay.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.greenbay.dtos.AuctionCreationRequestDTO;
import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.dtos.LoginResponseDTO;
import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.ItemRepository;
import org.gfa.greenbay.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class AuctionAddIntegrationTest {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private UserRepository userRepository;
  private String token;

  @BeforeAll
  public void init() throws Exception {
    User testUser = userRepository.findById(1L).get();

    LoginRequestDTO testRequest =
        new LoginRequestDTO(testUser.getUsername(), testUser.getPassword());

    String response = mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(testRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andReturn()
        .getResponse()
        .getContentAsString();

    token = objectMapper.readValue(response, LoginResponseDTO.class).getToken();
  }

  @Test
  public void successfulAddingItem_AllParameterRight() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        "something",
        "something description",
        "https://www.google.com/",
        "2018-09-16 08:00:00",
        24L,
        48L,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name", is("something")))
        .andExpect(jsonPath("ending_date_time", is("2018-09-16T08:00:00")));
  }

  @Test
  public void addingItemTest_wrongUrlFormat() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        "something",
        "something description",
        "https:/www.google.com/",
        "2018-09-16 08:00:00",
        24L,
        48L,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Photo url is not valid!")));
  }

  @Test
  public void addingItemTest_wrongTimeFormat() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        "something",
        "something description",
        "https://www.google.com/",
        "2018-09-1608:00:00",
        24L,
        48L,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error",
            is("Timestamp is given in wrong format! (yyyy-MM-dd HH:mm:ss)")));
  }

  @Test
  public void addingItemTest_nameMissing() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        null,
        "something description",
        "https://www.google.com/",
        "2018-09-16 08:00:00",
        24L,
        48L,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error",
            is("Auction cannot created, because additional information is required: [name]")));
  }

  @Test
  public void addingItemTest_descriptionMissing() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        "something",
        null,
        "https://www.google.com/",
        "2018-09-16 08:00:00",
        24L,
        48L,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error",
            is("Auction cannot created, because additional information is required: [description]")));
  }

  @Test
  public void addingItemTest_photoUrlBidPricePurchasePriceEndingDateMissing() throws Exception {
    AuctionCreationRequestDTO request = new AuctionCreationRequestDTO(
        "something",
        "something description",
        null,
        null,
        null,
        null,
        null,
        null
    );
    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error",
            is("Auction cannot created, because additional information is required: "
                + "[photo url, ending date time, starting price, purchase price]")));
  }

  @Test
  public void addingItemTest_payloadMissing() throws Exception {
    AuctionCreationRequestDTO request = null;

    mockMvc.perform(post("/auctions/add")
        .header("Authorization", "Bearer " + token)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error",
            is("Auction cannot created, all field are missing!")));
  }

}
