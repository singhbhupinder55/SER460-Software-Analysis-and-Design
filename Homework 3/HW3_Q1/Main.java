// Problem 1 - Factory Pattern solution (reduced coupling)

// Abstractions (interfaces)
interface Engine {
    void start();
}

interface Transmission {
    void shift();
}

interface Wheels {
    void roll();
}

// Concrete implementations
class StandardEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Engine started");
    }
}

class StandardTransmission implements Transmission {
    @Override
    public void shift() {
        System.out.println("Transmission shifted");
    }
}

class StandardWheels implements Wheels {
    @Override
    public void roll() {
        System.out.println("Wheels are rolling");
    }
}

// Car now depends on abstractions and receives dependencies 
class Car {
    private final Engine engine;
    private final Transmission transmission;
    private final Wheels wheels;

    public Car(Engine engine, Transmission transmission, Wheels wheels) {
        this.engine = engine;
        this.transmission = transmission;
        this.wheels = wheels;
    }

    public void drive() {
        engine.start();
        transmission.shift();
        wheels.roll();
        System.out.println("Car is driving");
    }
}

// Factory centralizes object creation
final class CarFactory {
    private CarFactory() {}

    public static Car createCar() {
        Engine engine = new StandardEngine();
        Transmission transmission = new StandardTransmission();
        Wheels wheels = new StandardWheels();
        return new Car(engine, transmission, wheels);
    }
}

// Main uses the factory (client code stays decoupled)
public class Main {
    public static void main(String[] args) {
        Car car = CarFactory.createCar();
        car.drive();
    }
}
