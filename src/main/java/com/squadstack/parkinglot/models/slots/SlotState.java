package com.squadstack.parkinglot.models.slots;

public enum SlotState {
    ACQUIRED{
        @Override
        public <T> T accept(SlotStateVisitor<T> visitor) {
            return visitor.visitAcquired();
        }
    },
    AVAILABLE{
        @Override
        public <T> T accept(SlotStateVisitor<T> visitor) {
            return visitor.visitAvailable();
        }
    }

    ;

    public abstract <T> T accept(SlotStateVisitor<T> visitor);
}