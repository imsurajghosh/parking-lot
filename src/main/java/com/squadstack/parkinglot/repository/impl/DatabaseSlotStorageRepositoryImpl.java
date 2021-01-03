package com.squadstack.parkinglot.repository.impl;

import com.squadstack.parkinglot.dto.StoredParkableEntity;
import com.squadstack.parkinglot.dto.StoredSlot;
import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.models.slots.Slot;
import com.squadstack.parkinglot.models.slots.SlotState;
import com.squadstack.parkinglot.repository.SlotStorageRepository;
import com.squadstack.parkinglot.utils.HibernateUtils;
import com.squadstack.parkinglot.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class DatabaseSlotStorageRepositoryImpl implements SlotStorageRepository {

    private final int slotSize;

    public DatabaseSlotStorageRepositoryImpl(int slotSize) {
        this.slotSize = slotSize;

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < slotSize; ++i) {
            session.save(StoredSlot.builder()
                    .slotNumber(i +1)
                    .state(SlotState.AVAILABLE)
                    .build());
        }

        session.getTransaction().commit();

    }


    @Override
    public List<AcquiredSlot> getAcquiredSlot(int age) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(StoredParkableEntity.class);
        criteria.add(Restrictions.eq("age", age));

        List<StoredParkableEntity> storedParkableEntities = criteria.list();

        List<AcquiredSlot> acquiredSlots = storedParkableEntities.stream()
                .map(StoredParkableEntity::getSlot)
                .map(MapperUtils::toSlot)
                .map(MapperUtils::toAcquiredSlot)
                .collect(Collectors.toList());

        session.getTransaction().commit();
        return acquiredSlots;
    }

    @Override
    public AcquiredSlot getAcquiredSlot(String carPlateNumber) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(StoredParkableEntity.class);
        criteria.add(Restrictions.eq("carPlateNumber", carPlateNumber));

        List<StoredParkableEntity> storedParkableEntities = criteria.list();

        Optional<AcquiredSlot> acquiredSlot = storedParkableEntities.stream()
                .map(StoredParkableEntity::getSlot)
                .map(MapperUtils::toSlot)
                .map(MapperUtils::toAcquiredSlot)
                .findFirst();

        session.getTransaction().commit();
        return acquiredSlot.orElseThrow(() -> ParkingLotException.from(ErrorCode.CAR_PLATE_NOT_PRESENT, "Car" +
                " plate number not present"));
    }

    @Override
    public AcquiredSlot acquireSlot(ParkableEntity parkableEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(StoredSlot.class);
        criteria.add(Restrictions.eq("state", SlotState.AVAILABLE));
        criteria.addOrder(Order.asc("slotNumber"));
        List<StoredSlot> storedSlotList = criteria.list();

        if (storedSlotList.isEmpty()) {
            throw ParkingLotException.from(ErrorCode.SLOTS_UNAVAILABLE_EXCEPTION, "slots unavailable");
        }

        StoredSlot availableSlot = storedSlotList.get(0);
        StoredParkableEntity storedParkableEntity = MapperUtils.toStoredParkableEntity(parkableEntity);
        session.save(storedParkableEntity);

        availableSlot.setState(SlotState.ACQUIRED);
        availableSlot.setStoredParkableEntity(storedParkableEntity);

        session.getTransaction().commit();
        return MapperUtils.toAcquiredSlot(MapperUtils.toSlot(availableSlot));
    }

    @Override
    public AcquiredSlot leaveSlot(int slotNumber) {
        if (slotSize < slotNumber) {
            throw ParkingLotException.from(ErrorCode.INVALID_SLOT_NUMBER, "invalid slot number");
        }

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();


        Criteria criteria = session.createCriteria(StoredSlot.class);
        criteria.add(Restrictions.eq("slotNumber", slotNumber));
        List<StoredSlot> slots = criteria.list();
        Optional<AcquiredSlot> acquiredSlot = slots.stream()
                .filter(new Predicate<StoredSlot>() {
                    @Override
                    public boolean test(StoredSlot storedSlot) {
                        return storedSlot.getState().equals(SlotState.ACQUIRED);
                    }
                })
                .map(MapperUtils::toSlot)
                .map(MapperUtils::toAcquiredSlot)
                .findFirst();

        if (acquiredSlot.isPresent()) {
            StoredSlot storedSlot = slots.get(0);
            storedSlot.setState(SlotState.AVAILABLE);
            storedSlot.setStoredParkableEntity(null);
        }

        session.getTransaction().commit();
        return acquiredSlot.orElseThrow(() -> ParkingLotException.from(ErrorCode.ATTEMPT_TO_LEAVE_UNACQUIRED_SLOT, "" +
                "leaving unacquired slot"));
    }

    @Override
    public void close() {
        HibernateUtils.shutdown();
    }
}