package me.sreejithnair.linkup.post_service.client;

import me.sreejithnair.linkup.post_service.dto.response.PersonResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/{userId}/first-connections")
    List<PersonResponseDto> getFirstConnections(@PathVariable Long userId);

}
