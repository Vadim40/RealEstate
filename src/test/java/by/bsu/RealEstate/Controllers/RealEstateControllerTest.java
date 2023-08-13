package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.RealEstateMapper;
import by.bsu.RealEstate.Models.DTO.RealEstateDTO;
import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Models.User;
import by.bsu.RealEstate.Services.CustomUserDetailsService;
import by.bsu.RealEstate.Services.RealEstateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = RealEstateController.class)
class RealEstateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RealEstateService realEstateService;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    RealEstateMapper realEstateMapper;


    private RealEstate realEstate;
    private RealEstateDTO realEstateDTO;
    private User user;
    private final String URL = "/estate";


    @BeforeEach
    public void getPageOfRealEstates() {
        realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").id(1L).userId(1L).build();
        realEstateDTO = RealEstateDTO.builder()
                .countRooms(3)
                .price(1000)
                .square(100)
                .type("flat")
                .build();
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .role("ADMIN")
                .build();

    }

    @Test
    public void shouldReturnPageOfRealEstates_HttpStatusIsOk() throws Exception {

        RealEstate realEstate2 = RealEstate.builder().countRooms(3).price(300).square(200)
                .type("flat").userId(1L).build();
        List<RealEstate> realEstates = new ArrayList<>();
        realEstates.add(realEstate2);
        realEstates.add(realEstate);
        Page<RealEstate> realEstatePage = new PageImpl<>(realEstates);
        int offset = 0;
        int pageSize = 10;
        when(realEstateService.findRealEstatesWithPagination(offset, pageSize))
                .thenReturn(realEstatePage);

        when(realEstateMapper.mapRealEstateToRealEstateDTO(any(RealEstate.class)))
                .thenAnswer(invocationOnMock -> {
                    RealEstate realEstate1 = invocationOnMock.getArgument(0);
                    return new RealEstateDTO(realEstate1.getCountRooms(),
                            realEstate1.getPrice(),
                            realEstate1.getSquare(),
                            realEstate1.getType());
                });
        mockMvc.perform(get(URL + "/all/0/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()").value(2));


    }

    @Test
    public void shouldGetRealEstateById_HttpStatusIsOk() throws Exception {
        long realEstateId = 1L;

        when(realEstateService.findRealEstateById(realEstateId)).thenReturn(realEstate);
        when(realEstateMapper.mapRealEstateToRealEstateDTO(realEstate))
                .thenAnswer(invocationOnMock -> {
                    RealEstate realEstate1 = invocationOnMock.getArgument(0);
                    return new RealEstateDTO(realEstate1.getCountRooms(),
                            realEstate1.getPrice(),
                            realEstate1.getSquare(),
                            realEstate1.getType());
                });

        mockMvc.perform(get(URL + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countRooms").value(2));


    }

    @Test
    public void shouldCreateRealEstate_HttpStatusIsCreate() throws Exception {

        when(customUserDetailsService.getAuthenticatedUser())
                .thenReturn(user);

        when(realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO))
                .thenReturn(new RealEstate(1L, realEstateDTO.getType(),
                        realEstateDTO.getPrice(),
                        realEstateDTO.getSquare(),
                        realEstateDTO.getCountRooms(),
                        1L,
                        false));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(realEstateDTO);

        mockMvc.perform(post(URL + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
        verify(realEstateService, times(1)).saveRealEstate(any(RealEstate.class));
    }

    @Test
    public void shouldEditRealEstate_HttpStatusIsCreate() throws Exception {
        long realEstateId = 1L;
        when(customUserDetailsService.getAuthenticatedUser())
                .thenReturn(user);
        when(realEstateService.findRealEstateById(realEstateId))
                .thenReturn(realEstate);

        when(realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO))
                .thenReturn(new RealEstate(1L, realEstateDTO.getType(),
                        realEstateDTO.getPrice(),
                        realEstateDTO.getSquare(),
                        realEstateDTO.getCountRooms(),
                        1L,
                        false));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(realEstateDTO);

        mockMvc.perform(put(URL + "/1/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
        verify(realEstateService, times(1)).updateRealEstate(anyLong(), any(RealEstate.class));
    }

    @Test
    public void shouldDeleteRealEstate_HttpStatusIsOk() throws Exception {
        long realEstateId = 1L;
        when(customUserDetailsService.getAuthenticatedUser())
                .thenReturn(user);
        when(realEstateService.findRealEstateById(realEstateId))
                .thenReturn(realEstate);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(realEstateDTO);

        mockMvc.perform(delete(URL + "/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        verify(realEstateService, times(1)).deleteRealEstate(anyLong());
    }
}