package com.example.tictactoe.service;

import com.google.protobuf.Empty;
import greet.Board;
import greet.GameServiceGrpc;
import greet.Player;
import greet.Response;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GameService extends GameServiceGrpc.GameServiceImplBase {

    private final GameStateService gameStateService;
    @Override
    public void createNewSession(Empty empty, StreamObserver<Response> responseObserver) {
        String greeting = "Welcome!\nNew Game Started!";
        gameStateService.createNewSession();
        Response response = Response.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void makeMove(Player player, StreamObserver<Response> responseObserver) {
        String name = player.getId();
        String greeting = "Hello, " + name + "!";

        gameStateService.updateState(player);
        Response response = Response.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCurrentStateOfSession(Empty empty, StreamObserver<Board> responseObserver) {
        gameStateService.createNewSession();
        Board board = Board.newBuilder()
                .putAllBoard(gameStateService.getBoard())
                .build();

        responseObserver.onNext(board);
        responseObserver.onCompleted();
    }
}
