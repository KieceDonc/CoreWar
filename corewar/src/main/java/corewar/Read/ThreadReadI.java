package corewar.Read;

import java.util.concurrent.Callable;

public class ThreadReadI extends Thread implements Callable<Integer> {

    private int value = -1;

    @Override
    public void run() {
      this.value = Read.i();
    }

    @Override
    public Integer call() {
        return this.value;
    }
}