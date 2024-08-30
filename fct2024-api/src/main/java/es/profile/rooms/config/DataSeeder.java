package es.profile.rooms.config;

import es.profile.rooms.model.entities.*;
import es.profile.rooms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private static final int BOOKING_DURATION_HOURS = 2;
    private static final int BOOKING_START_HOUR = 9;
    private static final float EXTRA_PRICE_PER_HOUR = 10.0f;
    private static final int OFFICE_POSTCODE_BASE_SEVILLA = 41018;
    private static final int OFFICE_POSTCODE_BASE_BARCELONA = 80001;
    private static final int OFFICE_POSTCODE_BASE_MADRID = 28001;

    private final BookingsRepository bookingsRepository;
    private final ExtrasRepository extrasRepository;
    private final OfficesRepository officesRepository;
    private final RoomsRepository roomsRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    @Transactional
    public void seedData() {
        seedUsers();
        seedExtras();
        seedOffices();
        seedRooms();
        seedBookings();
    }

    @Transactional
    public void seedUsers() {
        System.out.println("Seeding users...");
        String[] names = {"Alvaro", "Angelo", "Angela", "Fran", "Esteban", "Daniel", "Raul"};
        String[] surnames = {"Lama", "Rios", "Rodriguez", "Infante", "Garcia", "Rodrigo", "Luque"};
        String[] dnis = {"12345678A", "87654321L", "23456789C", "98765432D", "34567890E", "98765432A", "87654321L"};

        for (int i = 0; i < names.length; i++) {
            Role role = (i == 0) ? Role.ADMIN : Role.USER;

            Users user = Users.builder()
                    .name(names[i])
                    .surname(surnames[i] + " " + (i + 1))
                    .username(names[i].toLowerCase() + (i + 1))
                    .password(passwordEncoder.encode("password"))
                    .email(names[i].toLowerCase() + (i + 1) + "@example.com")
                    .dni(dnis[i])
                    .phone("123-456-978" + i)
                    .postcode("54321")
                    .address("Calle Jose de la Cámara")
                    .role(role)
                    .available(true)
                    .build();

            usersRepository.save(user);
        }
    }

    @Transactional
    private void seedExtras() {
        System.out.println("Seeding extras...");
        String[] extrasNames = {"AquaService", "CoffeeMachine", "Projector", "Whiteboard", "Microphone"};
        String[] extrasDescriptions = {
                "Dispensador de agua fría y caliente",
                "Máquina de café con variedad de opciones",
                "Proyector de alta definición para presentaciones",
                "Pizarra blanca con marcadores y borrador",
                "Micrófono inalámbrico para conferencias"
        };
        for (int i = 0; i < extrasNames.length; i++) {
            Extras extra = Extras.builder()
                    .name(extrasNames[i])
                    .description(extrasDescriptions[i])
                    .priceHour(EXTRA_PRICE_PER_HOUR + i * 2)
                    .available(true)
                    .build();

            extrasRepository.save(extra);
        }
    }

    @Transactional
    private void seedRooms() {
        System.out.println("Seeding rooms...");
        String[] roomNames = {"Sala 'Profilix' ", "Sala 'Azul' ", "Sala 'Verde' ", "Sala 'Roja' ", "Sala 'Amarilla' "};
        String[] roomDescriptions = {
                "La Sala 'Profilix' proporciona un espacio ideal para realizar presentaciones audiovisuales.",
                "La Sala 'Azul' ofrece un ambiente tranquilo y relajante para reuniones de equipo.",
                "La Sala 'Verde' está equipada con tecnología de vanguardia para sesiones de formación interactivas.",
                "La Sala 'Roja' cuenta con una configuración flexible para adaptarse a diferentes tipos de eventos.",
                "La Sala 'Amarilla' es perfecta para sesiones de brainstorming y talleres de creatividad."
        };

        for (int i = 0; i < roomNames.length; i++) {
            Rooms room = Rooms.builder()
                    .nameRoom(roomNames[i])
                    .size("Grande")
                    .capacity((50 + i * 10) + " personas")
                    .priceHour(100 + i * 20)
                    .availability("Disponible")
                    .description(roomDescriptions[i])
                    .pictureList("room" + (i + 1) + ".jpg")
                    .available(true)
                    .build();

            Optional<Offices> office = officesRepository.findById(i + 1);
            office.ifPresent(room::setOffice);

            roomsRepository.save(room);
        }
    }

    @Transactional
    private void seedOffices() {
        System.out.println("Seeding offices...");
        String[] cities = {"Sevilla", "Barcelona", "Madrid"};
        int[] basePostcodes = {OFFICE_POSTCODE_BASE_SEVILLA, OFFICE_POSTCODE_BASE_BARCELONA, OFFICE_POSTCODE_BASE_MADRID};

        for (int i = 0; i < cities.length; i++) {
            for (int j = 1; j <= 2; j++) { // 2 offices per city
                Offices office = Offices.builder()
                        .province(cities[i] + " " + j)
                        .city(cities[i])
                        .postcode(Integer.toString(basePostcodes[i] + j))
                        .address("Calle José de la Cámara, " + (5 + j) + ", 4º Planta")
                        .available(true)
                        .build();
                Extras Extra = extrasRepository.findById(j).orElseThrow(null);
                List<Extras> roomExtras = new ArrayList<>();
                roomExtras.add(Extra);
                office.setExtras(roomExtras);
                officesRepository.save(office);
            }
        }
    }

    @Transactional
    private void seedBookings() {
        System.out.println("Seeding bookings...");
        for (int i = 0; i < 5; i++) {
            Optional<Rooms> room = roomsRepository.findById(i + 1);
            Optional<Users> user = usersRepository.findById(i + 1);
            List<Extras> extras = extrasRepository.findAll();

            if (room.isPresent() && user.isPresent()) {
                Bookings booking = Bookings.builder()
                        .ticketTime(LocalDateTime.now())
                        .bookingDate(LocalDate.now())
                        .timeStart(LocalTime.of(BOOKING_START_HOUR + i, 0))
                        .timeEnd(LocalTime.of(BOOKING_START_HOUR + i + BOOKING_DURATION_HOURS, 0))
                        .totalPrice(100.0f + (i * 50))
                        .room(room.get())
                        .user(user.get())
                        .status(BookingStatus.ACCEPTED)
                        .build();

                bookingsRepository.save(booking);
            }
        }
    }
}
