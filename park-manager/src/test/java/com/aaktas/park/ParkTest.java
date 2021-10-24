package com.aaktas.park;

import com.aaktas.model.VehicleVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkTestMethod() throws Exception {

        mockMvc.perform(get("/parks/test")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("you reached the test method")));
    }

    @Test
    public void parkTest() throws Exception {

        VehicleVO vehicleVO1 = new VehicleVO("34 MHG 001","Black Car");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parks/1")
                        .content(asJsonString(vehicleVO1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string("Allocated 1 slot."));

        VehicleVO vehicleVO2 = new VehicleVO("34 MHG 002","Red Truck");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parks/3")
                        .content(asJsonString(vehicleVO2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string("Allocated 4 slot."));

        VehicleVO vehicleVO3 = new VehicleVO("34 MHG 003","Blue Jeep");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parks/2")
                        .content(asJsonString(vehicleVO3))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string("Allocated 2 slot."));

        VehicleVO vehicleVO4 = new VehicleVO("34 MHG 004","Black Truck");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parks/3")
                        .content(asJsonString(vehicleVO4))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string("Garage is full"));

        mockMvc.perform(get("/parks/leave/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("2 slots deallocated.34 MHG 003 leaved from the park")));

        VehicleVO vehicleVO5 = new VehicleVO("34 MHG 005","White Car");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parks/1")
                        .content(asJsonString(vehicleVO5))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string("Allocated 1 slot."));

        mockMvc.perform(get("/parks/status/")).andDo(print());

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
