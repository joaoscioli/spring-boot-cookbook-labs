package com.packt.albumstwo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "football.api.url=http://localhost:7979"})
public class FootballClientServiceTests {

    private static WireMockServer wireMockServer;

    @Autowired
    FootballClientService footballClientService;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(7979);
        wireMockServer.start();
        WireMock.configureFor(7979);
    }
}
