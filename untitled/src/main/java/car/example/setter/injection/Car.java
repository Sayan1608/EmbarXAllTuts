package car.example.setter.injection;

public class Car {

    private  Specification carSpecification;

    public void setCarSpecification(Specification carSpecification) {
        this.carSpecification = carSpecification;
    }

    public void showCarSpecificationDetails(){
        System.out.println("Car Details(Setter) : " + carSpecification.toString());
    }
}
