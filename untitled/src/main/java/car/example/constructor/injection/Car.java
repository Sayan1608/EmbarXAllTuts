package car.example.constructor.injection;

public class Car {
    private final Specification carSpecification;

    public Car(Specification carSpecification) {
        this.carSpecification = carSpecification;
    }

    public void showCarSpecificationDetails(){
        System.out.println("Car Details : " + carSpecification.toString());
    }
}
