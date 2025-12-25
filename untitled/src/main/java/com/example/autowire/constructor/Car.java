package com.example.autowire.constructor;

public class Car {
    private Specification carSpecification;

    public Car(Specification carSpecification) {
        this.carSpecification = carSpecification;
    }

    // Autowiring By Name uses setter
    public void setCarSpecification(Specification carSpecification) {
        this.carSpecification = carSpecification;
    }

    public void showCarSpecificationDetails(){
        System.out.println("Car Details : " + carSpecification.toString());
    }
}
