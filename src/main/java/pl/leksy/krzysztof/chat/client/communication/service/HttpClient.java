package pl.leksy.krzysztof.chat.client.communication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.leksy.krzysztof.chat.client.communication.model.http.CreateRoomRequestDto;
import pl.leksy.krzysztof.chat.client.communication.model.http.CreateRoomResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpClient {
    private final RestTemplate restTemplate;

    @Value("${chat.server.create.url}")
    private String createUrl;

    public CreateRoomResponseDto createChatRoomOnServer(CreateRoomRequestDto dto) {
        final var response = restTemplate.postForEntity(createUrl, dto, CreateRoomResponseDto.class);
        final var responseStatus = response.getStatusCode();
        if (responseStatus == HttpStatus.OK) {
            return response.getBody();
        } else {
            LOGGER.error("Exception during networking, status code: {}", responseStatus);
            throw new RuntimeException(); // TODO: exception
        }
    }

    // TODO: join + password checking
}
