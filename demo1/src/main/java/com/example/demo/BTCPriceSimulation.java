package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Component
public class BTCPriceSimulation implements Runnable {
        private int priceIncrease = 10;
        private int cycleDuration = 3 * 60; // 3分鐘
        public AtomicInteger currentBTC = new AtomicInteger(100);

        public BTCPriceSimulation(){
            new Thread(this).start();
        }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            simulateCycle();

        }
    }
    private void simulateCycle() throws InterruptedException {
        // 初始3分鐘，價格增加
        for (int i = 0; i < cycleDuration / 5; i++) {
            currentBTC.addAndGet(priceIncrease);
            System.out.println(currentBTC);
            sleep(5000); // 5秒
        }

        // 初始3分鐘後，價格減少
        for (int i = 0; i < cycleDuration / 5; i++) {
            currentBTC.addAndGet(-priceIncrease);
            System.out.println(currentBTC);
            sleep(5000); // 5秒
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
