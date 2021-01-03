package com.squadstack.parkinglot.models;

import lombok.Builder;

import java.util.Objects;

public class ParkableEntity {

    private final String carPlateNumber;
    private final int age;

    @Builder
    public ParkableEntity(String carPlateNumber, int age) {
        this.carPlateNumber = carPlateNumber;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkableEntity that = (ParkableEntity) o;
        return age == that.age &&
                Objects.equals(carPlateNumber, that.carPlateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carPlateNumber, age);
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "ParkableEntity{" +
                "carPlateNumber='" + carPlateNumber + '\'' +
                ", age=" + age +
                '}';
    }
}
