package com.texo.goldenraspberryawards.api;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProducersApiTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testAwardsFrequency() throws Exception {
		mockMvc.perform(get("/producers/awards-frequency")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.min[0].producer", equalTo("Joel Silver")))//
			.andExpect(jsonPath("$.min[0].interval", equalTo(1)))//
			.andExpect(jsonPath("$.min[0].previousWin", equalTo(1990)))//
			.andExpect(jsonPath("$.min[0].followingWin", equalTo(1991)))//
			.andExpect(jsonPath("$.min.length()", equalTo(1)))//
			.andExpect(jsonPath("$.max[0].producer", equalTo("Matthew Vaughn")))//
			.andExpect(jsonPath("$.max[0].interval", equalTo(13)))//
			.andExpect(jsonPath("$.max[0].previousWin", equalTo(2002)))//
			.andExpect(jsonPath("$.max[0].followingWin", equalTo(2015)))//
			.andExpect(jsonPath("$.max.length()", equalTo(1)));
	}

}
