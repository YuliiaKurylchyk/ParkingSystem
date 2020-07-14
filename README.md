# ParkingSystem
Final project.

Java, Servlet API, JSP, JSTL, H2 IN MEMORY.
Junit 5, Mockito.
i18n

Current project represents parking lot with different sizes of slots:
SMALL,MEDIUM and LARGE. Each type of vehicle has own parking slot,
which is defined according to additional parameters: SMALL slot will be
defined for MOTORBIKES or CARS with carSize MINI_CAR.
MEDIUM slot will bew defined for almost all cars (but PICK UP),for
TRUCKS without trailer and for small BUSES (count of  seats < 17).
LARGE slot will be defined for CARS with carSize PickUp, for Trucks
with trailer and big buses.

At home page there is information about how many vehicles is present
in parking lot and  today's entries. There is also information
about how many vehicles of appropriate type is present in parking lot and
how many parking slots are 'VACANT'.

User will be able to create new parking ticket by filling in the
information about vehicle(make,model,license plate and additional parameter
depending on the type of vehicle), customer(name, surname, phone number)and
choosing parking slot.

All necessary information will be saved in parking ticket. It has
many properties which contain information about vehicle,customer,
parking slot, status('LEFT','PRESENT'),arrival time, departure time and cost.

Option 'Remove' in current application means that vehicle is leaving,
so the cost should be calculated. During that the departure time is set, and
status changes to 'LEFT'

Option 'Delete completely' deletes parking ticket from database. Only
parking ticket with status 'LEFT' can be deleted.

One customer can have many vehicles on the parking lot.

Information about vehicle and customer of ticket can be changed, but parking slot
information can only be changed when parking ticket's status is 'PRESENT'.

When user fills in the information about vehicle which is present in database, that
information will be selected from database only if the vehicle's status of ticket is
LEFT, but if it is present, the exception will be thrown.

When user fills in customer's phone number which is already present in database,
that customer is selected from database and set to current parking ticket.

User is able to search parking ticket using parking_ticket_id,
customer's phone number or vehicle license plate.

User is able to explore the lists of vehicles, parking tickets,
customers or parking slots and see detailed information about it.
For instance, user can get all parking tickets which belong to appropriate
customer or what vehicle is located on current slot.

User is also able to look up list of parking tickets according to date
or status of parking ticket('PRESENT','VACANT').

User is also able to add,delete parking slots or change the prices of slots
on page /admin. Only 'VACANT' parking slots can be deleted.
