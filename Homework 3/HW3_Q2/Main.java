// Problem 2 - Eliminating Content Coupling using Encapsulation

class Engine {
    private int fuelLevel;
    private boolean running;

    public Engine() {
        this.fuelLevel = 100;
        this.running = false;
    }

    public void start() {
        if (fuelLevel > 0) {
            running = true;
            System.out.println("Engine started");
        } else {
            System.out.println("Not enough fuel to start the engine");
        }
    }

    // Public method to safely reduce fuel (no direct field access)
    public void consumeFuel(int amount) {
        if (amount < 0) return; 
        fuelLevel = Math.max(0, fuelLevel - amount);
    }

    public boolean isRunning() {
        return running;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }
}

class Car {
    private final Engine engine;

    public Car() {
        engine = new Engine();
    }

    public void drive() {
        // No direct access to engine fields (content coupling removed)
        engine.consumeFuel(10);

        if (!engine.isRunning()) {
            engine.start();
        }

        if (engine.isRunning()) {
            System.out.println("Car is driving with fuel level: " + engine.getFuelLevel());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
        car.drive();
    }
}
