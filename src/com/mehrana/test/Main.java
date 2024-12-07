package com.mehrana.test;

import com.mehrana.test.entity.Leave;
import com.mehrana.test.entity.Personnel;
import com.mehrana.test.service.LeaveService;
import com.mehrana.test.service.PersonnelService;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        menu();
        try {
            while (running) {
                System.out.print("Please enter an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline left by nextInt()

                switch (choice) {
                    case 1:
                        insert(sc); //this method for adding information
                        break;
                    case 2:
                        select(); //this method for selecting
                        break;
                    case 3:
                        update(sc); //this method for updating
                        break;
                    case 4:
                        remove(sc); //this method fo removing
                        break;
                    case 5:
                        searchByName(sc); // New method for searching by username
                        break;
                    case 6:
                        insertLeave(sc);
                    case 7:
                        System.out.println("Goodbye!");
                        sc.close();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again...");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        System.out.println("*** Menu ***");
        System.out.println("1) Add");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Remove");
        System.out.println("5) Search by Name");
        System.out.println("6)  insert Leave");
        System.out.println("7) Exit");
    }

    public static Optional<Personnel> insert(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.println("** Enter Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        System.out.print("Enter your personnel code: ");
        int personnelCode = sc.nextInt();
        sc.nextLine();

        Personnel personnel = new Personnel();
        personnel.setUserName(userName);
        personnel.setMobile(mobile);
        personnel.setPersonnelCode((long) personnelCode);
        System.out.println("Your information has been saved: " + userName + " - " + mobile + " - " + personnelCode);
        return personnelService.insert(personnel);
    }

    public static List<Personnel> select() throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        List<Personnel> personnelList = personnelService.getListPersonnel();
        System.out.println("    Username | Mobile | Personnel Code");
        System.out.println("--------------------------------------");
        for (Personnel personnel : personnelList) {
            System.out.println(personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
        }
        return personnelList;
    }

    public static Personnel update(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your personnel code: ");
        long personnelCode = sc.nextLong();
        sc.nextLine();

        Personnel existingPersonnel = personnelService.findPersonnelByCode(personnelCode);
        if (existingPersonnel == null) {
            System.out.println("Personnel not found!");
            return null;
        }

        System.out.println("** Enter New Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        existingPersonnel.setUserName(userName);
        existingPersonnel.setMobile(mobile);

        Personnel updatedPersonnel = personnelService.updatePersonnel(existingPersonnel);
        if (updatedPersonnel != null) {
            System.out.println("Personnel updated successfully: " + updatedPersonnel);
        } else {
            System.out.println("Failed to update personnel.");
        }

        return updatedPersonnel;
    }

    public static void remove(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Optional<Personnel> existingPersonnel = personnelService.findById(id);

        if (existingPersonnel.isPresent()) {
            personnelService.deleteById(id);
            System.out.println("Personnel with ID " + id + " has been deleted successfully.");
        } else {
            System.out.println("Personnel not found with ID " + id + "!");
        }
    }

    //  Search by Name
    public static void searchByName(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter the username to search: ");
        String name = sc.nextLine();

        List<Personnel> results = personnelService.findPersonnelByName(name);
        if (results.isEmpty()) {
            System.out.println("No personnel found with the name: " + name);
        } else {
            System.out.println("    Username | Mobile | Personnel Code");
            System.out.println("--------------------------------------");
            for (Personnel personnel : results) {
                System.out.println(personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
            }
        }
    }

    public static Optional<Leave> insertLeave(Scanner sc) throws SQLException, ParseException {
        System.out.print("enter your personnelCode: ");
        int personnelCode = sc.nextInt();

        PersonnelService personnelService = new PersonnelService();
        Personnel p = personnelService.findPersonnelByCode(personnelCode);
        LeaveService leaveService = new LeaveService();
        Scanner scanner = new Scanner(System.in);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        String todayAsString = simpleDateFormat.format(today);
        System.out.println("Today's date: " + todayAsString);
        System.out.print("Enter your starting date(\"YYYY-MM-DD\"): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter your ending date(\"YYYY-MM-DD\"): ");
        String endDate = scanner.nextLine();
        System.out.println("Enter your description: ");
        String description = scanner.nextLine();

        Leave leave = new Leave();
        leave.setStartDate(simpleDateFormat.parse(startDate));
        leave.setEndDate(simpleDateFormat.parse(endDate));
        leave.setDescription(description);
        leave.setPersonnelId(leave.getPersonnelId());
        return leaveService.insert(leave);

    }
}
