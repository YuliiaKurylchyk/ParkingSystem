JAVA FINAL PROJECT
PARKING SYSTEM

Current project represent parking lot with different sizes of slots:
SMALL,MEDIUM and LARGE. Each type of vehicle has own parking slot,
which is defined according to additional parameters: SMALL slot will be
defined for MOTORBIKES or CARS with carSize MINI_CAR.
MEDIUM slot will bew defined for almost all cars (but PICK UP),for
TRUCKS without trailer and for small BUSES (count of  seats < 17).
LARGE slot will be defined for CARS with carSize PickUp, for Trucks
with trailer and big buses.

All necessary information will be saved in parking ticket. It has
mane properties which contain information about vehicle,customer,
parking slot, status('LEFT','PRESENT'),arrival time, departure time and cost.

Option 'Remove' in current application means that vehicle is leaving,
so the cost should be calculated. During that the departure time is set, and
Status changes to 'LEFT'

One customer can have many vehicles on the parking lot.

