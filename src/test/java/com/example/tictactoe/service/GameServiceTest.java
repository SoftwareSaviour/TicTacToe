package com.example.tictactoe.service;

import com.google.protobuf.Empty;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tictactoe.GameServiceGrpc;
import tictactoe.Player;
import tictactoe.Response;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Mock
    GameStateService gameStateService;

    @Test
    public void createNewSession_shouldReturnGreeting() throws Exception {
        // Arrange
        GameService service = new GameService(gameStateService);
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(service)
                .build()
                .start());

        GameServiceGrpc.GameServiceBlockingStub stub = GameServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        // Act
        Response response = stub.createNewSession(Empty.newBuilder().build());

        // Assert
        Assertions.assertEquals("Welcome!\nNew Game Started!", response.getGreeting());
    }

    @Test
    public void greet_shouldReturnGreeting() throws Exception {
        // Arrange
        String name = "World";
        GameService service = new GameService(gameStateService);
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(service)
                .build()
                .start());

        GameServiceGrpc.GameServiceBlockingStub stub = GameServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        Player player = Player.newBuilder().setId("x").setPosition("0").build();
        // Act
        Response response = stub.makeMove(Player.newBuilder().setId("x").setPosition("0").build());
        Mockito.verify(gameStateService, Mockito.times(1)).updateState(player);
        // Assert
        Assertions.assertEquals("Hello, x!", response.getGreeting());
    }
}