package com.study.my.future;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

public class FutureService {

    class Request{
        String orderCode;
        String serialNo;
        CompletableFuture<Map<String, Object>> future;
    }

    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public Map<String, Object> query(String orderCode){
        String serialNo = UUID.randomUUID().toString();
        Request request = new Request();
        request.orderCode = orderCode;
        request.serialNo = serialNo;
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.future = future;
        queue.add(request);
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
