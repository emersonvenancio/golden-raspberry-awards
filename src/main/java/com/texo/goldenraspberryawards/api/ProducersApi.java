package com.texo.goldenraspberryawards.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texo.goldenraspberryawards.api.response.ProducerAwardsFrequecy;
import com.texo.goldenraspberryawards.service.ProducerService;

@RestController
@RequestMapping("/producers")
public class ProducersApi {

	private static final Logger log = LoggerFactory.getLogger(ProducersApi.class);

	@Autowired
	private ProducerService service;

	@GetMapping("/awards-frequency")
	public ResponseEntity<ProducerAwardsFrequecy> getAwardsFrequecy() {
		try {
			final ProducerAwardsFrequecy result = service.getAwardsFrequecy();
			return ResponseEntity.ok(result);
		} catch (final Exception e) {
			log.error("Error on getAwardsFrequecy", e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
