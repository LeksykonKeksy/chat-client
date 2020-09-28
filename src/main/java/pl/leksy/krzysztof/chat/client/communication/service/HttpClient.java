package pl.leksy.krzysztof.chat.client.communication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.leksy.krzysztof.chat.client.communication.model.http.CreateRoomRequestDto;
import pl.leksy.krzysztof.chat.client.communication.model.http.CreateRoomResponseDto;
import pl.leksy.krzysztof.chat.client.communication.model.http.JoinRoomRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpClient {
    private final RestTemplate restTemplate;

    @Value("${chat.server.create.url}")
    private String createUrl;

    @Value("${chat.server.join.url}")
    private String joinUrl;

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

    public boolean joinChatRoomOnServer(JoinRoomRequestDto dto) {
        final var response = restTemplate.postForEntity(joinUrl, dto, String.class);
        final var responseStatus = response.getStatusCode();
        switch (responseStatus) {
            case OK:
                return true;
            case UNAUTHORIZED:
                LOGGER.error("User unauthorized, please provide password.");
                return false;
            case CONFLICT:
                LOGGER.error("Server full.");
                return false;
            default:
                LOGGER.error("Unknown error. Status code: {}", responseStatus);
                return false;
        }
    }
}
