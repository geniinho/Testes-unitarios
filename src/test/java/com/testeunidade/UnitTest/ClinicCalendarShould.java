package com.testeunidade.UnitTest;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DisplayName("Teste da classe ClinicCalendar")
public class ClinicCalendarShould {

    private ClinicCalendar calendar;

    @BeforeAll
    static void testClassSetup(){
        System.out.println("Antes de tudo...");
    }

    @BeforeEach
    void init(){
        System.out.println("Antes de cada um");
        calendar = new ClinicCalendar(LocalDate.of(2022,7,11));
    }

    @Test
    @DisplayName("verifica se os dados cadastrados estão correndo")
    void allowEntryOfAnAppointment(){
        calendar = new ClinicCalendar(LocalDate.now());
        calendar.addAppointment(
                "Genivaldo",
                "Alves",
                "avery",
                "07/09/2022 6:00 PM");

        List<PatientAppointment> appointments = calendar.getAppointments();
        Assertions.assertNotNull(appointments);
        Assertions.assertEquals(1,appointments.size());
        PatientAppointment enteredAppt = appointments.get(0);
        Assertions.assertEquals("Genivaldo",enteredAppt.getPatientFirstName());
        Assertions.assertEquals("Alves",enteredAppt.getPatientLastName());
        Assertions.assertEquals(Doctor.avery,enteredAppt.getDoctor());
        Assertions.assertSame(Doctor.avery,enteredAppt.getDoctor());
        Assertions.assertEquals("07/09/2022 6:00 PM",
                enteredAppt.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a")),
                ()-> "Ao usar lambda essa mensagem só será exibida se o teste falhar.");
    }

    @Test
    @DisplayName("Retorna 'true' caso existe agendamento no dia")
    void returnTrueForHasAppointmentsIfThereAreAppointments(){
        calendar = new ClinicCalendar(LocalDate.now());
        calendar.addAppointment(
                "Genivaldo",
                "Alves",
                "avery",
                "07/09/2022 6:00 PM");
        Assertions.assertTrue(calendar.hasAppointment(LocalDate.of(2022,7,9)));
    }

    @Test
    @DisplayName("Retorna 'flaso' caso não exista agendamento no dia")
    void returnFalseForHasAppointmentsIfThereAreAppointments(){
        ClinicCalendar calendar = new ClinicCalendar(LocalDate.now());
        Assertions.assertFalse(calendar.hasAppointment(LocalDate.of(2022,7,9)));
    }

    @Test
    @DisplayName("Confere quantidade de agendamentos por dia")
    void returnCurrentDaysAppointments(){
        ClinicCalendar calendar = new ClinicCalendar(LocalDate.now());
        calendar.addAppointment(
                "Ingrid",
                "Solange",
                "avery",
                "07/10/2022 6:00 PM");

        calendar.addAppointment(
                "Genivaldo",
                "Alves",
                "avery",
                "07/09/2022 6:00 PM");

        calendar.addAppointment(
                "Claudia",
                "Costa",
                "avery",
                "07/11/2022 6:00 PM");

        Assertions.assertEquals(3,calendar.getAppointments().size());
        Assertions.assertFalse(calendar.hasAppointment(LocalDate.of(2022,7,30)));
    }

    @AfterEach
    void tearDownEachTest(){
        System.out.println("Depois de cada");
    }

    @AfterAll
    static void testDownTestClass(){
        System.out.println("Depois de tudo...");
    }
}
